import React, { isValidElement, useEffect, useState, useRef } from "react";
import "bootstrap/dist/css/bootstrap.css";
import "../../assets/stylesheets/main_styles.css";
import * as Stomp from "stompjs";
import SockJS from "sockjs-client";
import Player from "../player/player";
import Level from "../level/level";
import Menu from "../menu/menu";
import background from "../../photographs/hiddenobjects/background/background.jpg";
import swal from "sweetalert";
import Cookies from "universal-cookie";
import Navbar from "../navigation/Navbar";
// import './hiddenObjects.css'
import Timer from "../sidebar/timer";



let correctObjects = [];
let link = window.location.toString();
let level = Number(link.split("=")[1]);
let randomImagesArray = [];
let mistakes = 0;

let constants;

export default function ClientComponent() {

    const socket = useRef(new SockJS('/brainbright-websocket'));
    const stompClient = useRef(Stomp.over(socket.current));

    const cookies = new Cookies();
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
            this.subscribe("/user/topic/getobjects", function (data) {
                //Push the names from the Image objects into image array
                let array = [];
                JSON.parse(data.body).map((item) => {
                    array.push(item);
                });
                setImages(array);
                getRandomObjects(level);
            });
            getImages(level);
            this.subscribe("/user/topic/getrandomobjects", function (data) {
                //Push the names from the Image objects into image array
                JSON.parse(data.body).map((item) => {
                    randomImagesArray.push(item);
                });
                setRandomImages(randomImagesArray);
            });
        });
    }, []);

    function getImages(level) {
        stompClient.current.send("/app/getobjects", {}, level);
    }
    function getRandomObjects(level) {
        console.log(typeof level, 'AA')
        stompClient.current.send("/app/getrandomobjects", {}, level);
    }


    function timerOver() {
        Array.from(document.getElementsByClassName("ho-topButtons")).forEach((el) => {
            el.style.visibility = "hidden";
        })

        Array.from(document.getElementsByClassName("ho-bottomButtons")).forEach((el) => {
            el.style.visibility = "visible";
        })

        document.getElementById("timer").style.visibility = "hidden";
    }

    function checkButton(e) {
        let value = e.target.value;
        if (randomImagesArray.includes(value)) {
            document.getElementById(value).style.visibility = "hidden";
            correctObjects.push(value);
            setCorrectRender(correctObjects);
            if (correctObjects.length === level + 2) {
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
                                window.location.replace("/hiddenobject?lvl=5");
                                break;
    
                            default:
                                window.location.replace("/visual"); 
                                break;
                        }
                    });
                }
                else{
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
                                window.location.replace("/visual"); //ose nashta e bon me shku te /levels?game="loja" or whatever
                                break;
    
                            case "next":
                                window.location.replace("/hiddenobject?lvl=" + (level + 1));
                                break;
    
                            default:
                                window.location.replace("/visual"); //ose nashta e bon me shku te /levels?game="loja" or whatever
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
                    button: constants.TRY_AGAIN,
                });
            }
        }
    }

    function recordScore() {
        stompClient.current.send(
            "/app/ho_recordscore",
            {},JSON.stringify(
            {
                name: cookies.get("currentUser"),
                level: level,
                missed: mistakes,
                points: 100,
            })
        );
    }

    return (
        <div className="container-fluid">
            <Navbar />
            <div className="row">
                <div className="col-md-3">
                    <Menu
                        level={levelRender}
                        playerName={cookies.get("username")}
                        instructions={constants.MEMORYQUEST_INSTRUCTIONS}
                        pause={() => { setPause(value => !value) }}
                    ></Menu>
                </div>
                <div className="col-md-6 text-center all_words mt-1">
                    <div className="row justify-content-center">
                        {randomImages.map((item, index) => {
                            if (
                                index % 2 === 1 &&
                                index < randomImages.length - 2
                            ) {
                                let path = item.split("/")[3];
                                return (
                                    <button
                                        value={item}
                                        id="random"
                                        className="m-2 ho-topButtons"
                                        style={{
                                            backgroundImage:
                                                "url(" +
                                                require("../../photographs/supermarket/" +
                                                    path) +
                                                ")",
                                            zIndex: "2",
                                            height: "68px",
                                            width: "68px",
                                            backgroundPosition: "center",
                                        }}
                                    ></button>
                                );
                            }
                        })}
                    </div>
                    <div className="row mt-5 justify-content-center">
                        <img
                            src={background}
                            style={{ zIndex: "1", position: "absolute" }}
                        />
                        <div
                            style={{ zIndex: "2", width: "90%" }}
                            className="mt-2"
                        >
                            {images.map((item, index) => {
                                if (index % 2 === 1) {
                                    let path = item.split("/")[3];
                                    return (
                                        <button
                                            value={item}
                                            className="m-2 ho-bottomButtons"
                                            id={item}
                                            style={{
                                                backgroundImage:
                                                    "url(" +
                                                    require("../../photographs/supermarket/" +
                                                        path) +
                                                    ")",
                                                zIndex: "2",
                                                height: "68px",
                                                width: "68px",
                                                backgroundPosition: "center",
                                                visibility: "hidden"
                                            }}
                                            onClick={checkButton}
                                        ></button>
                                    );
                                }
                            })}
                        </div>
                    </div>
                </div>
                <div className="col-md-3 mt-5">
                    <div className="mistakes ml-5 mt-5">
                        {constants.INCORRECT_2}
                        <div className="mt-3">
                            <span className="border p-3">{mistakesRender}</span>{" "}
                            {constants.FROM}{" "}
                            <span className="border p-3">{level + 2}</span>
                        </div>
                    </div>

                    {images.length > 0 ? <div className="col-md-3" id="timer"><br /><br /><br /><Timer timer={5 + 5 * level} onTimerFinish={timerOver} /></div> : null}
                </div>
            </div>
        </div>
    );
}
