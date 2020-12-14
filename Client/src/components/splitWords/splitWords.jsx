import React, { useEffect, useState } from "react";
import splitWordStyle from "./style.css";
import * as Stomp from "stompjs";
import SockJS from "sockjs-client";

var socket = new SockJS('https://brainbright.herokuapp.com/brainbright-websocket');
var stompClient = Stomp.over(socket);

function getSplitWords(client) {
  client.send("/app/getsplitwords", {}, null);
}

export default function ClientComponent() {
  const [splitWords, setSplitWords] = useState([]);
  const [splitWord, setSplitWord] = useState('');
  const [splitWordArray, setSplitWordArray] = useState([]);
  const [endResult, setEndResult] = useState('');
  const [splitWordText, setSplitWordText] = useState([]);

  useEffect(() => {

    stompClient.connect({}, function () {

      this.subscribe('/topic/splitwordlist', function (data) {
        let array = new Array(24 - JSON.parse(data.body).length * 2).fill(' ');
        JSON.parse(data.body).map((item) => {
          array.splice(Math.floor(Math.random() * 24), 0, item.firstPart);
          array.splice(Math.floor(Math.random() * 24), 0, item.secondPart);
        });
        setSplitWords(array);
      });

      getSplitWords(this);
    });
  }, []);

  function clearContent() {
    setSplitWordText([]);
    setSplitWordArray([]);
  }


  function getButtonContent(e) {
    if (splitWordArray.length >= 2) {
      setSplitWordArray.length == 2 ? setSplitWordArray([]) : setSplitWordText([]);
      setSplitWordArray([e.target.innerHTML]);
      setSplitWordText([e.target.innerHTML]);
    } else {
      const newArray = splitWordArray;
      newArray.push(e.target.innerHTML);
      setSplitWordArray(newArray);
      setSplitWordText(newArray);
    }

    if (splitWordArray.length === 2) {

      setSplitWord('');
      setSplitWordArray([]);
      stompClient.subscribe('/topic/sws_validactionresponse', function (splitwords) {
        if (!JSON.parse(splitwords.body)) {
          setEndResult('You are wrong');
        } else {
          setEndResult('You are correct');
        }
      });
      stompClient.send("/app/sws_validaction", {}, JSON.stringify({ "firstPart": splitWordArray[0], "secondPart": splitWordArray[1] }));
    }
  }

  return (
    <div className="container-fluid">

      <div className="row">
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
                <div className="col-md-3 col-sm-6" key={item.word}>
                  {item !== ' ' ? <button className="btn btn-primary mb-5 p-3 rounded border-0" onClick={getButtonContent}>{item}</button> : <br></br>}
                </div>
              )
            }

          </div>
          <div className="result p-2">{
            splitWordText.length == 0 ? "Click a button..." :
              splitWordText.map((item) =>
                <span>{item}</span>
              )
          }</div>
          <div className="mt-3">{endResult}</div>
          <button className="btn pl-5 pr-5 pt-2 pb-2 mt-3" onClick={clearContent}>Clear</button>

        </div>
        <div className="col-md-3">
          <div className="text-center">
            <div className="border border-dark">test</div>
          </div>

        </div>

      </div> 
    </div>
  )
}

