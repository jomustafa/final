import React, { useEffect, useState, useRef} from "react";
import "./style.css";
import * as Stomp from "stompjs";
import SockJS from "sockjs-client";
import Player from '../player/player';
import Level from '../level/level';
import Menu from '../menu/menu';
import swal from 'sweetalert';
import Sidebar from '../sidebar/sidebar';
import { server } from "websocket";
import Navbar from "../navigation/Navbar";
import Cookies from "universal-cookie";

let i = 0;
let buttonarray = [];
let matrixLength = 0;
// function getNapCharacter(client, level, isNew) {
//     client.send("/app/nap_getcharacter", {}, JSON.stringify({ "level": level, "isNew": isNew }));
// }
let constants;
let link = window.location.toString();
let level = Number(link.split("=")[1]);

export default function ClientComponent() {
    //When switching menus, need to connect here instead
    const cookies = new Cookies();
    const socket = useRef(new SockJS('/brainbright-websocket'));
    //const socket = useRef(new SockJS('http://localhost:8080/brainbright-websocket'));
    const stompClient = useRef(Stomp.over(socket.current));
    if (cookies.get("language") === "en") {
        constants = require("../../assets/metatext/constants");
    } else {
        constants = require("../../assets/metatext/constantsgr");
    }

    const [wordMatrix, setWordMatrix] = useState([]);
    const [countryList, setCountryList] = useState([]);
    //show on screen the level
    const [levelRender, setLevelRender] = useState(Number(level));
    const [userResponse, setUserResponse] = useState(constants.SELECT_THE_CORRECT_WORD);
    const [pause, setPause] = useState(false);
    //const [correctWordsRender, setCorrectWordsRender] = useState([]);

    useEffect(() => {
            stompClient.current.connect({}, function () {

                getWordMatrix(this, level);

                this.subscribe('/user/topic/ws_countries', function (data) {
                    console.log("countries:" + data.body);
                    setCountryList(JSON.parse(data.body));
                    let buttons = [...document.getElementsByClassName("resultrow")];
                    console.log(buttons);
                    buttons.map(b =>
                        b.style.backgroundColor = "white"
                    );
                });

                this.subscribe('/user/topic/ws_matrix', function (data) {
                    getCountries(stompClient.current);
                    let array = new Array();
                    JSON.parse(data.body).map((item) => {
                        item.map((item2) => {
                            array.push(item2);
                        })
                    });
                    matrixLength = array.length;
                    setWordMatrix(array);
                });
                // if (level == 1) {
                //     getNapCharacter(this, 1, true);
                // }
                this.subscribe('/user/topic/ws_validactionresponse', function (data) {
                    let serverResponse = JSON.parse(data.body);
                    if (serverResponse[0] == 0) {
                        console.log(data.body);
                        setUserResponse("Incorrect word!");
                    } else if (serverResponse[0] == 1) {
                        setUserResponse("Correct word!");
                        disableButtons();
                        document.getElementById(serverResponse[1]).style.backgroundColor = "green";

                        //correctWordArray.push(document.getElementById(submittedFieldID).value);
                        // NOT SURE ABOUT THIS ONE :    setCorrectWordsRender(correctwrd=>[...correctwrd, document.getElementById(submittedFieldID).value]);
                    }
                    else if (serverResponse[0] == 2) { // to level up
                        setUserResponse("Correct word!");
                        disableButtons();
                        document.getElementById(serverResponse[1]).style.backgroundColor = "green";
                        if (level === 5) {
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
                                        window.location.replace("/verbal"); //ose nashta e bon me shku te /levels?game="loja" or whatever
                                        break;

                                    case "next":
                                        window.location.replace("/wordsearch?lvl=5");
                                        break;

                                    default:
                                        window.location.replace("/verbal"); //ose nashta e bon me shku te /levels?game="loja" or whatever
                                        break;
                                }
                            });
                        }
                        else {
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
                                        window.location.replace("/verbal"); //ose nashta e bon me shku te /levels?game="loja" or whatever
                                        break;

                                    case "next":
                                        window.location.replace("/wordsearch?lvl=" + (level + 1));
                                        break;

                                    default:
                                        window.location.replace("/verbal"); //ose nashta e bon me shku te /levels?game="loja" or whatever
                                        break;
                                }
                            });
                        }

                        //NOT SURE ABOUT THIS ONE : setCorrectWordsRender(correctwrd=>[...correctwrd, document.getElementById(submittedFieldID).value]);
                    } else if (serverResponse[0] == 3) {
                        setUserResponse(constants.WRONG_INPUT_BODY);
                    }

                    buttonarray = [];
                });
            });
    }, []);

    function getWordMatrix(client, level) {
        i = 0;
        client.send("/app/ws_getmatrix", {}, JSON.stringify({ level: level, id: cookies.get("currentUser"), language: cookies.get("language") }));
        let buttons = [...document.getElementsByClassName("wsbutton")];
        buttons.map(item => {
            item.style.backgroundColor = "";
        })
    }

    function getCountries(client) {
        client.send("/app/ws_getcountries", {}, level);
    }

    function disableButtons() {
        //IF THEY HAVE THE SAME Y, SAME COLUMN
        let startRow = buttonarray[0];
        let startCol = buttonarray[1];
        let endRow = buttonarray[2];
        let endCol = buttonarray[3];
        let numRows = Math.sqrt(matrixLength);
        //Loop until the startrow and startcol are the same as the endrow and endcol
        while (startRow !== endRow || startCol !== endCol) {
            document.getElementById((startRow * numRows) + startCol).style.backgroundColor = "grey";
            console.log((startRow * numRows) + startCol);
            if (endRow > startRow) {
                startRow++;
            } else if (endRow < startRow) {
                startRow--;
            }
            if (endCol > startCol) {
                startCol++;
            } else if (endCol < startCol) {
                startCol--;
            }

        }
        document.getElementById((endRow * numRows) + endCol).style.backgroundColor = "grey";

    }
    function getButtonContent(e) {
        //Push the new item into the array of buttons
        let numRows = Math.sqrt(wordMatrix.length);
        let y = Math.floor(e.target.id / numRows);
        let x = e.target.id % numRows;
        console.log(y);
        console.log(x);
        buttonarray.push(y);
        buttonarray.push(x);


        //let textArray = splitWordText;

        //If it's 2 buttons clicked, send the request
        if (buttonarray.length === 4) {
            stompClient.current.send("/app/ws_validaction", {}, JSON.stringify({ "startRow": buttonarray[0], "startCol": buttonarray[1], "endRow": buttonarray[2], "endCol": buttonarray[3] }));

        }
    }

    return (
        <div className="container-fluid" style={{height:"100vh"}}>
            <Navbar />
            <div className="row">
                <div className="col-md-3">
                    <Menu level={levelRender} playerName={cookies.get("username")} instructions={constants.WORDSEARCH_INSTRUCTIONS} pause={() => { setPause(value => !value) }}></Menu>
                </div>
                <div className="col-md-6 text-center all_words" style={{height:"50%"}}>
                    <div className="row">
                        {
                            wordMatrix.map((item, index) =>
                                <div style={{ width: 100 / Math.sqrt(wordMatrix.length) + "%",height: 100 / Math.sqrt(wordMatrix.length) + "%" }} className={"col-xs-1"} key={index}>

                                    <button style={{padding:0}} className="btn btn-light mb-0 rounded border-0 wsbutton" onMouseDown={getButtonContent} onMouseUp={getButtonContent} id={index}>{item}</button>
                                </div>
                            )
                        }

                    </div>
                    <div className="mt-3">{userResponse}</div>
                    <button className="btn pl-5 pr-5 pt-2 pb-2 mt-3 gamebutton">{constants.CLEAR_BTN}</button>

                </div>
                <div className="col-md-3">

                    <Sidebar level={levelRender} correctWords={countryList} elements={0}></Sidebar>
                </div>

            </div>
        </div>
    )
}

