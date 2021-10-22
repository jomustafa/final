import React, { useRef, useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.css";
import "../../assets/stylesheets/main_styles.css";
import * as Stomp from "stompjs";
import SockJS from "sockjs-client";
import correctSpellingStyle from "./style.css";
import Player from '../player/player';
import Level from '../level/level';
import Menu from '../menu/menu';
import Sidebar from '../sidebar/sidebar';
import swal from 'sweetalert';
import Navbar from "../navigation/Navbar";
import Cookies from "universal-cookie";

let buttonarray = [];
let buttonArrayUsed = [];
let buttonArrayAllWords = [];
let buttonArrayAllWordsOld = [];
let link = window.location.toString();
let level = Number(link.split("=")[1]);
let constants;


export default function ClientComponent() {
  const cookies = new Cookies();
  const socket = useRef(new SockJS('/brainbright-websocket'));
  const stompClient = useRef(Stomp.over(socket.current));

  if (cookies.get("language") === "en") {
    constants = require("../../assets/metatext/constants");
  } else {
    constants = require("../../assets/metatext/constantsgr");
  }

  const [correctSpellingList, setCorrectSpellingList] = useState([]);

  const [correctSpellingArray, setCorrectSpellingArray] = useState([]);
  const [endResult, setEndResult] = useState('');
  const [correctSpellingText, setCorrectSpellingText] = useState([]);
  const [pause, setPause] = useState(false);
  const [wordsFound, setwordsFound] = useState([]);

  const [levelRender, setLevelRender] = useState(Number(level));

  //This array has elements equal to the number of words we get from the backend. Used for the sidebar.


  //integer representing the number of words the user has guessed
  const [correctWords, setCorrectWord] = useState(0);
  useEffect(() => {

    stompClient.current.connect({}, function () {

      this.subscribe('/user/topic/correctspellinglist', function (data) {
        console.log(data);
        let array = new Array(12).fill(' ');
        JSON.parse(data.body).map((item, index) => {
          if (index == 0) {
            array.splice(1, 1, item);
          }
          if (index == 1) {
            array.splice(2, 1, item);
          }
          if (index == 2) {
            array.splice(5, 1, item);
          }
          if (index == 3) {
            array.splice(6, 1, item);
          }
          if (index == 4) {
            array.splice(9, 1, item);
          }
          if (index == 5) {
            array.splice(10, 1, item);
          }

        });
        setCorrectSpellingList(array);
      });


      function getCorrectSpellingList(client, level, isNew) {
        client.send("/app/getcorrectspellinglist", {}, JSON.stringify({ "level": level, "isNew": isNew, "id": cookies.get("currentUser"), language: cookies.get("language") }));
      }

      stompClient.current.subscribe('/user/topic/validactionresponsecorrectspelling', function (correctspellingResult) {
        if (JSON.parse(correctspellingResult.body) === 0) {
          setEndResult(constants.WRONG_INPUT);
          document.getElementById(buttonarray[0]).disabled = true;
          document.getElementById(buttonarray[0]).style.opacity = "0.2";
          document.getElementById("resultPartText").style.backgroundColor = "#DC143C";

          for (let i = 0; i < 6; i++) {
            if (!document.getElementsByClassName("buttonsContainingWords")[i].hasAttribute("disabled")) {
              buttonArrayAllWords.push(document.getElementsByClassName("buttonsContainingWords")[i].id);
              buttonArrayAllWordsOld.push(document.getElementsByClassName("buttonsContainingWords")[i].id);
            }
          }

          shuffle(buttonArrayAllWords);

          for (let i = 0; i < buttonArrayAllWords.length; i++) {
            document.getElementById(buttonArrayAllWords[i]).innerHTML = buttonArrayAllWordsOld[i];
            document.getElementById(buttonArrayAllWords[i]).id = buttonArrayAllWordsOld[i];
          }

          buttonArrayAllWords = [];
          buttonArrayAllWordsOld = [];

        } else if (JSON.parse(correctspellingResult.body) === 1 || JSON.parse(correctspellingResult.body) === 2) {
          document.getElementById("resultPartText").style.backgroundColor = "green";

          for (let i = 0; i < 6; i++) {
            buttonArrayUsed.push(document.getElementsByClassName("buttonsContainingWords")[i].id);
          }

          buttonArrayUsed.map(key => {
            document.getElementById(key).removeAttribute("disabled");
            document.getElementById(key).style.opacity = "1";
          })

          buttonArrayUsed = [];

          if (JSON.parse(correctspellingResult.body) === 2) {
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
                    window.location.replace("/correctspelling?lvl=" + (level + 1));
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
                    window.location.replace("/correctspelling?lvl=" + (level + 1));
                    break;

                  default:
                    window.location.replace("/verbal"); //ose nashta e bon me shku te /levels?game="loja" or whatever
                    break;
                }
              });
            }

          }
          else {
            setEndResult(constants.CORRECT_WORD);
            getCorrectSpellingList(stompClient.current,level,false);
          }

          for (let i = 0; i < wordsFound.length; i++) {
            if (wordsFound[i] == "") {
              wordsFound.splice(i, i, buttonarray[0]);
            }
          }

          setwordsFound(wordsFound => [...wordsFound, buttonarray[0]]);
          setCorrectWord(correctWords + 1);





        }
        buttonarray = [];
      });
      getCorrectSpellingList(this, level, true);
    });
  }, []);

  function clearContent() {
    setCorrectSpellingText([]);
    setCorrectSpellingArray([]);
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

  function getButtonContent(e) {
    let a = e.target.innerHTML;
    buttonarray.push(a);
    stompClient.current.send("/app/validactioncorrectspelling", {}, JSON.stringify(buttonarray));
    setCorrectSpellingText(buttonarray)
    e.target.blur();
  }

  function updateSws(a) {
    let buttonarray = correctSpellingArray
    buttonarray.push(a);
    setCorrectSpellingArray(buttonarray);
  }

  return (
    <div className="container-fluid">
      <Navbar />
      <div className="row">
        <div className="col-md-3">

          <Menu level={levelRender} playerName={cookies.get("username")} instructions={constants.CORRECTSPELLING_INSTRUCTIONS} pause={() => { setPause(value => !value) }}></Menu>
        </div>
        <div className="col-md-6 text-center all_words">
          <div className="row">
            {
              correctSpellingList.map((item) =>
                <div className="col-md-3 col-sm-6" key={item.word}>
                  {item !== ' ' ? <button className="btn btn-primary mb-5 p-3 rounded border-0 buttonsContainingWords" id={item} onClick={getButtonContent}>{item}</button> : <br></br>}
                </div>
              )
            }

          </div>
          <div className="result p-2">{
            correctSpellingText.length === 0 ? constants.CLICK_BTN_STR :
              correctSpellingText.map((item) =>
                <span>{item}</span>
              )
          }</div>
          <div className="mt-3" id="resultPartText">{endResult}</div>
          <button className="btn pl-5 pr-5 pt-2 pb-2 mt-3" onClick={clearContent}>{constants.CLEAR_BTN}</button>

        </div>
        <div className="col-md-3">
          <Sidebar level={levelRender} correctWords={wordsFound} elements={2 + 2 * levelRender}></Sidebar>
        </div>

      </div>
    </div>
  )
}
