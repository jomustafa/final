import React, { useEffect, useState } from "react";
import socketIOClient from "socket.io-client";
const ENDPOINT = "http://127.0.0.1:3050";

let splitWordArray = [];
let splitWord = '';

export default function ClientComponent() {
  var [splitWords, setSplitWords] = useState([]);
  var [result, setResult] = useState();

  useEffect(() => {
    const socket = socketIOClient(ENDPOINT);
    socket.on("all_split_words", data => {
      setSplitWords(data.splitWords);
      // splitWordArray.map((item) => {
      //   splitWord += item;
      // });

    });
    socket.on("result", data => {
      setResult(data);
      console.log(data);

    });

    console.log(splitWordArray);
    socket.emit("chat", splitWordArray);

  }, []);


  return (
    <div className="row">
      <div className="col-md-4">
        <div className="row">
          <div className="d-flex">
            <div className="player">
              Player:
                    </div>
            <div className="player-name">
              Name
                    </div>
          </div>
        </div>
        <div className="row">
          Level
            </div>
        <div className="row">
          Menu
            </div>
      </div>
      <div className="col-md-6">
        <div className="row">
          {
            splitWords.map((item) =>
              <div className="col-md-3">
                <button className="btn btn-primary text-center" onClick={GetButtonContent}>{item.firstPart}</button>
              </div>
            )
          }
          {
            splitWords.map((item) =>
              <div className="col-md-3">
                <button className="btn btn-primary mb-3">{item.secondPart}</button>
              </div>
            )
          }

        </div>
        <div className="row">
          <span>Incorrect pair:</span>
          <div className="button-class">
            <button className="btn">Clear</button>
          </div>
        </div>
      </div>
      <div className="col-md-2">
        <div className="row">
          Round:
                <div className="col-md-12">1</div>
          <div className="col-md-12">2</div>
        </div>

      </div>

    </div>
  )
}

function GetButtonContent(e) {
  if (splitWordArray.length < 2)
    splitWordArray = [...splitWordArray, e.target.innerHTML];
  else {
    splitWordArray = [];
    splitWordArray = [...splitWordArray, e.target.innerHTML];
  }
  console.log(splitWordArray);
}
