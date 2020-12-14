import React, { useEffect, useState } from "react";
import splitWordStyle from "./style.css";
import * as Stomp from "stompjs";
import SockJS from "sockjs-client";

//
var socket = new SockJS('https://brainbright.herokuapp.com/brainbright-websocket');
var stompClient = Stomp.over(socket);

let submittedFieldID= " ";
function getNapCharacter(client) {
    client.send("/app/nap_getcharacter", {}, null);
}
function isValidAction(event) {
    if (event.keyCode === 13) {
        submittedFieldID = event.target.id;
        console.log(submittedFieldID);
        let word = document.getElementById(submittedFieldID).value.toUpperCase();
        stompClient.send("/app/nap_validaction", {}, JSON.stringify({ "word": word, "type": event.target.id }));
    }
}
export default function ClientComponent() {
    const [splitWord, setSplitWord] = useState('');
    const [splitWords, setSplitWords] = useState([]);
    const [napCharacter, setNapCharacter] = useState('');
    const [splitWordArray, setSplitWordArray] = useState([]);
    const [endResult, setEndResult] = useState('');
    const [splitWordText, setSplitWordText] = useState([]);

    const [categories, setCategories] = useState(["plant", "animal", "occupation", "name", "country"]);

    useEffect(() => {

        stompClient.connect({}, function () {

            this.subscribe('/topic/nap_getcharacterresponse', function (data) {
                console.log(data.body);
                setNapCharacter(data.body);
            });

            getNapCharacter(this);
            this.subscribe('/topic/nap_validactionresponse', function (data) {
                if (data.body == 0) {
                    document.getElementById("result").innerHTML = "Incorrect word!";
                    document.getElementById(submittedFieldID).value = "";
                } else if (data.body == 1) {
                    document.getElementById("result").innerHTML = "Correct word!";
                    document.getElementById(submittedFieldID).disabled = true;
                    document.getElementById(submittedFieldID).style.backgroundColor = "lightgreen";
                } else { //data === -1
                    document.getElementById("result").innerHTML = "You already submitted that word!";
                    document.getElementById(submittedFieldID).disabled = "lightred";
                }
            });
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
            stompClient.subscribe('/topic/validactionresponse', function (splitwords) {
                if (!JSON.parse(splitwords.body)) {
                    setEndResult('You are wrong');
                } else {
                    setEndResult('You are correct');
                }
            });
            stompClient.send("/app/validaction", {}, JSON.stringify({ "firstPart": splitWordArray[0], "secondPart": splitWordArray[1] }));
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
                    <div className="starting-character p-2 ">{
                        "The chosen character is" + napCharacter
                    }</div>
                    <div className="row">
                        {
                            categories.map((item) =>
                                <div className="col-md-3 col-sm-6" key={item.word}>
                                    {item !== ' ' ? <p>{item}</p> : <br></br>}
                                    <label>

                                        <input type="text" name="name" id={item} onKeyDown={isValidAction} />
                                    </label>
                                </div>
                            )
                        }
                        <div className="result p-2" id="result">{
                            "Waiting to submit a word..."
                        }</div>
                    </div>


                    <div className="mt-3">{endResult}</div>
                    <button className="btn pl-5 pr-5 pt-2 pb-2 mt-3" onClick={clearContent}>Clear</button>

                </div>
                <div className="col-md-3">
                    <div className="text-center">
                        <div className="border border-dark">Submitted words</div>
                    </div>

                </div>

            </div>
        </div>
    )
}

