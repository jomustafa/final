import React, { useEffect, useState, useRef } from "react";
import "bootstrap/dist/css/bootstrap.css";
import "../../assets/stylesheets/main_styles.css";
import * as Stomp from "stompjs";
import SockJS from "sockjs-client";
import spotDifferencesStyle from "./style.css";
import Player from "../player/player";
import Level from "../level/level";
import Menu from "../menu/menu";
import Sidebar from "../sidebar/sidebar";
import swal from "sweetalert";
import Cookies from "universal-cookie";
import Navbar from "../navigation/Navbar";
import Timer from "../sidebar/timer";

let coordarray = [];
let link = window.location.toString();
let level = Number(link.split("=")[1]);

let array = [];
let numdifferences = 0;

let timeAllotted = [300, 300, 300, 420, 300];
function getspotDifferences(client, level, isNew) {
    client.send(
        "/app/getspotdifferencesinitlevel",
        {},
        JSON.stringify({ level: level, isNew: isNew })
    );
}


let constants;
let found = 0;

export default function ClientComponent() {
    window.alert("The game has started");

    const[howManyFound, setHowManyFound] = useState(0);
    const socket = useRef(new SockJS('/brainbright-websocket'));
    const stompClient = useRef(Stomp.over(socket.current));

    const cookies = new Cookies();


    if (cookies.get("language") === "en") {
        constants = require("../../assets/metatext/constants");
    } else {
        constants = require("../../assets/metatext/constantsgr");
    }
    const [spotDifferencesArray, setspotDifferencesArray] = useState([]);
    const [endResult, setEndResult] = useState("");
    const [spotDifferencesText, setspotDifferencesText] = useState([]);

    const [levelRender, setLevelRender] = useState(Number(level));
    const [imagesName, setimagesName] = useState("");
    const [timer, setTimer] = useState(0);
    const [ndifferences, setNdifferences] = useState(0);
    const [pause, setPause] = useState(false);
    const canvas = React.useRef();
    let ctx = null;

    useEffect(() => {
        const canvasEle = canvas.current;
        canvasEle.width = canvasEle.clientWidth;
        canvasEle.height = canvasEle.clientHeight;

        ctx = canvasEle.getContext("2d");

        stompClient.current.connect({}, function () {
            this.subscribe(
                "/user/topic/spotdifferencesinitlevel",
                function (data) {
                    setHowManyFound(0);
                    found = 0;
                    numdifferences = 0;
                    array = [];
                    setimagesName([]);
                    setNdifferences([]);
                    setTimer([]);

                    ctx.clearRect(0, 0, canvasEle.width, canvasEle.height);
                    JSON.parse(data.body).map((item) => {
                        array.push(item);
                    });
                    var str = array[0];
                    var n = str.lastIndexOf("/");
                    var result = str.substring(n + 1);
                    var exactName = result.split(".")[0];
                    setimagesName(exactName);
                    setNdifferences(array[1]);
                    numdifferences = array[1];
                    setTimer(array[2]);

                    setEndResult("");
                    document.getElementById(
                        "resultPartText"
                    ).style.backgroundColor = "white";
                }
            );

            stompClient.current.subscribe(
                "/user/topic/spotdifferencescheckselectedcords",
                function (spotDifferencesResult) {
                    if (JSON.parse(spotDifferencesResult.body) === false) {
                        setEndResult("You are wrong");
                        document.getElementById(
                            "resultPartText"
                        ).style.backgroundColor = "#DC143C";
                    } else if (
                        JSON.parse(spotDifferencesResult.body) === true
                    ) {
                        const r1Info = {
                            x: coordarray[0],
                            y: coordarray[1],
                            w: 100,
                            h: 50,
                        };
                        drawRect(r1Info);

                        setEndResult("You are correct");
                        document.getElementById(
                            "resultPartText"
                        ).style.backgroundColor = "green";

                        if (found == numdifferences) {
                            //recordScore();
                            // swal(
                            //     "Good job!",
                            //     "You just leveled UP!!!",
                            //     "success",
                            //     {
                            //         button: "Press here to continue!",
                            //     }
                            // ).then(function () {
                            //     setLevelRender((level) => level + 1);
                            //     level++;
                            //     getspotDifferences(stompClient.current, level, true);
                            // });
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
                                            window.location.replace("/spotDifferences?lvl=5");
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
                                            window.location.replace("/spotDifferences?lvl=" + (level + 1));
                                            break;
                
                                        default:
                                            window.location.replace("/visual"); 
                                            break;
                                    }
                                });
                            }

                            howManyFound = 0;
                        }
                    }
                }
            );
            getspotDifferences(this, level, true);
        });
    }, []);

    function clearContent() {
        setspotDifferencesText([]);
        setspotDifferencesArray([]);
    }

    //draw circle
    const drawRect = (info = {}) => {
        const { x, y, w, h } = info;
        //check if circle has already been drawn
        if (!ctx.isPointInPath(x, y)) {
            //outer circle
            var borderWidth = 4;
            var borderColor1 = "yellow";
            ctx.beginPath();
            ctx.strokeStyle = borderColor1;
            ctx.lineWidth = borderWidth;
            ctx.arc(x, y, 25, 0, 2 * Math.PI);
            ctx.stroke();

            //inner circle
            borderWidth = 6;
            var borderColor2 = "red";
            ctx.beginPath();
            ctx.strokeStyle = borderColor2;
            ctx.lineWidth = borderWidth;
            ctx.arc(x, y, 20, 0, 2 * Math.PI);
            ctx.stroke();

            setHowManyFound(found=>found+1);
            found++;
        }
    };

    function FindPosition(oElement) {
        if (typeof oElement.offsetParent != "undefined") {
            for (
                var posX = 0, posY = 0;
                oElement;
                oElement = oElement.offsetParent
            ) {
                posX += oElement.offsetLeft;
                posY += oElement.offsetTop;
            }
            return [posX, posY];
        } else {
            return [oElement.x, oElement.y];
        }
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
    

    function handleClick(event) {
        //handle the mouse click over the image
        var myImg = document.getElementById("imageShow");
        var PosX = 0;
        var PosY = 0;
        var ImgPos;
        ImgPos = FindPosition(myImg);
        if (!e) var e = window.event;
        if (e.pageX || e.pageY) {
            PosX = e.pageX;
            PosY = e.pageY;
        } else if (e.clientX || e.clientY) {
            PosX =
                e.clientX +
                document.body.scrollLeft +
                document.documentElement.scrollLeft;
            PosY =
                e.clientY +
                document.body.scrollTop +
                document.documentElement.scrollTop;
        }
        PosX = PosX - ImgPos[0];
        PosY = PosY - ImgPos[1];

        coordarray = [];
        coordarray.push(PosX);
        coordarray.push(PosY);
        stompClient.current.send(
            "/app/getspotdifferencescheckselectedcords",
            {},
            JSON.stringify(coordarray)
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
    return (
        <div className="container-fluid">
            <Navbar />
            <div className="row">
                <div className="col-md-3 mr-5">
                    <Menu
                        level={levelRender}
                        playerName={cookies.get("username")}
                        pause={()=>{setPause(value=>!value)}}
                    ></Menu>
                     <div className="found">{constants.WORDS_FOUND_LABEL}</div>
                    <div className="border border-success">
                        {constants.WORDS_FOUND_LABEL} {howManyFound}/{ndifferences}{" "}
                    </div>
                    {imagesName.length > 0 ? <div className="col-md-3" id="timer"><Timer timer={timeAllotted[level-1]} onTimerFinish={timerOver} /></div> : null}
                </div>
                <div className="col-md-9 text-center all_words_fd">
                        <div id="imageContainer">
                            {
                                <img
                                    id="imageShow"
                                    src={
                                        "images/spotdifferences/" +
                                        imagesName +
                                        ".jpg"
                                    }
                                    onError={(e) => {
                                        e.target.onerror = null;
                                        e.target.src =
                                            "images/white_background.png";
                                    }}
                                    onClick={handleClick}
                                    style={{
                                        height: "100%",
                                        width: "100%",
                                        zIndex: "-1",
                                    }}
                                    draggable="false"
                                />
                            }
                            <canvas
                                ref={canvas}
                                id="canvas"
                                style={{
                                    width: "100%",
                                    height: "700px",
                                    zIndex: "10",
                                    position: "absolute",
                                    left: 0,
                                    top: 0,
                                    pointerEvents: "none",
                                }}
                            />
                        </div>
                    <br />
                    <div className="result p-2">
                        {spotDifferencesText.length === 0
                            ? constants.CLICK_IMG
                            : spotDifferencesText.map((item) => (
                                <span>{item}</span>
                            ))}
                    </div>
                    <div className="mt-3" id="resultPartText">
                        {endResult}
                    </div>
                    <button
                        className="btn pl-5 pr-5 pt-2 pb-2 mt-3 gamebutton"
                        onClick={clearContent}
                    >
                        {constants.CLEAR_BTN}
                    </button>
                </div>
            </div>
        </div>
    );
}
