import React, { useEffect, useState } from "react";
import socketIOClient from "socket.io-client";
import splitWordStyle from "./style.css";
import * as Stomp from "stompjs";
import SockJS from "sockjs-client";
import SockJsClient from 'react-stomp';
// const ENDPOINT = "http://127.0.0.1:3060";
// const socket = socketIOClient(ENDPOINT);
connect();

var stompClient = null;
var buttonStack = [];
var splitWords = [];


function connect() {
  // <SockJsClient 
  // url='http://localhost:8080/brainbright-websocket' 
  // topics={['/topic/splitwordlist']}
  //   onMessage={(msg) => { console.log(msg); }}
  //   ref={(client) => { this.clientRef = client }} />
  var socket = new SockJS('http://127.0.0.1:8080/brainbright-websocket');
  stompClient = Stomp.over(socket);
  
  stompClient.connect({}, function (frame) {
    // console.log('Connected: ' + frame);
    console.log(this, 'CLIENT');
    // stompClient.subscribe('/topic/validactionresponse', function(splitwords) {
    // 	// if (!JSON.parse(splitwords.body)) {
    // 	// 	showResponse("That's wrong!");
    // 	// } else {
    // 	// 	showResponse("Correct!");	
    //   // }
    //   console.log(splitWords)
    // });
    this.subscribe('/topic/splitwordlist', function (result) {
      // splitWords = JSON.parse(result.body);
      // initButtons();
      console.log(result);
    });
    getSplitWords(this);
  });
}

function getSplitWords(client) {
  console.log("ayo? getsplitwords");
  client.send("/app/getsplitwords", {}, null);
}

export default function ClientComponent() {
  // const [splitWords, setSplitWords] = useState([]);
  // const [result, setResult] = useState();
  // const [splitWord, setSplitWord] = useState('');
  // const [splitWordArray, setSplitWordArray] = useState([]);
  // const [endResult, setEndResult] = useState('');

  // useEffect(() => {
  //   socket.on("all_split_words", data => {
  //     let array = new Array(24 - data.splitWords.length * 2).fill(' ');
  //     data.splitWords.map((item) => {
  //       array.splice(Math.floor(Math.random() * 24), 0, item.firstPart);
  //       array.splice(Math.floor(Math.random() * 24), 0, item.secondPart);
  //     });
  //     setSplitWords(array);
  //   });
  // }, []);


  // function GetButtonContent(e) {

  //   if (splitWordArray.length >= 2) {
  //     setSplitWordArray([]);
  //     setSplitWordArray([e.target.innerHTML]);
  //   } else {
  //     const newArray = splitWordArray;
  //     newArray.push(e.target.innerHTML);
  //     console.log(splitWordArray.length);
  //     setSplitWordArray(newArray);
  //   }

  //   if (splitWordArray.length === 2) {
  //     socket.emit("result", splitWordArray.join(''));
  //     setSplitWord('');
  //     setSplitWordArray([]);
  //     socket.on("result", data => {
  //       setResult(data);
  //       if (data) {
  //         setEndResult('You are correct');
  //       } else {
  //         setEndResult('You are wrong');
  //       }
  //     });
  //   }
  // }


  return (
    <div className="container-fluid">test
      {/* <div className="row">
        <div className="col-md-3">
          <div className="d-flex">
            <div className="player">
              Player:
                </div>
            <div className="player-name">
              Name
              </div>
          </div>

          <div className="">
            Level
            </div>
          <div className="">
            Menu
            </div>
        </div>
        <div className="col-md-6 text-center all_words">
          <div className="row">
            {
              splitWords.map((item) =>
                <div className="col-md-3" key={item.word}>
                  {item !== ' ' ? <button className="btn btn-primary mb-5 p-3 rounded border-0" onClick={GetButtonContent}>{item}</button> : <br></br>}
                </div>
              )
            }

          </div>
          <div className="result p-2">Incorrect pair:</div>
          <button className="btn pl-5 pr-5 pt-2 pb-2 mt-5">Clear</button>

        </div>
        <div className="col-md-3">
          <div className="text-center">
            Round:
            <div>{endResult}</div>
            <div>2</div>
          </div>

        </div>

      </div> */}
    </div>
  )
}

