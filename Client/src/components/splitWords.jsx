import React, { useEffect, useState } from "react";
import socketIOClient from "socket.io-client";
const ENDPOINT = "brainbright.herokuapp.com:443";

export default function ClientComponent() {
  const [splitWords, setSplitWords] = useState([]);
  const [result, setResult] = useState();
  const [splitWord, setSplitWord] = useState('');
  const [splitWordArray, setSplitWordArray] = useState([]);
  const [endResult, setEndResult] = useState('');

  useEffect(() => {
    const socket = socketIOClient(ENDPOINT);
    socket.on("all_split_words", data => {
      setSplitWords(data.splitWords);
    });
  }, []);

  function GetButtonContent(e) {
    if (splitWordArray.length >= 2) {
      setSplitWordArray([]);
      setSplitWordArray([e.target.innerHTML]);
    } else {
      const newArray = splitWordArray;
      newArray.push(e.target.innerHTML)
      setSplitWordArray(newArray);
    }


    const socket = socketIOClient(ENDPOINT);
    console.log(splitWordArray, splitWordArray.length)
    if (splitWordArray.length === 2) {
      socket.emit("result", splitWordArray.join(''));
      setSplitWord('');
      setSplitWordArray([]);
      socket.on("result", data => {
        setResult(data);
        console.log(data);
        if (data) {
          setEndResult('You are correct');
        } else {
          setEndResult('You are wrong');
        }
      });
    }

  }

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
              <div className="col-md-3" key={item.word}>
                <button className="btn btn-primary text-center" onClick={GetButtonContent}>{item.firstPart}</button>
              </div>
            )
          }
          {
            splitWords.map((item) =>
              <div className="col-md-3" key={item.word}>
                <button className="btn btn-primary mb-3" onClick={GetButtonContent}>{item.secondPart}</button>
              </div>
            )
          }

        </div>
        <div className="row">
          <span></span>
          <span>Incorrect pair:</span>
          <div className="button-class">
            <button className="btn">Clear</button>
          </div>
        </div>
      </div>
      <div className="col-md-2">
        <div className="row">
          Round:
        <div className="col-md-12">{endResult}</div>
          <div className="col-md-12">2</div>
        </div>

      </div>

    </div>
  )
}


