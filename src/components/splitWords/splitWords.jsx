import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.css";
import * as Stomp from "stompjs";
import SockJS from "sockjs-client";
import splitWordStyle from "./style.css";
import Player from '../player/player';
import Level from '../level/level';
import Menu from '../menu/menu';


var socket = new SockJS('http://localhost:8080/brainbright-websocket');
var stompClient = Stomp.over(socket);

let buttonarray = [];
function getSplitWords(client) {
  client.send("/app/getsplitwords", {}, null);
}

export default function ClientComponent() {
  const [splitWords, setSplitWords] = useState([]);
  //const [splitWord, setSplitWord] = useState('');
  const [splitWordArray, setSplitWordArray] = useState([]);
  const [endResult, setEndResult] = useState('');
  const [splitWordText, setSplitWordText] = useState([]);

  //This array has elements equal to the number of words we get from the backend. Used for the sidebar.
  const[wordList, setWordList] = useState([]);

  //integer representing the number of words the user has guessed
  const [correctWords, setCorrectWord] = useState(0);
  useEffect(() => {

    stompClient.connect({}, function () {

      this.subscribe('/topic/splitwordlist', function (data) {
        setWordList(JSON.parse(data.body));  
        let array = new Array(24 - JSON.parse(data.body).length * 2).fill(' ');
        JSON.parse(data.body).map((item) => {
          array.splice(Math.floor(Math.random() * 24), 0, item.firstPart);
          array.splice(Math.floor(Math.random() * 24), 0, item.secondPart);
        });
        setSplitWords(array);
      });

      stompClient.subscribe('/topic/sws_validactionresponse', function (splitwords) {
        if (JSON.parse(splitwords.body)===0) {
          setEndResult('You are wrong');
        } else if (JSON.parse(splitwords.body)===1){
          setEndResult('You are correct');
          //console.log("swarray length: " + splitWordArray.length);
          
          document.getElementById(buttonarray[0]).style.display = "none";
          
          document.getElementById(buttonarray[1]).style.display = "none";
            const result = buttonarray[0]+buttonarray[1];
            document.getElementsByClassName("resultrow")[correctWords].innerHTML = result;
            setCorrectWord(correctWords+1);
        }else if (JSON.parse(splitwords.body)===2){
          console.log("something something victory");
        }
        buttonarray = [];
      });
      getSplitWords(this);
    });
  }, []);

  function clearContent() {
    setSplitWordText([]);
    setSplitWordArray([]);
  }


  function getButtonContent(e) {
    //Push the new item into the array of buttons
    let a = e.target.innerHTML;
    buttonarray.push(a);

    //let textArray = splitWordText;

    //If it's 2 buttons clicked, send the request
    if(buttonarray.length===2){
      //setSplitWordArray(buttonArray);
      stompClient.send("/app/sws_validaction", {}, JSON.stringify({ "firstPart": buttonarray[0], "secondPart": buttonarray[1] }));
      setSplitWordText(buttonarray)
    }else{
      setSplitWordText([e.target.innerHTML]);
    }

    
      
    //   buttonArray.push(e.target);
    //   setSplitWordArray(buttonArray);
    //   setSplitWordText(textArray);

    // if(splitWordArray.length==2){
      
    // }else{

    // }




    // if (splitWordArray.length >= 2) {
    //   setSplitWordArray.length === 2 ? setSplitWordArray([]) : setSplitWordText([]);
    //   setSplitWordArray([e.target]);
    //   setSplitWordText([e.target.innerHTML]);
    // } else {
      
    //   buttonArray.push(e.target);

    //   let textArray = [];
    //   splitWordArray.map(item=>
    //     textArray.push(item.innerHTML)
    //   )
    //   setSplitWordArray(buttonArray);
    //   setSplitWordText(textArray);
    // }

    

    // if (splitWordArray.length === 2) {
    //   console.log("the length of the array is " + splitWordArray.length);
    //   //setSplitWord('');
      
      
    //   stompClient.send("/app/sws_validaction", {}, JSON.stringify({ "firstPart": splitWordArray[0].innerHTML, "secondPart": splitWordArray[1].innerHTML }));
    // }
  }

  function updateSws(a){
    let buttonarray = splitWordArray
    buttonarray.push(a);
    setSplitWordArray(buttonarray);
  }
  return (
    <div className="container-fluid">

      <div className="row">
        <div className="col-md-3">
          <Player name="PlayerName"></Player>
          <Level level="5"></Level>
          <Menu></Menu>
        </div>
        <div className="col-md-6 text-center all_words">
          <div className="row">
            {
              splitWords.map((item) =>
                <div className="col-md-3 col-sm-6" key={item.word}>
                  {item !== ' ' ? <button className="btn btn-primary mb-5 p-3 rounded border-0" id={item} onClick={getButtonContent}>{item}</button> : <br></br>}
                </div>
              )
            }

          </div>
          <div className="result p-2">{
            splitWordText.length === 0 ? "Click a button..." :
              splitWordText.map((item) =>
                <span>{item}</span>
              )
          }</div>
          <div className="mt-3">{endResult}</div>
          <button className="btn pl-5 pr-5 pt-2 pb-2 mt-3" onClick={clearContent}>Clear</button>

        </div>
        <div className="col-md-3">
          <div className="text-center">
            Found
            <div className="border border-dark">{correctWords} out of 4:</div>
            {
              wordList.map(item =>
                <div className="border border-dark resultrow" id={item.word}></div>
              )
            }
            
          </div>

        </div>

      </div> 
    </div>
  )
}

