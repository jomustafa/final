import React, { useEffect, useState, useRef } from "react";
import "bootstrap/dist/css/bootstrap.css";
import "../../assets/stylesheets/main_styles.css";
import * as Stomp from "stompjs";
import SockJS from "sockjs-client";
import spotDifferencesStyle from "./style.css";
import Player from '../player/player';
import Level from '../level/level';
import Menu from '../menu/menu';
import Sidebar from '../sidebar/sidebar';
import swal from 'sweetalert';
import Cookies from "universal-cookie";
import Navbar from "../navigation/Navbar";
import Timer from "../sidebar/timer";


let link = window.location.toString();
let level = Number(link.split("=")[1]);

let alphabet;
let englishAlpha = ["A", "B", "G", "D", "E", "Z", "H", "U", "I", "K", " ", "Q", "L", "M", "N", "J", "O", "P", "R", "S", "W", " ", "T", "Y", "F", "X", "C", "V", "Q"];
let greekAlpha = ["A", "B", "Γ", "Δ", "Ε", "Ζ", "Η", "Θ", "Ι", "Κ", " ", "Λ", "Μ", "Ν", "Ξ", "Ο", "Π", "Ρ", "Σ", " ", "Τ", "Υ", "Φ", "Χ", "Ψ", "Ω"];

let elementPartsOriginal = [];

let bodyCount = 0;
let ctx = null;



let category_select;
if (window.location.href.split('/')[4]) {
  category_select = window.location.href.split('/')[4].split('?')[0];
} else {
  category_select = ''
}
console.log(category_select);


let constants;

