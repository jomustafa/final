import React, {useRef, useEffect, useState } from "react";
import * as Stomp from "stompjs";
import SockJS from "sockjs-client";
import Player from "../player/player";
import Level from "../level/level";
import Menu from "../menu/menu";
import swal from "sweetalert";
import Sidebar from "../sidebar/sidebar";
import "./style.css";
import Navbar from "../navigation/Navbar";
import Cookies from "universal-cookie";
import Timer from "../sidebar/timer";



let link = window.location.toString();
let level = Number(link.split("=")[1]);
let subLevel = 0;
let timeAllotted = [180, 170, 150, 100, 80];
let correctSquares = 0;
let wrongSquares = 0;

// function getNapCharacter(client, level, isNew) {
//     client.send("/app/nap_getcharacter", {}, JSON.stringify({ "level": level, "isNew": isNew }));
// }

//Array of Pattern objects (Pattern object = Size s, Patterns[])
let allGames = [];
const cookies = new Cookies();
let constants;

export default function ClientComponent() {

    const socket = useRef(new SockJS('/brainbright-websocket'));
    const stompClient = useRef(Stomp.over(socket.current));

    if (cookies.get("language") === "en") {
        constants = require("../../assets/metatext/constants");
    } else {
        constants = require("../../assets/metatext/constantsgr");
    }

    const [currentMatrix, setCurrentMatrix] = useState();
    const [currentPattern, setCurrentPattern] = useState([]);
    //show on screen the level
    const [levelRender, setLevelRender] = useState(Number(level));
    const [endResult, setEndResult] = useState(" ");
    const [pause, setPause] = useState(false);
    //const [correctWordsRender, setCorrectWordsRender] = useState([]);

    useEffect(() => {
            stompClient.current.connect({}, function () {
                

                this.subscribe("/user/topic/patternlist", function (data) {
                    hideButtons();
                    //Store all the pattern objects, which have the size and the correct patterns
                    allGames = JSON.parse(data.body);
                    setCurrentMatrix(allGames[subLevel]);
                    let array = new Array(
                        Math.pow(allGames[subLevel].size, 2)
                    ).fill(" ");

                    setCurrentPattern(array);
                    highlightButtons(allGames[subLevel]);
                    changeButtonState(true);
                    setTimeout(() => {
                        hideButtons();
                        changeButtonState(false);
                    }, 3000);
                });
                getPatterns(level);

                // if (level == 1) {
                //     getNapCharacter(this, 1, true);
                // }
            });
        
    }, []);

    function changeButtonState(value) {
        let buttons = document.getElementsByClassName("fpbutton");
        Array.from(buttons).forEach((el) => {
            el.disabled = value;
        });
    }

    function hideButtons() {
        let buttons = document.getElementsByClassName("fpbutton");
        Array.from(buttons).forEach((el) => {
            el.style.backgroundColor = "white";
        });
    }

    function highlightButtons(game) {
        let buttons = document.getElementsByClassName("fpbutton");
        game.pattern.map((item) => {
            document.getElementById(
                item[0] * game.size + item[1]
            ).style.backgroundColor = "#40c4ff";
        });
    }

    function getPatterns(level) {
        stompClient.current.send(
            "/app/getpatterns",
            {},
            JSON.stringify({ level: level, id: cookies.get("currentUser") })
        );
    }
    
    function buttonClicked(e) {
        let clickedButton = e.target;
        let guessed = false;
        currentMatrix.pattern.forEach((el) => {
            console.log(el[0] * allGames[subLevel].size + el[1]);
            console.log("id: " + e.target.id);
            if (el[0] * allGames[subLevel].size + el[1] == e.target.id) {
                guessed = true;
            }
        });

        if (guessed) {
            clickedButton.style.backgroundColor = "#40c4ff";
            clickedButton.disabled = true;
            correctSquares++;
        } else {
            setEndResult(constants.INCORRECT);
            document.getElementById("resultPartText").style.backgroundColor =
                "#DC143C";
            correctSquares = 0;
            wrongSquares++;
            if (subLevel < allGames.length - 1) {
                changeButtonState(true);
                highlightButtons(allGames[subLevel]);
                subLevel++;
                setCurrentMatrix(allGames[subLevel]);
                let array = new Array(
                    Math.pow(allGames[subLevel].size, 2)
                ).fill(" ");
                setCurrentPattern(array);
                setTimeout(hideButtons, 1000);
                setTimeout(() => {
                    highlightButtons(allGames[subLevel]);
                }, 2000);
                setTimeout(() => {
                    hideButtons();
                    changeButtonState(false);
                }, 5000);
                //changeButtonState(false);
            } else {
                finishLevel();
            }
        }

        if (correctSquares === allGames[subLevel].pattern.length) {
            correctSquares = 0;
            subLevel++;
            setEndResult(constants.CORRECT);
            document.getElementById("resultPartText").style.backgroundColor =
                "green";
            if (subLevel < allGames.length) {
                changeButtonState(true);

                setCurrentMatrix(allGames[subLevel]);
                let array = new Array(
                    Math.pow(allGames[subLevel].size, 2)
                ).fill(" ");
                setCurrentPattern(array);
                setTimeout(hideButtons, 200);
                setTimeout(() => {
                    highlightButtons(allGames[subLevel]);
                }, 1000);
                setTimeout(() => {
                    hideButtons();
                    changeButtonState(false);
                }, 3000);
                //changeButtonState(false);
            } else {
                finishLevel();
            }
        }
    }

    function recordScore() {
        stompClient.current.send(
            "/app/fp_recordscore",
            {}, JSON.stringify(
                {
                    name: cookies.get("currentUser"),
                    level: level,
                    missed: wrongSquares,
                    points: (10 - wrongSquares) * 10,
                })
        );
    }

    function timerOver() {
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
                    window.location.replace("/photographic");
                    break;

                case "retry":
                    window.location.reload();
                    break;

                default:
                    window.location.replace("/photographic");
                    break;
            }
        });
    }

    function finishLevel() {
        subLevel = 0;

        recordScore();
        correctSquares = 0;
        if (wrongSquares > 3) {
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
        } else {
            // swal(constants.FINISHED_LEVEL_LABEL, "", "success", {
            //     button: constants.CONTINUE_BTN,
            // }).then(function () {
            //     level++;
            //     wrongSquares = 0;
            //     getPatterns(level);
            //     setLevelRender((level) => level + 1);
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
                            window.location.replace("/visual"); //ose nashta e bon me shku te /levels?game="loja" or whatever
                            break;

                        case "next":
                            window.location.replace("/findpatterns?lvl=5");
                            break;

                        default:
                            window.location.replace("/visual"); //ose nashta e bon me shku te /levels?game="loja" or whatever
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
                            window.location.replace("/visual"); //ose nashta e bon me shku te /levels?game="loja" or whatever
                            break;

                        case "next":
                            window.location.replace("/findpatterns?lvl=" + (level + 1));
                            break;

                        default:
                            window.location.replace("/visual"); //ose nashta e bon me shku te /levels?game="loja" or whatever
                            break;
                    }
                });
            }

        }


    }
    return (
        <div className="container-fluid">
            <Navbar></Navbar>
            <div className="row">
                <div className="col-md-3">
                    <Menu
                        level={levelRender}
                        playerName={cookies.get("username")}
                        instructions={constants.FINDPATTERNS_INSTRUCTIONS}
                        pause={() => { setPause(value => !value) }}
                    ></Menu>
                </div>
                <div className="col-md-6 text-center all_words">
                    <div className="row">
                        {currentPattern.map((item, index) => (
                            <div
                                style={{
                                    width:
                                        100 / Math.sqrt(currentPattern.length) +
                                        "%",
                                    height: "200px",
                                    border: "2px solid black",
                                }}
                                className={"col-xs-1"}
                                key={index}
                            >
                                <button
                                    style={{ height: "100%" }}
                                    className="btn btn-light mb-0 p-3 rounded border-0 fpbutton"
                                    onClick={buttonClicked}
                                    id={index}
                                ></button>
                            </div>
                        ))}
                    </div>
                    <div className="mt-3" id="resultPartText">
                        {endResult}
                    </div>
                </div>

                {currentPattern.length > 0 ? <div className="col-md-3" id="timer"><br /><br /><br /><Timer timer={timeAllotted[level - 1]} onTimerFinish={timerOver} /></div> : null}

            </div>
        </div>
    );
}
