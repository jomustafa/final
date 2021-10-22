import React, {useRef, isValidElement, useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.css";
import "../../assets/stylesheets/main_styles.css";
import * as Stomp from "stompjs";
import SockJS from "sockjs-client";
import Player from '../player/player';
import Level from '../level/level';
import Menu from '../menu/menu';
import swal from 'sweetalert';
import Sidebar from '../sidebar/sidebar';
import Navbar from "../navigation/Navbar";
import Cookies from "universal-cookie";
import "./style.css"
import Timer from "../sidebar/timer";


let buttonarray = [];
let correctWords = 0;
let link = window.location.toString();
let level = Number(link.split("=")[1]);
let missed = 0;

export default function ClientComponent() {
    const cookies = new Cookies();

    const socket = useRef(new SockJS('/brainbright-websocket'));
    const stompClient = useRef(Stomp.over(socket.current));

    let constants;
    if(cookies.get("language") === "en"){
        constants = require('../../assets/metatext/constants');
    }else{
        constants = require('../../assets/metatext/constantsgr')
    }
    //The Image objects (Image Class: String Name, String URL)
    const [images, setImages] = useState([]);
    //TextBox at the bottom showing correct/wrong
    const [endResult, setEndResult] = useState('');
    const [levelRender, setLevelRender] = useState(Number(level));
    const [pause, setPause] = useState(false);



    useEffect(() => {
        stompClient.current.connect({}, function () {

            //Executed whenever a list of words is received
            this.subscribe('/user/topic/imagelist', function (data) {
                console.log(data.body);
                //Change all the image boxes to visible, used in order to reset the buttons after a levelup
                let buttons = [...document.getElementsByClassName("findpairbutton")];
                console.log(buttons);
                console.log(document.getElementsByClassName("findpairbutton"));
                buttons.map(el => {
                    el.style.visibility = "visible";
                    el.style.opacity = 1.0;
                    el.disabled = false;
                });

                // document.getElementById("timer").style.visibility = "visible";
                // let sidebar = document.getElementsByClassName("resultrow");
                // Array.from(sidebar).forEach(el => {
                //   el.innerHTML = " ";
                // });


                //Push the names from the Image objects into image array
                let array = [];
                JSON.parse(data.body).map((item) => {
                    array.push(item);
                });
                setImages(array);

                setTimeout(() => {
                    buttons = [...document.getElementsByClassName("findpairbutton")];
                    buttons.map(item => item.style.backgroundImage = "url(" + require("../../photographs/findpairs/questionmark.png") + ")");
                }, 5000);
            });
            getImages(level);
        });
    }, []);

    function getImages(level) {
        console.log(level);
        stompClient.current.send("/app/getimages", {}, level);
    }

    function performAction(response) {
        //If incorrect word

        //Asynchronous array for frontend stuff, which lets us empty button array for functional stuff
        let temparray = buttonarray;
        buttonarray = [];
        if (response === 0) {
            setEndResult(constants.WRONG);
            missed++;
            setTimeout(() => {
                temparray.map(item => {
                    item.disabled = false;
                    item.style.backgroundImage = "url(" + require("../../photographs/findpairs/questionmark.png") + ")";
                });

            }, 1000);


        } else {  //if correct result
            setEndResult(constants.CORRECT);

            //Hide buttons of correct guesses
            setTimeout(() => {
                temparray.map(item => {
                    item.style.visibility = "hidden";
                });

            }, 1000);
            // const result = buttonarray[0] + buttonarray[1];

            //If game continues after result
            if (response === 2) { //2 = gameOver
                // swal(constants.FINISHED_LEVEL_LABEL, "", "success", {
                //     button: constants.CONTINUE_BTN,
                //   }).then(function () {
                //     //stompClient.current.send("/app/fp_recordScore", {}, JSON.stringify({"name":cookies.get("currentUser"), "level":level, "missed":missed}));
                //     level++;
                //     window.location.replace("/findpairs?lvl="+level);
                //     // getImages(level);
                //     // setLevelRender(level => level + 1);
                //     // missed = 0;
                // });
                stompClient.current.send("/app/fp_recordScore", {}, JSON.stringify({"name":cookies.get("currentUser"), "level":level, "missed":missed}));
                if(level === 5){
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
                                window.location.replace("/findpairs?lvl=5");
                                break;
                
                            default:
                                window.location.replace("/visual"); //ose nashta e bon me shku te /levels?game="loja" or whatever
                                break;
                        }
                    });
                }
                else{
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
                                window.location.replace("/findpairs?lvl="+(level+1));
                                break;
                
                            default:
                                window.location.replace("/visual"); //ose nashta e bon me shku te /levels?game="loja" or whatever
                                break;
                        }
                    });
                }
                correctWords = 0;
                // missed = 0;
            }


        };
    }

    function clearContent() {
        buttonarray = [];
    }



    function getButtonContent(item) {
        //Push the new item into the array of buttons
        item.persist();
        buttonarray.push(item.target);
        console.log();
        console.log(item.target);
        //"url("+require("../../photographs/findpairs/questionmark.png")+")");
        item.target.style.backgroundImage = "url(" + require("../../photographs/findpairs/" + item.target.getAttribute("value") + ".png") + ")";
        item.target.disabled = true;
        item.target.onclick = "";
        // setTimeout(() => {
        //If it's 2 buttons clicked, send the request
        let action = "";
        if (buttonarray.length === 2) {
            if (buttonarray[0].getAttribute("value") === buttonarray[1].getAttribute("value")) {
                correctWords = correctWords + 2;

                if (correctWords === images.length) {
                    action = 2;
                } else {
                    action = 1;
                }
            } else {
                action = 0;
            }
            performAction(action);
        }
        // }, 200);
    }

    return (
        <div className="container-fluid">
            <Navbar />
            <div className="row">
                <div className="col-md-3">
                    <Menu level={levelRender} playerName={cookies.get("username")} instructions={constants.FINDPAIRS_INSTRUCTIONS} pause={()=>{setPause(value=>!value)}}></Menu>
                </div>
                <div className="col-md-6 text-center all_words">
                    <div className="row">
                        {
                            images.map((item, index, array) =>
                                <div className="col-md-3 col-sm-6" key={(array.length) * 20 + index}>
                                    <button value={item} className="findpairbutton" style={{backgroundImage: "url('/photographs/findpairs/" + item + ".png')"}}
                                        onClick={getButtonContent}></button>
                                </div>
                            )
                        }
                    </div>
                    <div className="mt-3">{endResult}</div>
                    
                    {/* <button
                        className="btn pl-5 pr-5 pt-2 pb-2 mt-3"
                        onClick={clearContent}
                    >
                        Clear
          </button> */}
                </div>
                {images.length>0 ? <div className="col-md-3" id="timer"><Timer  timer={5} onTimerFinish={()=>{document.getElementById("timer").style.visibility="hidden"}}/></div> : null}
            </div>
        </div>
    );
}
