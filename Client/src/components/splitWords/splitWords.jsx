import React, { useEffect, useState, useRef } from "react";
import "bootstrap/dist/css/bootstrap.css";
import "../../assets/stylesheets/main_styles.css";
import * as Stomp from "stompjs";
import SockJS from "sockjs-client";
import splitWordStyle from "./style.css";
import Player from "../player/player";
import Level from "../level/level";
import Menu from "../menu/menu";
import swal from "sweetalert";
import { whileStatement } from "@babel/types";
import { func } from "prop-types";
import Sidebar from "../sidebar/sidebar";
import Navbar from "../navigation/Navbar";
import axios from "axios";
import Cookies from "universal-cookie";


let buttonarray = [];
let arrayofbuttons = []; //will hold literal buttons to disable
let constants;
let link = window.location.toString();
let level = Number(link.split("=")[1]);

export default function ClientComponent() {
  const cookies = new Cookies();
  const socket = useRef(new SockJS('/brainbright-websocket'));
  const stompClient = useRef(Stomp.over(socket.current));



  if (cookies.get("language") === "en") {
    constants = require("../../assets/metatext/constants");
  } else {
    constants = require("../../assets/metatext/constantsgr");
  }

  //The sw buttons
  const [splitWords, setSplitWords] = useState([]);
  //TextBox at the bottom showing correct/wrong
  const [endResult, setEndResult] = useState("");
  //TextBox at the bottom showing the submitted word
  const [splitWordText, setSplitWordText] = useState([]);

  const [levelRender, setLevelRender] = useState(Number(level));

  //Integer representing the number of words the user has guessed
  const [correctWords, setCorrectWord] = useState(0);

  //The submitted correct words
  const [correctWordsRender, setCorrectWordsRender] = useState([]);

  const [pause, setPause] = useState(false);


  let wordcount = 0;
  useEffect(() => {
    stompClient.current.connect({}, function () {
      //Executed whenever a list of words is received
      this.subscribe("/user/topic/splitwordlist", function (data) {
        //Change all the buttons to visible, used in order to reset the buttons after a levelup
        let buttons = document.getElementsByClassName("swbutton");
        Array.from(buttons).forEach((el) => {
          el.style.visibility = "visible";
        });
        let sidebar = document.getElementsByClassName("resultrow");
        Array.from(sidebar).forEach((el) => {
          el.innerHTML = " ";
        });
        wordcount = 0;
        setCorrectWord(0);

        //Create 4x6 grid
        let array = new Array(24 - JSON.parse(data.body).length * 2).fill(" ");
        JSON.parse(data.body).map((item) => {
          array.splice(Math.floor(Math.random() * 24), 0, item.firstPart);
          array.splice(Math.floor(Math.random() * 24), 0, item.secondPart);
        });
        setSplitWords(array);
      });

      //Executed whenever server responds two two words
      stompClient.current.subscribe(
        "/user/topic/sws_validactionresponse",
        function (splitwords) {
          //If incorrect word
          if (JSON.parse(splitwords.body) === 0) {
            arrayofbuttons.forEach((e)=>{
              e.disabled = false;
            });
            setEndResult(constants.WRONG);
          } else {
            //if correct result

            setEndResult(constants.CORRECT);

            //Hide buttons of correct guesses
            document.getElementById(buttonarray[0]).style.visibility = "hidden";
            document.getElementById(buttonarray[1]).style.visibility = "hidden";
            const result = buttonarray[0] + buttonarray[1];

            //If game continues after result
            if (JSON.parse(splitwords.body) === 1) {
              //document.getElementsByClassName("resultrow")[correctWords].innerHTML = result;
              setCorrectWord((correctWords) => correctWords + 1);
              setCorrectWordsRender((corrWords) => [...corrWords, result]);
            } else if (JSON.parse(splitwords.body) === 2) {
              //2 = gameOver
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
                      window.location.replace("/verbal");
                      break;
  
                    case "next":
                      window.location.replace("/splitword?lvl=5");
                      break;
  
                    default:
                      window.location.replace("/verbal");
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
                      window.location.replace("/verbal");
                      break;
  
                    case "next":
                      window.location.replace("/splitword?lvl=" + (level + 1));
                      break;
  
                    default:
                      window.location.replace("/verbal");
                      break;
                  }
                });
              }
              setCorrectWordsRender((corrWords) => [...corrWords, result]);
            }
          }
          buttonarray = [];
          arrayofbuttons = [];
        }
      );
      getSplitWords(this, level);
    });
  }, []);

  function getSplitWords(client, level) {
    console.log(level);
    client.send(
      "/app/getsplitwords",
      {},
      JSON.stringify({ level: level, id: cookies.get("currentUser"), language: cookies.get("language") })
    );
  }

  function clearContent() {
    setSplitWordText([]);
    buttonarray = [];
    arrayofbuttons.forEach(el=>el.disabled=false);
    arrayofbuttons = [];

    //setSplitWordArray([]);
  }

  function getButtonContent(e) {
    //Push the new item into the array of buttons
    let a = e.target.innerHTML;
    buttonarray.push(a);
    arrayofbuttons.push(e.target);
    e.target.disabled = true;
    //let textArray = splitWordText;

    //If it's 2 buttons clicked, send the request
    if (buttonarray.length === 2) {
      stompClient.current.send(
        "/app/sws_validaction",
        {},
        JSON.stringify({
          firstPart: buttonarray[0],
          secondPart: buttonarray[1],
        })
      );
      setSplitWordText(buttonarray);
    } else {
      setSplitWordText([e.target.innerHTML]);
    }
  }

  return (
    <div className="container-fluid ">
      <Navbar />
      <div className="row">
        <div className="col-md-3">
          {/* <Player name="PlayerName"></Player> */}

          <Menu level={levelRender} playerName={cookies.get("username")} instructions={constants.SPLITWORDS_INSTRUCTIONS} pause={() => { setPause(value => !value) }}></Menu>
        </div>
        <div className="col-md-6 text-center all_words">
          <div className="row">
            {splitWords.map((item) => (
              <div className="col-md-3 col-sm-6" key={item.word}>
                {item !== " " ? (
                  <button
                    className="btn btn-primary mb-5 p-3 rounded border-0"
                    id={item}
                    onClick={getButtonContent}
                  >
                    {item}
                  </button>
                ) : (
                  <br></br>
                )}
              </div>
            ))}
          </div>
          <div className="result p-2">{
            splitWordText.length === 0 ? constants.CLICK_BTN_STR :
              splitWordText.map((item) =>
                <span>{item}</span>
              )
          }</div>
          <div className="mt-3">{endResult}</div>
          <button
            className="btn pl-5 pr-5 pt-2 pb-2 mt-3 gamebutton"
            onClick={clearContent}
          >
            {constants.CLEAR_BTN}
          </button>
        </div>
        <div className="col-md-3">
          <Sidebar
            level={levelRender}
            correctWords={correctWordsRender}
            elements={2 + 2 * levelRender}
          ></Sidebar>
        </div>
      </div>
    </div>
  );
}
