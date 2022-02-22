import React, {useRef, isValidElement, useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.css";
import "../../assets/stylesheets/main_styles.css";
import * as Stomp from "stompjs";
import SockJS from "sockjs-client";
import Player from '../player/player';
import Level from '../level/level';
import Menu from '../menu/menu';
import swal from 'sweetalert';
import Cookies from "universal-cookie";
import './findTheObjects.css'
import Timer from "../sidebar/timer";
import Navbar from "../navigation/Navbar";



let correctObjects = [];

let constants;
let link = window.location.toString();
let level = Number(link.split("=")[1]);
let randomImagesArray = [];
let buttonArray = [];
let mistakes = 0;
let buttonClickedAll = [];
let buttonClickedHidden = [];
let correct = 0;
let category_select;
if( window.location.href.split('/')[4]) {
    category_select = window.location.href.split('/')[4].split('?')[0];
} else {
    category_select = 'kitchen'
}

let goal = 0;
const kitchen1 = {
    one: ['32%', '15%'],
    two: ['10% !important', '5% !important'],
    three: ['20px', '20px'],
    four: ['12', '20px', '20px']
}

const kitchen2 = {
    one: ['40%', '1%'],
    two: ['20px', '-22%'],
    three: ['38%', '10%'],
    four: ['1%', '10%'],
    five: ['-43%', '5%']
}

const kitchen3 = {
    one: ['20px', '1%'],
    two: ['41%', '-24'],
    three: ['24', '1%'],
    four: ['24%', '20px'],
    five: ['39%', '20px'],
    six: ['10%', '-20%']

}

const kitchen4 = {
    one: ['20px', '20px'],
    two: ['7%', '20px'],
    three: ['4%', '20px'],
    four: ['5%', '20px'],
    five: ['20%', '-2%'],
    six: ['23%', '20px'],
    seven: ['24%', '20px']

}

const kitchen5 = {
    one: ['20px', '20px'],
    two: ['3%', '20px'],
    three: ['20%', '20px'],
    four: ['31%', '5%'],
    five: ['-1%', '5%'],
    six: ['-5%', '20px'],
    seven: ['1%', '20px'],


}

const livingroom1 = {
    one: ['20px', '20px'],
    two: ['34%', '-21%'],
    three: ['26%', '20px'],
    four: ['12%', '20px'],
    five: ['14%', '20px']
}

const livingroom2 = {
    one: ['25%', '20px'],
    two: ['23%', '20px'],
    three: ['21%', '20px'],
    four: ['29%', '20px'],
    five: ['20px', '20px']
}

const livingroom3 = {
    one: ['34%', '20px'],
    two: ['20px', '20px'],
    three: ['10%', '20px'],
    four: ['29%', '20px'],
    five: ['-5%', '20px'],
    six: ['-3%', '20px']

}

const livingroom4 = {
    one: ['25%', '20px'],
    two: ['5%', '20px'],
    three: ['8%', '20px'],
    four: ['14%', '20px'],
    five: ['20px', '20px'],
    six: ['3%', '20px'],
    seven: ['-5%', '20px']
}

const livingroom5 = {
    one: ['20%', '20px'],
    two: ['30%', '20px'],
    three: ['22%', '20px'],
    four: ['23%', '20px'],
    five: ['-2%', '-5%'],
    six: ['-3%', '20px'],
    seven: ['-7%', '20px']
}

export default function ClientComponent() {
    const cookies = new Cookies();
    const socket = useRef(new SockJS('/brainbright-websocket'));
    const stompClient = useRef(Stomp.over(socket.current));
    
    if(cookies.get("language") === "en"){
        constants = require("../../assets/metatext/constants");
    } else {
        constants = require("../../assets/metatext/constantsgr");
    }

    const [images, setImages] = useState([]);
    const [levelRender, setLevelRender] = useState(Number(level));
    const [correctRender, setCorrectRender] = useState([]);
    const [mistakesRender, setMistakesRender] = useState(0);
    const [pause, setPause] = useState(false);
    useEffect(() => {
        stompClient.current.connect({}, function () {
            this.subscribe('/user/topic/gethiddenobjects', function (data) {
                let array = [];
                JSON.parse(data.body).map((item) => {
                    array.push(item);
                });
                setImages(array);

            });
            getImages(level, category_select);
        });
    }, []);

    function getImages(level, category) {
        if (category === 'livingroom') {
            category = 'Living Room'
        }
        stompClient.current.send("/app/gethiddenobjects", {}, JSON.stringify({ "level": level, "category": category.charAt(0).toUpperCase() + category.slice(1) }));
    }

    function renderBackground(category, level) {
        console.log(category, level);
        renderButtonsObject(returnObject(category, level));
        return <div className="justify-content-center" style={{ backgroundImage: "url(" + require("../../photographs/memory-quest/" + category + level + ".jpg") + ")", zIndex: "1", height: "500px", width: "100%" }}>
            {buttonArray.map((item, index) => {
                if (index < Object.keys(returnObject(category, level)).length) {
                    return <button value={item.props.value} className="hidden-object mr-2 border-0 bg-none" style={{ backgroundImage: item.props.style.backgroundImage, width: "200px", height: "100px", marginTop: item.props.style.marginTop, marginLeft: item.props.style.marginLeft, verticalAlign: 'top' }} onClick={(e) => hiddenButtons(e)}></button>
                }
            })}
        </div>
    }

    function renderButtonsObject(object) {
        setGoal(object);
        let array = [];
        images.map(item => {
            let name = item.split('/')[4];
            array.push(name);
        })
        for (const [key, value] of Object.entries(object)) {
            let pathArray = [];
            let path = array[Math.floor(Math.random() * array.length)];
            pathArray.push(path);
            if (pathArray.includes(path)) {
                path = array[Math.floor(Math.random() * array.length)];
                pathArray.push(path);
            }
            if (path !== undefined) {
                let style_object = {
                    backgroundImage: "url(" + require("../../photographs/memory-quest/unnamed-items/" + path) + ")",
                    zIndex: '2',
                    height: '80px',
                    width: '120px',
                    backgroundPosition: 'center',
                    marginTop: value[0],
                    marginLeft: value[1]
                }
                buttonArray.push(<button className="m-2" style={style_object} value={path}></button>)
            }

        }
    }

    function returnObject(category, level) {
        if (category === 'kitchen' && level === 1) return kitchen1;
        else if ((category === 'kitchen' && level === 2)) return kitchen2;
        else if ((category === 'kitchen' && level === 3)) return kitchen3;
        else if ((category === 'kitchen' && level === 4)) return kitchen4;
        else if ((category === 'kitchen' && level === 5)) return kitchen5;
        else if ((category === 'livingroom' && level === 1)) return livingroom1;
        else if ((category === 'livingroom' && level === 2)) return livingroom2;
        else if ((category === 'livingroom' && level === 3)) return livingroom3;
        else if ((category === 'livingroom' && level === 4)) return livingroom4;
        else if ((category === 'livingroom' && level === 5)) return livingroom5;
    }

    function setGoal(object) {
        goal = Object.keys(object).length;
    }

    function hiddenButtons(e) {
        buttonClickedHidden.push(e.target.value);
        console.log(e.target.value, buttonClickedAll[buttonClickedAll.length - 1], 'POSHT')
        if (buttonClickedAll.length !== 0) {
            if (buttonClickedAll[buttonClickedAll.length - 1] === e.target.value) {
                correct++;
                document.getElementById(buttonClickedAll[buttonClickedAll.length - 1]).style.visibility = "hidden";
                if (correct === goal) {
                    document.getElementById(buttonClickedAll[buttonClickedAll.length - 1]).style.visibility = "inherit";
                    changeLevel();

                }
            }
        }
    }

    function recordScore() {
        stompClient.current.send(
            "/app/findobject_recordscore",
            {},JSON.stringify(
            {
                name: cookies.get("currentUser"),
                level: level,
                missed: mistakes,
                points: correct,
            })
        );
    }


    function allButtons(e) {
        buttonClickedAll.push(e.target.value);
        console.log(e.target.value, buttonClickedHidden[buttonClickedHidden.length - 1], 'NALT')

        if (buttonClickedHidden.length !== 0) {
            if (buttonClickedHidden[buttonClickedHidden.length - 1] === e.target.value) {
                correct++;
                document.getElementById(e.target.value).style.visibility = "hidden";
                if (correct === goal) {
                    document.getElementById(e.target.value).style.visibility = "inherit";
                    changeLevel();
                }
            }else{
                mistakes++;
            }
        }
    }

    function changeLevel() {
        recordScore();

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
                        window.location.replace("/visual"); 
                        break;
    
                    case "next":
                        window.location.replace("/findtheobjects?lvl=5");
                        break;
    
                    default:
                        window.location.replace("/visual"); 
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
                        window.location.replace("/visual"); 
                        break;
    
                    case "next":
                        window.location.replace("/findtheobjects?lvl="+(level+1));
                        break;
    
                    default:
                        window.location.replace("/visual"); 
                        break;
                }
            });
        }
        
    }

    function timerOver() {
        Array.from(document.getElementsByClassName("hidden-object")).forEach((el) => {
            el.style.backgroundImage = "none";
        })

        document.getElementById("timer").style.visibility = "hidden";
    }

    return (
        <div className="container-fluid">
            <Navbar/>
            <div className="row">
                <div className="col-md-3">
                    <Menu level={levelRender}
                        playerName={cookies.get("username")}
                        instructions={constants.MEMORYQUEST_INSTRUCTIONS}
                        pause={() => { setPause(value => !value) }}></Menu>
                </div>
                <div className="col-md-6 text-center all_words">
                    <div className="row justify-content-center">
                        {images.map((item) => {
                            let path = item.split('/')[4];
                            return <button id={item.split('/')[4]} value={item.split('/')[4]} className="mr-2 mt-2 bg-none border-0" style={{ backgroundImage: "url(" + require("../../photographs/memory-quest/unnamed-items/" + path) + ")", zIndex: "2", height: "100px", width: "100px", backgroundPosition: "center" }} onClick={(e) => allButtons(e)}></button>

                        })}
                    </div>
                    <div className="row mt-5 justify-content-center">
                        {renderBackground(category_select, levelRender)}
                    </div>

                </div>
                <div className="col-md-3">
                {images.length > 0 ? <div className="col-md-3" id="timer"><br /><br /><br /><Timer timer={5 + 5 * level} onTimerFinish={timerOver} /></div> : null}

                </div>
            </div>
        </div>
    );
}