export default function ClientComponent() {

  const socket = useRef(new SockJS('/brainbright-websocket'));
  const stompClient = useRef(Stomp.over(socket.current));

  const cookies = new Cookies();


  if (cookies.get("language") === "en") {
    constants = require("../../assets/metatext/constants");
    alphabet = englishAlpha;
  } else {
    constants = require("../../assets/metatext/constantsgr");
    alphabet = greekAlpha;
  }
  /*  if(cookies.get("language")==="en"){
      constants = require("../../assets/metatext/constants");
    }
    else {
      constants = require("../../assets/metatext/constantsgr");
    }*/

  const [spotDifferencesArray, setspotDifferencesArray] = useState([]);
  const [endResult, setEndResult] = useState('');
  const [spotDifferencesText, setspotDifferencesText] = useState([]);



  const [levelRender, setLevelRender] = useState(Number(level));
  const [timer, setTimer] = useState(0);

  const [elementPartsTB, setElementPartsTB] = useState([]);
  const [disabled, setDisabled] = useState(true);
  const [incorrectCharacters, setincorrectCharacters] = useState('');
  const [category, setCategory] = useState('');
  const [remainingAttempts, setremainingAttempts] = useState(6);
  const [pause, setPause] = useState(false);
  const canvas = React.useRef();


  //score = base 100
  //bonus_score = (base_time(120 || timer state) - elapsed_time) * 5
  //add bonus to score

  useEffect(() => {


    const canvasEle = canvas.current;
    canvasEle.width = canvasEle.clientWidth;
    canvasEle.height = canvasEle.clientHeight;

    ctx = canvasEle.getContext("2d");

    stompClient.current.connect({}, function () {
      this.subscribe('/user/topic/gethangmanConstructor', function (data) {

        setincorrectCharacters('');
        setremainingAttempts(6);
        let randomElement = "";
        let array = [];
        bodyCount = 0;
        setDisabled(false);
        let arr3 = [];
        let elementParts = [];
        let array2 = [];
        ctx.clearRect(0, 0, canvasEle.width, canvasEle.height);

        JSON.parse(data.body).map((item) => {
          array.push(item);
        });
        /*
        export const ANIMALS_CATEGORY = "Animals";
export const OCCUPATIONS_CATEGORY = "Occuoations";  
export const PLANTS_CATEGORY = "Plants";
export const SAYINGS_CATEGORY = "Sayings";
*/

        /*
        export const ANIMALS_CATEGORY = "ζώο";
        export const OCCUPATIONS_CATEGORY = "Επαγγέλματα";
        export const PLANTS_CATEGORY = "Φυτά";
        export const SAYINGS_CATEGORY = "Ρήματα";
        */
        if (cookies.get("language") === "en") {
          if (array[0] === "ΖΩΑ") array[0] = "ANIMALS";
          else if (array[0] === "ΕΠΑΓΓΕΛΜΑΤΑ") array[0] = "OCCUPATIONS";
          else if (array[0] === "ΦΥΤΑ") array[0] = "PLANTS";
          else if (array[0] === "ΠΑΡΟΙΜΙΕΣ") array[0] = "SAYINGS";
        }
        setCategory(array[0]);
        array.shift();
        setTimer(array[0]);
        array.shift();

        randomElement = array[Math.floor(Math.random() * array.length)];

        elementParts = randomElement.split("");
        elementPartsOriginal = randomElement.split("");

        let arr2 = array2.concat(elementParts[0]);


        for (var i = 1; i < elementParts.length; i++) {
          arr2 = arr2.concat(" ");
        }

        for (var i = 1; i < elementParts.length; i++) {
          elementParts[i] = " ";
        }

        arr3 = arr2;

        setElementPartsTB(arr3);


        const r2Info = { sh: "foundation" };
        drawRect(r2Info);

        const r3Info = { sh: "main_pillar" };
        drawRect(r3Info);

        const r4Info = { sh: "top_pillar" };
        drawRect(r4Info);

        const r5Info = { sh: "rope" };
        drawRect(r5Info);

      });
      getHangman(this, level, true);
    });


  }, []);

  function clearContent() {
    setspotDifferencesText([]);
    setspotDifferencesArray([]);
  }

  function getHangman(client, level, isNew) {
    let categoryInt = 0;
    if (category_select == "occupation") {
      categoryInt = 0;
    }
    else if (category_select == "animals") {
      categoryInt = 1;
    }
    else if (category_select == "plants") {
      categoryInt = 2;
    }
    else if (category_select == "sayings") {
      categoryInt = 3;
    }
    client.send("/app/hangmanConstructor", {}, JSON.stringify({ "level": level, "isNew": isNew, "category": categoryInt, language: cookies.get("language") }));
  }

  function recordScore() {

    stompClient.current.send(
      "/app/hangman_recordscore",
      {}, JSON.stringify(
        {
          name: cookies.get("currentUser"),
          level: level,
          missed: bodyCount,
          //points: 
        })
    );
  }

  function getButtonContent(e) {
    let character = e.target.getAttribute('id');
    e.target.setAttribute('disabled', true);

    let index = 0;
    let indexes = [];


    for (var j = 1; j < elementPartsOriginal.length; j++) {
      if (elementPartsOriginal[j] == character) {
        indexes.push(j);
      }
    }

    if (indexes.length != 0) {
      let prevArray = elementPartsTB;

      let array;

      array = prevArray.map((item, index) => {
        for (var i = 0; i < indexes.length; i++) {
          if (index === indexes[i]) {
            item = character;
          }
        }
        return item;
      });

      setElementPartsTB(array);
      setEndResult('You are correct');
      document.getElementById("resultPartText").style.backgroundColor = "green";
      if (arraysEqual(array, elementPartsOriginal)) {
        setDisabled(true);
        if (level === 5) {
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
                window.location.replace("/hangman?lvl=5");
                break;

              default:
                window.location.replace("/visual");
                break;
            }
          });
        }
        else {
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
                window.location.replace("/hangman?lvl=" + (level + 1));
                break;

              default:
                window.location.replace("/visual");
                break;
            }
          });
        }

      }
    }
    else {
      let arrayIncorrect = incorrectCharacters.concat(character);
      setincorrectCharacters(arrayIncorrect);
      setremainingAttempts(remainingAttempts - 1);
      setEndResult('You are wrong');
      document.getElementById("resultPartText").style.backgroundColor = "#DC143C";

      if (bodyCount == 0) {
        const r1Info = { sh: "head" };
        drawRect(r1Info);
      }
      else if (bodyCount == 1) {
        const r6Info = { sh: "main_body" };
        drawRect(r6Info);
      }
      else if (bodyCount == 2) {
        const r9Info = { sh: "hand_left" };
        drawRect(r9Info);
      }
      else if (bodyCount == 3) {
        const r10Info = { sh: "hand_right" };
        drawRect(r10Info);
      }
      else if (bodyCount == 4) {
        const r7Info = { sh: "leg_left" };
        drawRect(r7Info);
      }
      else if (bodyCount == 5) { //game over
        const r8Info = { sh: "leg_right" };
        drawRect(r8Info);
        recordScore();
        swal(constants.UNFORTUNATELY_YOU_LOST, "", "error", {
          buttons: {
            retry: {
              text: constants.TRY_AGAIN,
              value: "retry",
            },
            goback: {
              text: constants.BACK_BTN,
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

            case "retry":
              window.location.reload();
              break;

            default:
              window.location.replace("/verbal");
              break;
          }
        });
      }

      bodyCount++;
    }
  }

  function arraysEqual(a, b) {
    if (a instanceof Array && b instanceof Array) {
      if (a.length != b.length)
        return false;
      for (var i = 0; i < a.length; i++)
        if (!arraysEqual(a[i], b[i]))
          return false;
      return true;
    } else {
      return a == b;
    }
  }

  //draw shapes
  const drawRect = (info = {}) => {
    const { x, y, w, h, sh } = info;
    var borderColor1 = "black";
    var borderColor2 = "blue";

    if (sh == "head") {
      var borderWidth = 12;
      ctx.beginPath();
      ctx.strokeStyle = borderColor2;
      ctx.lineWidth = borderWidth;
      ctx.fillStyle = "blue";
      ctx.arc(700, 165, 20, 0, 2 * Math.PI);
      ctx.fill();
    }
    else if (sh == "foundation") {
      ctx.lineWidth = 12;
      ctx.beginPath();
      ctx.strokeStyle = borderColor1;
      ctx.moveTo(800, 460);
      ctx.lineTo(950, 460);
      ctx.stroke();
    }
    else if (sh == "main_pillar") {
      ctx.lineWidth = 12;
      ctx.beginPath();
      ctx.strokeStyle = borderColor1;
      ctx.moveTo(875, 460);
      ctx.lineTo(875, 100);
      ctx.stroke();
    }
    else if (sh == "top_pillar") {
      ctx.lineWidth = 12;
      ctx.beginPath();
      ctx.strokeStyle = borderColor1;
      ctx.moveTo(875, 100);
      ctx.lineTo(700, 100);
      ctx.stroke();
    }
    else if (sh == "rope") {
      ctx.lineWidth = 12;
      ctx.beginPath();
      ctx.strokeStyle = borderColor1;
      ctx.moveTo(700, 100);
      ctx.lineTo(700, 150);
      ctx.stroke();
    }
    else if (sh == "main_body") {
      ctx.lineWidth = 8;
      ctx.beginPath();
      ctx.strokeStyle = borderColor2;
      ctx.moveTo(700, 165);
      ctx.lineTo(700, 300);
      ctx.stroke();
    }
    else if (sh == "leg_left") {
      ctx.lineWidth = 8;
      ctx.beginPath();
      ctx.strokeStyle = borderColor2;
      ctx.moveTo(700, 300);
      ctx.lineTo(735, 340);
      ctx.stroke();
    }
    else if (sh == "leg_right") {
      ctx.lineWidth = 8;
      ctx.beginPath();
      ctx.strokeStyle = borderColor2;
      ctx.moveTo(700, 300);
      ctx.lineTo(665, 340);
      ctx.stroke();
    }
    else if (sh == "hand_left") {
      ctx.lineWidth = 8;
      ctx.beginPath();
      ctx.strokeStyle = borderColor2;
      ctx.moveTo(700, 200);
      ctx.lineTo(735, 240);
      ctx.stroke();
    }
    else if (sh == "hand_right") {
      ctx.lineWidth = 8;
      ctx.beginPath();
      ctx.strokeStyle = borderColor2;
      ctx.moveTo(700, 200);
      ctx.lineTo(665, 240);
      ctx.stroke();
    }
  }

  function timerOver() {
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
          window.location.replace("/verbal");
          break;

        case "retry":
          window.location.reload();
          break;

        default:
          window.location.replace("/verbal");
          break;
      }
    });
  }

  return (

    <div className="container-fluid" >
      <Navbar />
      <div className="row">
        <div className="col-md-3">
          <Menu level={levelRender} playerName={cookies.get("username")} instructions={constants.HANGMAN_INSTRUCTIONS} pause={() => { setPause(value => !value) }}></Menu>
        </div>
        <div className="col-md-6 text-center all_words_fd">
          <div className="row">

            <div id="imageContainer">
              <div className="informationBar bigger_text">
                <div id="incorrectWords">
                  <div id="incorrect">{constants.WRONG} {incorrectCharacters}</div>
                </div>
                <div id="remainingAttemptsCount">
                  <div id="remainingAtt">{constants.TRIES_LEFT} {remainingAttempts}</div>
                </div>
              </div>

              <div id="wordToBeFound">
                {

                  elementPartsTB.map((item) =>
                    item === " " ?
                      <button className="btn btn_unclickable bigger_text">_</button>
                      :
                      <button className="btn btn_hangman_found_char bigger_text">{item}</button>
                  )
                }
              </div>
              <div id="keyboardInterface">
                {
                  alphabet.map((item) =>
                    item != " " ?
                      <button className="btn_hangman bigger_text" onClick={getButtonContent} id={item} disabled={disabled}>{item}</button>
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
              <canvas ref={canvas} id="canvas" style={{ width: "100%", height: "800px", zIndex: "10", position: "absolute", left: 0, top: 0, pointerEvents: "none" }} />
            </div>


          </div>

          <br />
          <div className="result p-2">{
            constants.CLICK_KEY
          }</div>
          <div className="mt-3" id="resultPartText">{endResult}</div>
          {/* <button className="btn pl-5 pr-5 pt-2 pb-2 mt-3" onClick={clearContent}>Clear</button> */}
        </div>

        <div classname="col-md-3" id="categoryWord text-center">
          <div id="categoryWordinner">{constants.SELECTED_HANGMAN_CATEGORY}<br /> {category}</div>
        </div>

        {elementPartsTB.length > 0 ? <div style={{ top: -350 }} className="col-md-3" id="timer"><br /><br /><br /><Timer timer={100} onTimerFinish={timerOver} /></div> : null}
      </div>

    </div>
  )

}
