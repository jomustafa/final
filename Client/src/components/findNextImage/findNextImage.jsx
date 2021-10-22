import React, { useRef, isValidElement, useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.css";
import "../../assets/stylesheets/main_styles.css";
import * as Stomp from "stompjs";
import SockJS from "sockjs-client";
import Player from "../player/player";
import Level from "../level/level";
import Menu from "../menu/menu";
import swal from "sweetalert";
import Sidebar from "../sidebar/sidebar";
import Navbar from "../navigation/Navbar";
import Cookies from "universal-cookie";


let buttonarray = [];
let constants;

let selected = false;
let lastword = "";
let correctWords = 0;
let link = window.location.toString();
let level = Number(link.split("=")[1]);
let images = [];
let flag = false;
export default function ClientComponent() {
  const socket = useRef(new SockJS('/brainbright-websocket'));
  const stompClient = useRef(Stomp.over(socket.current));
  //The Image names
  const cookies = new Cookies();

  //When switching menus, need to connect here instead
  if (cookies.get("language") === "en") {
    constants = require("../../assets/metatext/constants");
  } else {
    constants = require("../../assets/metatext/constantsgr");
  }

  //TextBox at the bottom showing correct/wrong
  const [incorrectWords, setIncorrectWords] = useState(0);
  const [endResult, setEndResult] = useState("");
  const [levelRender, setLevelRender] = useState(Number(level));
  const [currentImage, setCurrentImage] = useState("");
  const [pause, setPause] = useState(false);
  useEffect(() => {
    document.getElementById("nextImage").disabled = true;
    stompClient.current.connect({}, function () {
      //Executed whenever a list of words is received
      this.subscribe("/user/topic/fn_imagelist", function (data) {
        console.log(data.body);
        document.getElementById("nextImage").disabled = false;
        //Push the names from the Image objects into image array
        JSON.parse(data.body).map((item) => {
          images.push(item);
        });
        setCurrentImage(images.pop());
        console.log(images);
      });
      getImages(level);
    });
  }, []);

  function getImages(level) {
    console.log(level);
    stompClient.current.send("/app/fn_getimages", {}, level);
  }


  function gameOver(gameOverText) {
    // swal(gameOverText, {
    //   buttons: {
    //     cancel: {
    //       text: "Abort game!",
    //       value: "cancel",
    //     },
    //     retry: {
    //       text: "Restart game!",
    //       value: "retry",
    //     },
    //     defeat: true,
    //   },
    // }
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
        case "cancel":
          window.location.replace("/visual");
          break;

        case "retry":
          getImages(1);
          setIncorrectWords(0);
          break;

        default:
          window.location.replace("/visual");
          break;
      }
    });
  }

  function nextButton() {
    if (currentImage == lastword) {
      if (!selected) {
        gameOver("That was a duplicate word!");
      }
    }
    lastword = currentImage;
    setCurrentImage(images[Math.floor(Math.random() * images.length)]);
    selected = false;
  }

  function selectImage() {
    let incorrect = incorrectWords;
    if (currentImage == lastword && !selected) {
      setEndResult("You are correct");
      document.getElementById("resultPartText").style.backgroundColor = "green";
      selected = true;
      correctWords++;
    } else {
      setIncorrectWords((inc) => inc + 1);
      incorrect++;
      setEndResult("You are wrong");
      document.getElementById("resultPartText").style.backgroundColor =
        "#DC143C";
    }

    if (incorrect === 3) {
      gameOver("You have guessed incorrectly " + incorrect + " times.");
      stompClient.current.send(
        "/app/fn_recordScore",
        {},
        JSON.stringify({
          name: cookies.get("currentUser"),
          level: level,
          missed: incorrect,
          found: correctWords,
        })
      );
    }

    if (correctWords === 3) {
      // swal("Good job!", "You just leveled UP!!!", "success", {
      //   button: "Press here to continue!",
      // }).then(function () {
      //   stompClient.current.send(
      //     "/app/fn_recordScore",
      //     {},
      //     JSON.stringify({
      //       "name": cookies.get("currentUser"),
      //       "level": level,
      //       "missed": incorrect,
      //       "found": correctWords,
      //     })
      //   );
      //   correctWords = 0;
      //   level++;
      //   getImages(level);
      //   setIncorrectWords(0);
      //   setLevelRender((level) => level + 1);
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
              window.location.replace("/photographic"); //ose nashta e bon me shku te /levels?game="loja" or whatever
              break;

            case "next":
              window.location.replace("/findnextimage?lvl=5");
              break;

            default:
              window.location.replace("/photographic"); //ose nashta e bon me shku te /levels?game="loja" or whatever
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
              window.location.replace("/photographic"); //ose nashta e bon me shku te /levels?game="loja" or whatever
              break;

            case "next":
              window.location.replace("/findnextimage?lvl=" + (level + 1));
              break;

            default:
              window.location.replace("/photographic"); //ose nashta e bon me shku te /levels?game="loja" or whatever
              break;
          }
        });
      }

    }
  }

  function clearContent() {
    buttonarray = [];
  }

  // function getButtonContent(item) {
  //     //Push the new item into the array of buttons
  //     item.persist();
  //     buttonarray.push(item.target);
  //     console.log();
  //     console.log(item.target);
  //     //"url("+require("../../photographs/findpairs/questionmark.png")+")");
  //     item.target.style.backgroundImage = "url(" + require("../../photographs/findpairs/" + item.target.getAttribute("value") + ".png") + ")";
  //     item.target.disabled = true;
  //     item.target.onclick = "";
  //     // setTimeout(() => {
  //     //If it's 2 buttons clicked, send the request
  //     let action = "";
  //     if (buttonarray.length === 2) {
  //         if (buttonarray[0].getAttribute("value") === buttonarray[1].getAttribute("value")) {
  //             correctWords = correctWords + 2;

  //             if (correctWords === images.length) {
  //                 action = 2;
  //             } else {
  //                 action = 1;
  //             }
  //         } else {
  //             action = 0;
  //         }
  //         // performAction(action);
  //     }
  //     // }, 200);
  // }

  return (
    <div className="container-fluid">
      <Navbar />
      <div className="row">
        <div className="col-md-3">
          <Menu level={levelRender} playerName={cookies.get("username")} instructions={constants.FINDNEXTBOX_INSTRUCTIONS} pause={() => { setPause(value => !value) }}></Menu>
        </div>

        <div className="col-md-6 text-center all_words">
          <button onClick={selectImage}>
            <img
              height="500px"
              width="500px"
              src={
                currentImage !== ""
                  ? "/photographs/findnext/" + currentImage + ".jpg"
                  : null
              }
            ></img>
          </button>

          <br></br>
          <br></br>
          <button
            className="btn pl-5 pr-5 pt-2 pb-2 mt-6"
            id="nextImage"
            onClick={nextButton}
          >
            Next image
          </button>
          <div className="mt-3" id="resultPartText">
            {endResult}
          </div>
        </div>
        <div className="mistakes ml-5 mt-5">
          Mistakes
          <div className="mt-3">
            <span className="border p-3">{incorrectWords}</span> Out of{" "}
            <span className="border p-3">{level + 2}</span>
          </div>
        </div>
      </div>
    </div>
  );
}
