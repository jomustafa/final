import React, { isValidElement, useEffect, useState, useRef} from "react";
import "bootstrap/dist/css/bootstrap.css";
import "../../assets/stylesheets/main_styles.css";
import "./supermarket.css"
import * as Stomp from "stompjs";
import SockJS from "sockjs-client";
import Player from '../player/player';
import Level from '../level/level';
import Menu from '../menu/menu';
import swal from 'sweetalert';
import background from '../../photographs/supermarket/supermarket.png';
import Navbar from "../navigation/Navbar";
import Cookies from "universal-cookie";
import Timer from "../sidebar/timer";
import { type } from "jquery";


let correctProducts = [];
let link = window.location.toString();
let level = Number(link.split("=")[1]);
let randomImagesArray = [];
let mistakes = 0;
let constants;

export default function ClientComponent() {
    const socket = useRef(new SockJS('/brainbright-websocket'));
    const stompClient = useRef(Stomp.over(socket.current));
    const cookies = new Cookies();

    //When switching menus, need to connect here instead
    if (cookies.get("language") === "en") {
        constants = require("../../assets/metatext/constants");
    } else {
        constants = require("../../assets/metatext/constantsgr");
    }

    const [images, setImages] = useState([]);
    const [randomImages, setRandomImages] = useState([]);
    const [levelRender, setLevelRender] = useState(Number(level));
    const [correctRender, setCorrectRender] = useState([]);
    const [mistakesRender, setMistakesRender] = useState(0);
    const [pause, setPause] = useState(false);

    useEffect(() => {
        stompClient.current.connect({}, function () {

            //Executed whenever a list of words is received
            this.subscribe('/user/topic/getproducts', function (data) {
                // console.log(data.body);
                //Push the names from the Image objects into image array
                let array = [];
                JSON.parse(data.body).map((item) => {
                    array.push(item);
                });
                setImages(array);
                console.log(array);
                getRandomProducts(level);
            });
            getList(level);
            this.subscribe('/user/topic/getrandomproducts', function (data) {
                // console.log(data.body);
                //Push the names from the Image objects into image array
                JSON.parse(data.body).map((item) => {
                    randomImagesArray.push(item);
                });
                setRandomImages(randomImagesArray);
            });

        });
    }, []);

    function getList(level) {
        stompClient.current.send("/app/getproducts", {}, level);
    }
    function getRandomProducts(level) {
        stompClient.current.send("/app/getrandomproducts", {}, level);
    }

    function timerOver() {
        Array.from(document.getElementsByClassName("ingredients")).forEach((el) => {
            el.style.visibility = "visible";
        })



        // Array.from(document.getElementsByClassName("ho-bottomButtons")).forEach((el) => {
        //     el.style.visibility = "visible";
        // })

        document.getElementById("timer").style.visibility = "hidden";
        document.getElementById("ingredientlist").style.visibility = "hidden";
    }

    function recordScore() {
        stompClient.current.send(
            "/app/spotdif_recordscore",
            {},JSON.stringify(
            {
                name: cookies.get("currentUser"),
                level: level,
                missed: 0,
                points: 100,
            })
        );
    }

    function checkButton(e) {
        let value = e.target.value;
        if (randomImagesArray.includes(value)) {
            document.getElementById(value).style.visibility = "hidden";
            correctProducts.push(value);
            setCorrectRender(correctProducts);
            if (correctProducts.length === level + 3) {
                if(level === 5){
                    recordScore();
                    swal(constants.FINISHED_LEVEL_LABEL, "", "success", {
                        buttons: {
                            retry: {
                                text: constants.CONTINUE_BTN,
                                value: "next",
                            },
                            goback: {
                                text: constants.BACK_TO_MENU_BTN,
                                value: "back",
                            },
                        }
                    }
                    ).then((value) => {
                        console.log(value);
                        switch (value) {
                            case "back":
                                window.location.replace("/visual");
                                break;
    
                            case "next":
                                window.location.replace("/supermarket?lvl=5");
                                break;
    
                            default:
                                window.location.replace("/visual");
                                break;
                        }
                    });
                } else{
                    recordScore();
                    swal(constants.FINISHED_LEVEL_LABEL, "", "success", {
                        buttons: {
                            retry: {
                                text: constants.CONTINUE_BTN,
                                value: "next",
                            },
                            goback: {
                                text: constants.BACK_TO_MENU_BTN,
                                value: "back",
                            },
                        }
                    }
                    ).then((value) => {
                        console.log(value);
                        switch (value) {
                            case "back":
                                window.location.replace("/visual"); 
                                break;
    
                            case "next":
                                window.location.replace("/supermarket?lvl=" + (level + 1));
                                break;
    
                            default:
                                window.location.replace("/visual"); 
                                break;
                        }
                    });
                }
                
            }
        } else {
            mistakes++;
            setMistakesRender(mistakes);
            if (mistakes === level + 2) {
                level = 1;
                setLevelRender(level);
                recordScore();
                swal(constants.UNFORTUNATELY_YOU_LOST, "", "error", {
                    buttons: {
                        retry: {
                            text: constants.TRY_AGAIN,
                            value: "retry",
                        },
                        goback: {
                            text: constants.BACK_TO_MENU_BTN,
                            value: "back",
                        },
                    }
                }
                ).then((value) => {
                    console.log(value);
                    switch (value) {
                        case "back":
                            window.location.replace("/visual");
                            break;
        
                        case "retry":
                            window.location.reload();
                            break;
        
                        default:
                            window.location.replace("/visual");
                            break;
                    }
                });
            }

        }
    }

    return (
        <div className="container-fluid">
            <Navbar />

            <div className="row">
                <div className="col-sm col-md-3">
                    <Menu level={levelRender} playerName={cookies.get("username")} instructions={constants.SUPERMARKET_INSTRUCTIONS} pause={() => { setPause(value => !value) }}></Menu>
                </div>

                <div class="vl"></div>

                <div className="col-md-6 text-center all_words fixed-top">

                    {/* display the product images on the shelf */}
                    <div className="row justify-content-center" style={{ transform: "translateY(-16%)" }}>
                        <img src={background} style={{ zIndex: "1", position: "absolute" }} />
                        <div style={{ zIndex: "2", width: "90%" }} className="col-md-9">
                            {
                                images.map((item, index) => {
                                    if (index % 2 === 1) {
                                        let path = item.split('/')[3];
                                        return (
                                            <button value={item} className="mt-3 mx-4 ingredients" id={item}
                                                style={{ visibility: "hidden", backgroundImage: "url(" + require("../../photographs/supermarket/" + path) + ")", zIndex: "2", height: "75px", width: "90px", backgroundPosition: "center" }}
                                                onClick={checkButton}>
                                            </button>
                                        )
                                    }
                                })
                            }
                        </div>

                    </div>
                </div>
                <div className="col-md-3 mt-5">

                    {/* display the shopping list */}
                    <div className="card mt-4" id="ingredientlist" style={{ width: "13rem" }}>
                        <div class="card-header">
                            {constants.SHOPPING_LIST}
                        </div>
                        <ul className="list-group list-group-flush">
                            {randomImages.map((item, index) => {
                                if (index % 2 === 0) {
                                    console.log(typeof(item));
                                    return <li className="list-group-item" style={{listStyle: "none"}}> {constants[item]} </li>;
                                    //return <span className="m-2"> {item} </span>;

                                }
                            })}
                        </ul>
                    </div>

                    {images.length > 0 ? <div className="col-md-3" id="timer"><Timer timer={5 + 5 * level} onTimerFinish={timerOver} /></div> : null}
                    <div className="mistakes ml-5 mt-5">{constants.INCORRECT_SELECTIONS}
                        <div className="mt-3">
                            <span className="border p-3">{mistakesRender}</span> {constants.OUT_OF} <span className="border p-3">{level + 2}</span>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    );

}
