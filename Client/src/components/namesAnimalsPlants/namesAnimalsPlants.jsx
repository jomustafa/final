import React, { useEffect, useState, useRef } from "react";
import splitWordStyle from "./style.css";
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
let link = window.location.toString();
let level = Number(link.split("=")[1]);

let alphabet;
let englishAlpha = ["A", "B", "G", "D", "E", "Z", "H", "U", "I", "K", " ", "Q", "L", "M", "N", "J", "O", "P", "R", "S", "W", " ", "T", "Y", "F", "X", "C", "V", "Q"];
let greekAlpha = ["A", "B", "Γ", "Δ", "Ε", "Ζ", "Η", "Θ", "Ι", "Κ", " ", "Λ", "Μ", "Ν", "Ξ", "Ο", "Π", "Ρ", "Σ", " ", "Τ", "Υ", "Φ", "Χ", "Ψ", "Ω"];


let constants;
let currentField;
export default function ClientComponent() {
    const cookies = new Cookies();

    if (cookies.get("language") === "en") {
        constants = require("../../assets/metatext/constants");
        alphabet = englishAlpha;
    } else {
        constants = require("../../assets/metatext/constantsgr");
        alphabet = greekAlpha;
    }
  
    const socket = useRef(new SockJS('/brainbright-websocket'));
    const stompClient = useRef(Stomp.over(socket.current));

    const [napCharacter, setNapCharacter] = useState('');
    const [categories, setCategories] = useState(["plant", "animal", "occupation", "name", "country"]);

    //show on screen the level
    const [levelRender, setLevelRender] = useState(Number(level));
    const [correctWordsRender, setCorrectWordsRender] = useState([]);
    const [pause, setPause] = useState(false);
    let correctWords = 0;
    let correctWordArray = new Array();
    let sublevel = 0;
    useEffect(() => {
        stompClient.current.connect({}, function () {

            this.subscribe('/user/topic/nap_getcharacterresponse', function (data) {
                console.log(data.body);
                setNapCharacter(data.body);
            });


            getNapCharacter(level, true);

            this.subscribe('/user/topic/nap_validactionresponse', function (data) {
                if (data.body == 0) {
                    document.getElementById("result").innerHTML = constants.WRONG;
                    document.getElementById(submittedFieldID).value = "";
                } else if (data.body == 1) {
                    document.getElementById("result").innerHTML = constants.CORRECT;
                    document.getElementById(submittedFieldID).disabled = true;
                    document.getElementById(submittedFieldID).style.backgroundColor = "lightgreen";
                    currentField = null;
                    correctWords++;
                    //correctWordArray.push(document.getElementById(submittedFieldID).value);
                    setCorrectWordsRender(correctwrd => [...correctwrd, document.getElementById(submittedFieldID).value]);
                }
                else if (data.body == 2) { // to level up
                    // swal(constants.FINISHED_LEVEL_LABEL, "", "success", {
                    //     button: constants.CONTINUE_BTN,
                    //   }).then(function () {
                    //     setCorrectWordsRender([]);
                    //     setLevelRender(levelRender => levelRender + 1);
                    //     getNapCharacter(level, true);
                    // });
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
                                    window.location.replace("/verbal");
                                    break;

                                case "next":
                                    window.location.replace("/animals?lvl=5");
                                    break;

                                default:
                                    window.location.replace("/verbal");
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
                                    window.location.replace("/verbal");
                                    break;

                                case "next":
                                    window.location.replace("/animals?lvl=" + (level));
                                    break;

                                default:
                                    window.location.replace("/verbal");
                                    break;
                            }
                        });
                    }
                    correctWords++;

                    setCorrectWordsRender(correctwrd => [...correctwrd, document.getElementById(submittedFieldID).value]);
                }

                //logic to change levels

                if (correctWords % 5 === 0 && correctWords > sublevel) {
                    sublevel += 5;
                    categories.map(category => {
                        document.getElementById(category).value = '';
                        document.getElementById(category).disabled = false;
                        document.getElementById(category).style.backgroundColor = "";
                    })
                    if (correctWords / level === 5) {
                        level++;
                        correctWords = 0;

                    } else {
                        getNapCharacter(level, false);
                    }
                }
            });
        });

    }, []);

    function getNapCharacter(level, isNew) {
        stompClient.current.send("/app/nap_getcharacter", {}, JSON.stringify({ "level": level, "isNew": isNew, "id": cookies.get("currentUser"), "language": cookies.get("language") }));
    }

    function isValidAction(event) {
        if (event.keyCode === 13) {
            submittedFieldID = event.target.id;
            console.log(submittedFieldID);
            let word = document.getElementById(submittedFieldID).value.toUpperCase();
            stompClient.current.send("/app/nap_validaction", {}, JSON.stringify({ "word": word, "type": event.target.id }));
        }
    }
    return (
        <div className="container-fluid">

            <Navbar />

            <div className="row">
                <div className="col-md-3" id="player">
                    {/* <Player name="PlayerName"></Player>
                    <Level level={levelRender}></Level> */}
                    <Menu level={levelRender} playerName={cookies.get("username")} instructions={constants.NAMESANIMALSPLANTS_INSTRUCTIONS} pause={() => { setPause(value => !value) }}></Menu>
                </div>

                <div className="col-md-6 text-center all_words">
                    <div className="starting-character p-2 ">{
                        constants.WORD_STARTS_FROM + " "
                    }</div>
                    <div className="napCharacter">{napCharacter}
                        <br />
                        <br />
                    </div>
                    <div className="row">
                        {
                            categories.map((item) =>
                                <div className="col-md-6 col-sm-6" key={item.word}>
                                    {item !== ' ' ? <p>{constants[item.toUpperCase()]}</p> : <br></br>}
                                    <label>
                                        <input type="text" class="form-control" name="name" id={item} autoComplete="off" onFocus={(e) => { currentField = e.target; console.log(currentField) }} onKeyDown={isValidAction} />
                                    </label>
                                </div>
                            )
                        }
                    </div>

                    <div className="guideComment rounded p-3 text-white" id="result" style={{ backgroundColor: "#6c757d" }}>{constants.PRESS_ENTER}</div>

                    <div>
                        {
                            alphabet.map((item) =>
                                item != " " ?
                                    <button className="btn_hangman bigger_text" onClick={(e) => { if (currentField) { e.preventDefault(); console.log(currentField); currentField.value = currentField.value + item; currentField.focus() } }} id={item}>{item}</button>
                                    :
                                    <br />
                            )
                            /*cookies.get("language")==="en" ? //maybe punon must test.
                            englishAlpha.map((item) =>
                              item!=" "?
                                <button className="btn_hangman" onClick={getButtonContent} id={item} disabled={disabled}>{item}</button>
                              :
                                <br/>
                            )
                            :
                            greekAlpha.map((item) =>
                              item!=" "?
                                <button className="btn_hangman" onClick={getButtonContent} id={item} disabled={disabled}>{item}</button>
                              :
                                <br/>
                            )*/

                        }
                    </div>


                </div>

                <div className="col-md-3">
                    <div className="text-center">
                        <Sidebar level={levelRender} correctWords={correctWordsRender} elements={5 * levelRender}></Sidebar>
                    </div>

                </div>

            </div>
        </div>
    )
}

