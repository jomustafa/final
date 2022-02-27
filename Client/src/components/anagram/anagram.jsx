import React, { useRef, useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.css";
import "../../assets/stylesheets/main_styles.css";
import * as Stomp from "stompjs";
import SockJS from "sockjs-client";
import splitWordStyle from "../splitWords/style.css";
import Player from "../player/player";
import Level from "../level/level";
import Menu from "../menu/menu";
import swal from "sweetalert";
import Sidebar from "../sidebar/sidebar";
import Cookies from "universal-cookie";
import Navbar from "../navigation/Navbar";


let constants;
let link = window.location.toString();
let level = Number(link.split("=")[1]);


let missed = 0;
let buttonArray = [];
let anagramList = [];
export default function ClientComponent() {

  const cookies = new Cookies();

  const socket = useRef(new SockJS('/brainbright-websocket'));
  const stompClient = useRef(Stomp.over(socket.current));

  if (cookies.get("language") === "en") {
    constants = require("../../assets/metatext/constants");
  } else {
    constants = require("../../assets/metatext/constantsgr");
  }

  const [anagramArray, setAnagramArray] = useState([]);
  const [shuffledWord, setShuffledWord] = useState([]);
  const [anagramWord, setAnagramWord] = useState("");
  const [correctWord, setCorrectWord] = useState("");
  const [resultArray, setResultArray] = useState([]);
  const [endResult, setEndResult] = useState("");
  const [pause, setPause] = useState(false);
  const [levelRender, setLevelRender] = useState(Number(level));
  const [wordCount, setWordCount] = useState(0);
  const [correctWordsRender, setCorrectWordsRender] = useState([]);

  let wordcount = 0;
  let goal = false;

  useEffect(() => {
    stompClient.current.connect({}, function () {
      this.subscribe("/user/topic/anagramlist", function (data) {
        setAnagramArray(JSON.parse(data.body));

        anagramList = [];

        JSON.parse(data.body).map((item) => {
          anagramList.push(item);
        });

        let random = Math.floor(Math.random() * anagramList.length);
        let shuffled = shuffleArray(anagramList[random].split(""));
        console.log(anagramList[random]);
        setAnagramWord(anagramList.splice(random, 1)[0]);
        setShuffledWord(shuffled);
      });

      getAnagrams(level);
    });
  }, []);


  function getAnagrams(level) {
    console.log(level);
    stompClient.current.send("/app/getanagram", {}, JSON.stringify({level:level, id:cookies.get("currentUser"), language : cookies.get("language")}));
  }

  function shuffleArray(array) {
    for (let i = array.length - 1; i > 0; i--) {
      let j = Math.floor(Math.random() * (i + 1));
      [array[i], array[j]] = [array[j], array[i]];
    }
    return array;
  }

  function getButtonContent(e) {
    let a = e.target.innerHTML;
    buttonArray.push(a);
    setResultArray((arr) => [...arr, a]);
    console.log(resultArray);
  }

  function clearContent() {
    setResultArray([]);
    setEndResult("");
  }

  function checkValidity() {
    let found = true;

    for (let i = 0; i < anagramWord.length; i++) {
      if (resultArray[i] !== anagramWord[i]) {
        found = false;
        console.log(resultArray[i]);
        console.log(anagramWord[i]);
      }
    }


    function recordScore() {
      stompClient.current.send(
          "/app/anagram_recordscore",
          {},JSON.stringify(
          {
            name: cookies.get("currentUser"),
            level: level,
            missed: missed,
            points: 100,
          })
      );
    }


    if (found) {
      wordcount++;
      setWordCount((wordcount) => wordcount + 1);

      setCorrectWordsRender((corrWords) => [...corrWords, anagramWord]);

      clearContent();
      if (wordCount == 2 || wordCount >= 4) {
        recordScore();
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
                window.location.replace("/anagram?lvl=5");
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
                window.location.replace("/anagram?lvl=" + (level + 1));
                break;

              default:
                window.location.replace("/verbal"); 
                break;
            }
          });
        }

      } else {
        let random = Math.floor(Math.random() * anagramList.length);
        let shuffled = shuffleArray((anagramList[random].split("")));
        console.log(anagramList[random]);
        setAnagramWord(anagramList.splice(random, 1)[0]);

        setShuffledWord(shuffled);
      }
    } else {
      setEndResult(constants.WRONG);
      missed++;
    }
  }

  return (
    <div className="container-fluid">
      <Navbar />
      <div className="row">
        <div className="col-md-3">
          <Menu level={levelRender} playerName={cookies.get("username")} instructions={constants.ANAGRAM_INSTRUCTIONS} pause={() => { setPause(value => !value) }}></Menu>
        </div>
        <div className="col-md-6 text-center all_words">
          <h1>{constants.SHUFFLED_WORD_LABEL}</h1>
          <span>{shuffledWord}</span>

          <br></br>
          <br></br>

          <div className="row ">
            {resultArray.length === 0 ? (
              constants.CLICK_BTN_STR
            ) : (
              <button className="btn btn-primary mb-5 rounded border-0">
                {resultArray.join("")}
              </button>
            )}
          </div>
          <br></br>
          <br></br>

          <div className="row text-center">{endResult}</div>

          <br></br>
          <br></br>
          <div className="row ">
            {shuffledWord.map((item) => (
              <div className="col-md-2 col-sm-3" key={item.word}>
                <button
                  className="btn btn-primary mb-5 p-3 rounded border-0"
                  id={item}
                  onClick={getButtonContent}
                >
                  {item == " " ? constants.SPACE : item}
                </button>
              </div>
            ))}
          </div>

          <div className="row">
            <button
              className="btn pl-5 pr-5 pt-2 pb-2 mt-3"
              onClick={clearContent}
            >
              {constants.CLEAR_BTN}
            </button>
            <button
              className="btn pl-5 pr-5 pt-2 pb-2 mt-3"
              onClick={checkValidity}
            >
              {constants.SUBMIT}
            </button>
          </div>

          <br></br>
          <br></br>
          <br></br>
          <br></br>
        </div>
        <div className="col-md-3">
          <Sidebar
            level={levelRender}
            correctWords={correctWordsRender}
            elements={2 + levelRender}
          ></Sidebar>
        </div>
      </div>
    </div>
  );
}
