import React, { useEffect, useState, useRef} from "react";
import "bootstrap/dist/css/bootstrap.css";
import "../../assets/stylesheets/main_styles.css";
import "./style.css";
import * as Stomp from "stompjs";
import SockJS from "sockjs-client";
import Player from '../player/player';
import Level from '../level/level';
import Menu from '../menu/menu';
import swal from 'sweetalert';
import Sidebar from '../sidebar/sidebar';
import Navbar from "../navigation/Navbar";
import Cookies from "universal-cookie";



let submittedFieldID = " ";
let buttonarray = [];
let completeButtonArray = [];
let word = "";
let constants;
let link = window.location.toString();
let level = Number(link.split("=")[1]);



export default function ClientComponent() {

    const socket = useRef(new SockJS('/brainbright-websocket'));
    const stompClient = useRef(Stomp.over(socket.current));
    const cookies = new Cookies();


    if (cookies.get("language") === "en") {
        constants = require("../../assets/metatext/constants");
    } else {
        constants = require("../../assets/metatext/constantsgr");
    }
    const [pause, setPause] = useState(false);

    //show on screen the level
    const [levelRender, setLevelRender] = useState(Number(level));
    const [correctWordsRender, setCorrectWordsRender] = useState([]);
    const [letters, setLetters] = useState([]);
    let [wordRender, setWordRender] = useState();

    let correctWords = 0;
    let correctWordArray = new Array();
    let letterBox = new Array(9);

    useEffect(() => {
            stompClient.current.connect({}, function () {

                this.subscribe('/user/topic/wordex_get_letters', function (data) {
                    let letterArray = new Array();
                    JSON.parse(data.body).map(letter => {
                        letterArray.push(letter);
                    })
                    setLetters(letterArray);

                });
                this.subscribe('/user/topic/wordex_validactionresponse', function (data) {
                    console.log(data.body, "test");
                    if (JSON.parse(data.body) === 0) {

                    } else {  //if correct result

                        // setEndResult('You are correct');


                        //If game continues after result
                        if (JSON.parse(data.body) === 1) {
                            setCorrectWordsRender(correctWordsRender => [...correctWordsRender, word]);
                        } else if (JSON.parse(data.body) === 2) { //level up
                            // swal(constants.FINISHED_LEVEL_LABEL, "", "success", {
                            //     button: constants.CONTINUE_BTN,
                            //   }).then(function(){
                            //     level++;
                            //     console.log(level, 'sending level');
                            //     getWordx(stompClient.current, level, true);
                            //     setLevelRender(level=>level+1);
                            //     setCorrectWordsRender([]);
                            // });
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
                                            window.location.replace("/verba;"); 
                                            break;
    
                                        case "next":
                                            window.location.replace("/wordex?lvl=5");
                                            break;
    
                                        default:
                                            window.location.replace("/verbal"); 
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
                                            window.location.replace("/verba;"); //ose nashta e bon me shku te /levels?game="loja" or whatever
                                            break;
    
                                        case "next":
                                            window.location.replace("/wordex?lvl=" + (level + 1));
                                            break;
    
                                        default:
                                            window.location.replace("/verbal"); //ose nashta e bon me shku te /levels?game="loja" or whatever
                                            break;
                                    }
                                });
                            }
                            
                            setCorrectWordsRender(correctWordsRender => [...correctWordsRender, word]);
                        }
                    }
                    buttonarray = [];
                    completeButtonArray = [];
                });
                console.log('sending level', level);
                getWordx(this, level, true);
            });
    }, []);


    function getWordx(client, level, isNew) {
        client.send("/app/wordex_get_letters", {}, JSON.stringify({ "level": level, "isNew": isNew, userID: cookies.get("currentUser"), language: cookies.get("language") }));
    }

    function getButtonContent(e) {
        //Push the new item into the array of buttons
        let buttonValue = e.target.innerHTML;
        buttonarray.push(buttonValue);
        completeButtonArray.push(e.target);

        setWordRender(buttonarray.join(''));
        // console.log(buttonarray)
        word = buttonarray.join('');
        // console.log(word);
        e.target.disabled = 'true';
    }

    function sendWord() {
        // console.log("sending to back ", word);
        stompClient.current.send("/app/wordex_validaction", {}, JSON.stringify({ "word": word }));
        clearAll();

    }

    function clearAll() {
        setWordRender();
        buttonarray = [];
        completeButtonArray = [];
        let buttons = document.querySelectorAll('.word-button');
        Object.keys(buttons).map(function(key, index) {
            buttons[key].removeAttribute("disabled");
          });
    }

    function clearLast() {
        setWordRender(wordRender => wordRender.slice(0, -1));
        buttonarray.pop();
        completeButtonArray.pop().removeAttribute("disabled");
        
    }

    return (
        <div className="container-fluid">
            <Navbar />
            <div className="row">
                <div className="col-md-3" id="player">
                    <Menu level={levelRender} playerName={cookies.get("username")} instructions={constants.WORDEX_INSTRUCTIONS} pause={() => { setPause(value => !value) }}></Menu>
                </div>

                <div className="col-md-6 text-center all_words mt-3">

                    <div className="row">
                        {letters.map(letter =>
                            <div className="letterButton col-md-4 mb-2">
                                <button className="btn btn-primary word-button " onClick={getButtonContent}>
                                    {letter}
                                </button>
                            </div>
                        )}
                    </div>

                    <div className="result p-3 mt-5 rounded">{wordRender}</div>

                    <div className="mt-2">
                        <button className="btn pl-5 pr-5 pt-2 pb-2 mt-3" onClick={clearAll}>{constants.CLEAR_BTN}</button>
                        <button className="btn pl-5 pr-5 pt-2 pb-2 mt-3" onClick={clearLast}>{constants.CORRECTION_BTN}</button>
                        <button className="btn pl-5 pr-5 pt-2 pb-2 mt-3" onClick={sendWord}>{constants.VALIDATION_BTN}</button>
                    </div>

                </div>


                <div className="col-md-3">
                    <div className="text-center">
                        <Sidebar level={levelRender} correctWords={correctWordsRender} elements={levelRender * 3 + 3}></Sidebar>
                    </div>

                </div>

            </div>
        </div>
    )
}

