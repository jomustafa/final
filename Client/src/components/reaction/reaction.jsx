import React, { useEffect, useState, useRef } from "react";
import * as Stomp from "stompjs";
import SockJS from "sockjs-client";
import Player from "../player/player";
import Level from "../level/level";
import Menu from "../menu/menu";
import swal from "sweetalert";
import Sidebar from "../sidebar/sidebar";
// import "./style.css"
import Navbar from "../navigation/Navbar";
import Cookies from "universal-cookie";
import Timer from "../sidebar/timer";



let link = window.location.toString();
let level = Number(link.split("=")[1]);
let subLevel = 0;
let startTime;
let times = [];
let timeAllotted = [200, 200, 200, 100, 60];
let clicks = 1;
// let correctSquares = 0;
// let wrongSquares = 0;

// function getNapCharacter(client, level, isNew) {
//     client.send("/app/nap_getcharacter", {}, JSON.stringify({ "level": level, "isNew": isNew }));
// }

//Array of Pattern objects (Pattern object = Size s, Patterns[])
let intervals = [];
let sequence = [];



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
    //When switching menus, need to connect here instead
    const [matrix, setMatrix] = useState([]);
    const [currentPattern, setCurrentPattern] = useState([]);
    //show on screen the level
    const [levelRender, setLevelRender] = useState(Number(level));
    const [pause, setPause] = useState(false);
    const [userResponse, setUserResponse] = useState();
    //const [correctWordsRender, setCorrectWordsRender] = useState([]);

    useEffect(() => {
        stompClient.current.connect({}, function () {
            getSequence(level);

            this.subscribe('/user/topic/re_sequencelist', function (data) {

                //Store all the pattern objects, which have the size and the correct patterns
                let response = JSON.parse(data.body)[0];
                sequence = response.pattern;
                intervals = response.intervals;
                console.log(sequence);
                // setCurrentMatrix(allGames[subLevel]);
                let array = new Array(Math.pow(response.size, 2)).fill(" ");
                setMatrix(array);

            });
        });

    }, []);

    function getSequence(level) {
        stompClient.current.send("/app/re_getsequence", {}, level);
    }

    useEffect(() => {
        if (stompClient.current.connected)
            setTimeout(() => {
                enableButton();
                startTime = Date.now();
            }, intervals[subLevel] * 500);
    }, [matrix]);

    function enableButton() {
        //Blue button
        let coords = sequence[subLevel];
        let blueButtonId = coords[0] * Math.sqrt(matrix.length) + coords[1];
        let button = document.getElementById(blueButtonId);
        button.style.backgroundColor = "#40c4ff";
        console.log(button);
        button.addEventListener("click", nextLevel, false);

        //Red button
        if (level >= 4) {
            let redButtonId = Math.floor(Math.random() * matrix.length);
            while (redButtonId === blueButtonId) {
                // Get a red button with different coords than blue's
                redButtonId = Math.floor(Math.random() * matrix.length);
            }
            let redButton = document.getElementById(redButtonId);
            redButton.style.backgroundColor = "red";
            redButton.classList.add("colored");

            //Green button
            if (level === 5) {
                let greenButtonId = Math.floor(Math.random() * matrix.length);
                while (
                    greenButtonId === redButtonId ||
                    greenButtonId === blueButtonId
                ) {
                    // Get a green button with different coords than blue's/red's
                    greenButtonId = Math.floor(Math.random() * matrix.length);
                }
                let greenButton = document.getElementById(greenButtonId);
                greenButton.style.backgroundColor = "green";
                greenButton.classList.add("colored");
            }
        }
    }

    function nextLevel() {
        clicks--;
        console.log(clicks);
        times.push(Date.now() - startTime);
        console.log(times);
        let coords = sequence[subLevel];
        let button = document.getElementById(
            coords[0] * Math.sqrt(matrix.length) + coords[1]
        );
        button.removeEventListener("click", nextLevel);
        subLevel++;
        if (subLevel < sequence.length) {
            setTimeout(() => {
                enableButton();
                startTime = Date.now();
            }, intervals[subLevel] * 500);
        } else {
            subLevel = 0;
            let [min, max, avg] = getStats();
            recordScore();
            setPause(true);
            swal(
                "",
                constants.BEST_REACTION_LB + " " +
                min / 1000 + "s \n" +
                constants.WORST_REACTION_LB +
                max / 1000 +
                "s \n " +
                constants.AVERAGE_REACTION_LB +
                "" +
                avg / 1000 +
                "s",
                "success", {
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
                        window.location.replace("/photographic");
                        break;

                    case "next":
                        window.location.replace("/reaction?lvl=" + (level + 1));
                        break;

                    default:
                        window.location.replace("/photographic");
                        break;
                }
            });
        }

        let buttons = document.getElementsByClassName("colored");
        Array.from(buttons).forEach((el) => {
            el.style.backgroundColor = "white";
            el.classList.remove("colored");
        });
        button.style.backgroundColor = "white";
    }

    function getStats() {
        let array = times;
        let min = array[0];
        let max = array[0];
        let total = 0;

        for (let i = 0; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }

            if (array[i] > max) {
                max = array[i];
            }

            total += array[i];
        }
        return [min, max, total / array.length];
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


    function recordScore() {
        let points;
        let wrongCounter = clicks;
        switch (level) {
            case 1:
                points = 50 - wrongCounter * 10;
                break;
            case 2:
                points = 100 - wrongCounter * 10;
                break;
            case 3:
                points = 150 - wrongCounter * 15;
                break;
            case 4:
                points = 200 - wrongCounter * 15;
                break;
            case 5:
                points = 250 - wrongCounter * 20;
                break;

        }

        stompClient.current.send(
            "/app/re_recordscore",
            {}, JSON.stringify(
                {
                    name: cookies.get("currentUser"),
                    level: level,
                    missed: wrongCounter,
                    points: points,
                })
        );
    }


    return (
        <div className="container-fluid">
            <Navbar></Navbar>
            <div className="row">
                <div className="col-md-3">
                    <Menu
                        level={levelRender}
                        playerName={cookies.get("username")}
                        instructions={constants.REACTION_INSTRUCTIONS}
                        pause={() => { setPause(value => !value) }}
                    ></Menu>
                </div>
                <div className="col-md-6 text-center all_words">
                    <div className="row">
                        {matrix.map((item, index) => (
                            <div
                                style={{
                                    width: 100 / Math.sqrt(matrix.length) + "%",
                                    height: "100px",
                                    border: "2px solid black",
                                }}
                                className={"col-xs-1"}
                                key={index}
                            >
                                <button
                                    style={{ height: "100%" }}
                                    className="btn btn-light mb-0 p-3 rounded border-0 fpbutton"
                                    id={index}
                                    onClick={() => { clicks++ }}
                                ></button>
                            </div>
                        ))}
                    </div>
                </div>
                {matrix.length > 0 ? <div className="col-md-3" id="timer"><br /><br /><br />
                    <Timer timer={timeAllotted[level - 1]} onTimerFinish={timerOver} pause={pause} /></div> : null}
            </div>

        </div>
    );
}
