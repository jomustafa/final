import React, { useRef, useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.css";
import "../../assets/stylesheets/main_styles.css";
import * as Stomp from "stompjs";
import SockJS from "sockjs-client";
import coloredBoxesStyle from "./style.css";
import Player from '../player/player';
import Level from '../level/level';
import Menu from '../menu/menu';
import Sidebar from '../sidebar/sidebar';
import Navbar from "../navigation/Navbar";
import Cookies from "universal-cookie";
import swal from 'sweetalert';



let constants;

let buttonarray = [];
let howManyShouldBeFound = 2;
let indexColor = 0;
let colorsShuffled = [];
let colors_shuffled = [];
let shuffled_colors_final = [];
let colors_final = [];
let flag = true;
let colorFlagCorrect = false;
let howManyFound = 0;
let items = [];
let link = window.location.toString();
let level = Number(link.split("=")[1]);

const cookies = new Cookies();
let arrayRandomColors = ["aqua", "blue", "red", "black", "green", "gold", "purple", "darkorange", "saddlebrown", "pink", "darkgrey", "lime"];



export default function ClientComponent() {

  const cookies = new Cookies();

  const socket = useRef(new SockJS('/brainbright-websocket'));
  const stompClient = useRef(Stomp.over(socket.current));


  if (cookies.get("language") === "en") {
    constants = require("../../assets/metatext/constants");
  } else {
    constants = require("../../assets/metatext/constantsgr");
  }

  const [correctColor, setCorrectColor] = useState([]);

  const [coloredBoxesList, setColoredBoxesList] = useState([]);
  const [array_shuffled_collection, setarray_shuffled_collection] = useState([]);

  const [coloredBoxesArray, setColoredBoxesArray] = useState([]);
  const [endResult, setEndResult] = useState('');
  const [coloredBoxesText, setColoredBoxesText] = useState([]);

  const [wordsFound, setwordsFound] = useState([]);
  const [pause, setPause] = useState(false);

  const [levelRender, setLevelRender] = useState(Number(level));

  //This array has elements equal to the number of words we get from the backend. Used for the sidebar.
  function shuffle_colors(array) {
    const colorsun = array;
    let colors = colorsun.filter(function (entry) { return entry.trim() != ''; });
    colors_shuffled = shuffle(colors);
  }


  //integer representing the number of words the user has guessed
  const [correctWords, setCorrectWord] = useState(0);
  useEffect(() => {

    stompClient.current.connect({}, function () {
      this.subscribe('/user/topic/getcoloredboxes', function (data) {
        howManyShouldBeFound += 2;
        howManyFound = 0;
        setarray_shuffled_collection([]);
        setColoredBoxesList([]);
        let index_array;
        let numberOf_spaces = 0;
        let arrayColors = JSON.parse(data.body);
        let dummyArray = JSON.parse(data.body);
        dummyArray.shift();
        let arrayColors_shuffled = dummyArray;

        if (cookies.get("language") === "gr") {
          for (var i = 0; i < arrayColors.length; i++) {
            if (arrayColors[i].includes("black")) {
              arrayColors[i] = arrayColors[i].replace("black", "MAYPO");
            }
            if (arrayColors[i].includes("red")) {
              arrayColors[i] = arrayColors[i].replace("red", "ΚΟΚΚΙΝΟ");
            }
            if (arrayColors[i].includes("green")) {
              arrayColors[i] = arrayColors[i].replace("green", "ΠΡΑΣΙΝΟ");
            }
            if (arrayColors[i].includes("blue")) {
              arrayColors[i] = arrayColors[i].replace("blue", "ΜΠΛΕ");
            }
            if (arrayColors[i].includes("purple")) {
              arrayColors[i] = arrayColors[i].replace("purple", "ΜΟΒ");
            }
            if (arrayColors[i].includes("darkorange")) {
              arrayColors[i] = arrayColors[i].replace("darkorange", "ΠΟΡΤΟΚΑΛΙ");
            }
            if (arrayColors[i].includes("saddlebrown")) {
              arrayColors[i] = arrayColors[i].replace("saddlebrown", "ΚΑΦΕ");
            }
            if (arrayColors[i].includes("yellow")) {
              arrayColors[i] = arrayColors[i].replace("yellow", "ΚΙΤΡΙΝΟ");
            }
            if (arrayColors[i].includes("aqua")) {
              arrayColors[i] = arrayColors[i].replace("aqua", "ΓΑΛΑΖΙΟ");
            }
            if (arrayColors[i].includes("pink")) {
              arrayColors[i] = arrayColors[i].replace("pink", "ΡΟΖ");
            }
            if (arrayColors[i].includes("darkgrey")) {
              arrayColors[i] = arrayColors[i].replace("darkgrey", "ΓΚΡΙ");
            }
            if (arrayColors[i].includes("lime")) {
              arrayColors[i] = arrayColors[i].replace("lime", "ΛΑΧΑΝΙ");
            }
          }
        }
        let correctColor = arrayColors.shift();
        let specific_index_array;
        shuffle_colors(arrayColors_shuffled);

        setCorrectColor(correctColor);
        if (level == 4 || level == 5) {
          numberOf_spaces = 32;
        }
        else {
          numberOf_spaces = 24;
        }

        let array = new Array(numberOf_spaces - arrayColors.length * 2).fill(' ');
        let array_shuffled = new Array(numberOf_spaces - arrayColors.length * 2).fill(' ');


        let array_store_indexes_1 = [];
        let array_store_indexes_2 = [];

        arrayColors.map((item) => { //
          index_array = Math.floor(Math.random() * numberOf_spaces);
          array_store_indexes_1.push(index_array);
          array_store_indexes_2.push(index_array);

        });


        arrayColors.map((item1) => { //
          array.splice(array_store_indexes_1.pop(), 0, item1);
        });


        colors_shuffled.map((item2) => { //
          array_shuffled.splice(array_store_indexes_2.pop(), 0, item2);
        });


        setarray_shuffled_collection(array_shuffled);
        setColoredBoxesList(array);

        shuffled_colors_final = Array.from(array_shuffled);
        colors_final = Array.from(array);

        array_shuffled = [];
        array = [];
        arrayColors = [];
        colors_shuffled = [];

      });

      stompClient.current.subscribe('/user/topic/getNewReturnColoredBoxesCorrectColor', function (correctColorReturn) {

        if (cookies.get("language") === "gr") {
          if (correctColorReturn.body.includes("black")) {
            correctColorReturn.body = correctColorReturn.body.replace("black", "MAYPO");
          }
          if (correctColorReturn.body.includes("red")) {
            correctColorReturn.body = correctColorReturn.body.replace("red", "ΚΟΚΚΙΝΟ");
          }
          if (correctColorReturn.body.includes("green")) {
            correctColorReturn.body = correctColorReturn.body.replace("green", "ΠΡΑΣΙΝΟ");
          }
          if (correctColorReturn.body.includes("blue")) {
            correctColorReturn.body = correctColorReturn.body.replace("blue", "ΜΠΛΕ");
          }
          if (correctColorReturn.body.includes("purple")) {
            correctColorReturn.body = correctColorReturn.body.replace("purple", "ΜΟΒ");
          }
          if (correctColorReturn.body.includes("darkorange")) {
            correctColorReturn.body = correctColorReturn.body.replace("darkorange", "ΠΟΡΤΟΚΑΛΙ");
          }
          if (correctColorReturn.body.includes("saddlebrown")) {
            correctColorReturn.body = correctColorReturn.body.replace("saddlebrown", "ΚΑΦΕ");
          }
          if (correctColorReturn.body.includes("yellow")) {
            correctColorReturn.body = correctColorReturn.body.replace("yellow", "ΚΙΤΡΙΝΟ");
          }
          if (correctColorReturn.body.includes("aqua")) {
            correctColorReturn.body = correctColorReturn.body.replace("aqua", "ΓΑΛΑΖΙΟ");
          }
          if (correctColorReturn.body.includes("pink")) {
            correctColorReturn.body = correctColorReturn.body.replace("pink", "ΡΟΖ");
          }
          if (correctColorReturn.body.includes("darkgrey")) {
            correctColorReturn.body = correctColorReturn.body.replace("darkgrey", "ΓΚΡΙ");
          }
          if (correctColorReturn.body.includes("lime")) {
            correctColorReturn.body = correctColorReturn.body.replace("lime", "ΛΑΧΑΝΙ");
          }
        }
        setCorrectColor(correctColorReturn.body);
      });

      stompClient.current.subscribe('/user/topic/validactionresponsecoloredboxes', function (coloredboxesResult) {
        if (JSON.parse(coloredboxesResult.body) === 0) {
          setEndResult('You are wrong');
          document.getElementById("resultPartText").style.backgroundColor = "#DC143C";
          colorFlagCorrect = false;

        } else if (JSON.parse(coloredboxesResult.body) === 1 || JSON.parse(coloredboxesResult.body) === 2) {
          howManyFound++;
          document.getElementById("resultPartText").style.backgroundColor = "green";


          let index = shuffled_colors_final.indexOf(buttonarray[0]);

          colors_final.splice(index, 1, ' ');
          shuffled_colors_final.splice(index, 1, ' ');

          setarray_shuffled_collection(shuffled_colors_final);
          setColoredBoxesList(colors_final);

          if (JSON.parse(coloredboxesResult.body) === 2) {
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
                    window.location.replace("/visual"); 
                    break;

                  case "next":
                    window.location.replace("/coloredboxes?lvl=5");
                    break;

                  default:
                    window.location.replace("/visual"); 
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
                    window.location.replace("/visual"); 
                    break;

                  case "next":
                    window.location.replace("/coloredboxes?lvl=" + (level + 1));
                    break;

                  default:
                    window.location.replace("/visual"); 
                    break;
                }
              });
            }

          }
          else {
            setEndResult(constants.CORRECT);
            getNewCorrectColor(stompClient);
            console.log("whoaah: " + shuffled_colors_final);
            var flagNewGame = true;
            for (var i = 0; i < shuffled_colors_final.length; i++) {
              if (shuffled_colors_final[i] !== " ") {
                flagNewGame = false;
              }
            }

            if (flagNewGame) {
              getColoredBoxes(stompClient, level, true);
              console.log("NEW GAME");
            }
          }

          for (let i = 0; i < wordsFound.length; i++) {
            if (wordsFound[i] == "") {
              wordsFound.splice(i, i, buttonarray[0]);
            }
          }

          setwordsFound(wordsFound => [...wordsFound, buttonarray[0]]);
          setCorrectWord(correctWords + 1);
          shuffled_colors_final = [];
          colors_final = [];
        }
        buttonarray = [];
      });
      getColoredBoxes(level, true);
    });
  }, []);



  function getColoredBoxes(level, isNew) {
    stompClient.current.send("/app/getcoloredboxes", {}, JSON.stringify({ "level": level, "isNew": isNew, "userID": cookies.get("currentUser"), language: cookies.get("language") }));
  }

  function getNewCorrectColor() {
    stompClient.current.send("/app/getNewReturnColoredBoxesCorrectColor", {}, JSON.stringify({ "level": "1", "isNew": false }));
  }

  function clearContent() {
    setColoredBoxesText([]);
    setColoredBoxesArray([]);
  }

  function shuffle(array) {
    var currentIndex = array.length, temporaryValue, randomIndex;

    // While there remain elements to shuffle...
    while (0 !== currentIndex) {

      // Pick a remaining element...
      randomIndex = Math.floor(Math.random() * currentIndex);
      currentIndex -= 1;

      // And swap it with the current element.
      temporaryValue = array[currentIndex];
      array[currentIndex] = array[randomIndex];
      array[randomIndex] = temporaryValue;
    }

    return array;
  }

  function shuffle_mirror(array, array2) {
    var currentIndex = array.length, temporaryValue, randomIndex;

    // While there remain elements to shuffle...
    while (0 !== currentIndex) {

      // Pick a remaining element...
      randomIndex = Math.floor(Math.random() * currentIndex);
      currentIndex -= 1;

      // And swap it with the current element.
      temporaryValue = array[currentIndex];
      array[currentIndex] = array[randomIndex];
      array[randomIndex] = temporaryValue;
      //swap second array
      temporaryValue = array2[currentIndex];
      array2[currentIndex] = array2[randomIndex];
      array2[randomIndex] = temporaryValue;

    }
  }

  function getButtonContent(e) {
    let a = e.target.getAttribute('correctColorPick');
    buttonarray.push(a);
    stompClient.current.send("/app/validactioncoloredboxes", {}, JSON.stringify(buttonarray));
    setColoredBoxesText(buttonarray)
  }

  function updateSws(a) {
    let buttonarray = coloredBoxesArray
    buttonarray.push(a);
    setColoredBoxesArray(buttonarray);
  }

  if (colorFlagCorrect) {
    //get index of color
    //remove with index
    let index = shuffled_colors_final.indexOf(buttonarray[0]);

    colors_final.splice(index, 1, ' ');
    shuffled_colors_final.splice(index, 1, ' ');

    setarray_shuffled_collection(shuffled_colors_final);
    setColoredBoxesList(colors_final);
    shuffled_colors_final = [];
    colors_final = [];
    colorFlagCorrect = false;
  }

  shuffle_mirror(coloredBoxesList, array_shuffled_collection);
  shuffled_colors_final = array_shuffled_collection;
  colors_final = coloredBoxesList;

  function split_item(item) {
    let item_to_change = item;
    items = item_to_change.split('/');
  }

  return (

    <div className="container-fluid">
      <Navbar />
      <div className="row">
        <div className="col-md-3">
          <Menu level={levelRender} playerName={cookies.get("username")} instructions={constants.COLOREDBOXES_INSTRUCTIONS} pause={() => { setPause(value => !value) }}></Menu>
        </div>

        <div className="col-md-6 text-center all_words">
          <div className="text-center">{constants.FIND_THE_BOX_WITH_COLOR} <span id="colorRandomSpan" style={{ color: arrayRandomColors[Math.floor(Math.random() * arrayRandomColors.length)] }}>{correctColor}</span><hr /></div>

          <div className="row">
            {

              coloredBoxesList.map((item, index) =>
                <div className="col-md-3 col-sm-6" key={item.word}>
                  {
                    item !== ' ' ?
                      item.includes('/') && array_shuffled_collection[index].includes('/') ? <button className="btn btn-primary mb-5 p-3 rounded border-0 buttonsContainingWords" id={item} onClick={getButtonContent} correctColorPick={array_shuffled_collection[index]} style={{ background: "linear-gradient(180deg," + array_shuffled_collection[index].substring(array_shuffled_collection[index].indexOf('/') + 1) + " 50%," + array_shuffled_collection[index].substring(0, array_shuffled_collection[index].indexOf('/')) + " 50%" }}>{item.substring(item.indexOf('/') + 1)}<br />{item.substring(0, item.indexOf('/'))}</button>
                        : array_shuffled_collection[index].includes('/') && !item.includes('/') ? <button className="btn btn-primary mb-5 p-3 rounded border-0 buttonsContainingWords" id={item} onClick={getButtonContent} correctColorPick={array_shuffled_collection[index]} style={{ background: "linear-gradient(180deg," + array_shuffled_collection[index].substring(array_shuffled_collection[index].indexOf('/') + 1) + " 50%," + array_shuffled_collection[index].substring(0, array_shuffled_collection[index].indexOf('/')) + " 50%" }}>{item}</button>
                          : !array_shuffled_collection[index].includes('/') && item.includes('/') ? <button className="btn btn-primary mb-5 p-3 rounded border-0 buttonsContainingWords" id={item} onClick={getButtonContent} correctColorPick={array_shuffled_collection[index]} style={{ backgroundColor: array_shuffled_collection[index] }}>{item.substring(item.indexOf('/') + 1)}<br />{item.substring(0, item.indexOf('/'))}</button>
                            : <button className="btn btn-primary mb-5 p-3 rounded border-0 buttonsContainingWords" id={item} onClick={getButtonContent} correctColorPick={array_shuffled_collection[index]} style={{ backgroundColor: array_shuffled_collection[index] }}>{item}</button>
                      : <br></br>
                  }
                </div>
              )


            }

          </div>
          <div className="result p-2">{
            coloredBoxesText.length === 0 ? constants.CLICK_BTN_STR :
              coloredBoxesText.map((item) =>
                <span>{item}</span>
              )
          }</div>
          <div className="mt-3" id="resultPartText">{endResult}</div>
          {/* <button className="btn pl-5 pr-5 pt-2 pb-2 mt-3" onClick={clearContent}>Clear</button> */}

        </div>

      </div>
    </div>
  )
}
