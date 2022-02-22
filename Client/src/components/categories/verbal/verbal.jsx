import React from "react";
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";
import splitWordLogo from '../../../assets/images/splitword.jpg';
import namesAnimalsPlantsLogo from '../../../assets/images/namesanimalsplants.jpg';
import wordexLogo from '../../../assets/images/wordex.jpg';
import wordSearchLogo from '../../../assets/images/wordsearch.jpg';
import anagramLogo from '../../../assets/images/anagram.jpg';
import hangmanLogo from '../../../assets/images/hangman.jpg';
import wordsOfWondersLogo from '../../../assets/images/wordsofwonders.jpg';

import style from "./verbal.css";
import Navbar from "../../navigation/Navbar";
import Cookies from "universal-cookie";


let constants;

export default function Verbal() {

    const cookies = new Cookies();

    if(cookies.get("language") === "en"){
      constants = require("../../../assets/metatext/constants");
    } else {
      constants = require("../../../assets/metatext/constantsgr");
    }

    return (
        <div>
            <Navbar />
            <Router>
                <div className="container">
                    <div className="row justify-content-center mt-5">
                        <a href="/levels?game=splitword">
                            <div className="card p-3 mr-2 mb-3">
                                <div className="card-header">
                                    <img src={splitWordLogo} style={{width: "200px", height: "150px"}} />
                                </div>
                                <div className="card-body"><div className="title">{constants.SPLIT_WORDS} </div></div>
                            </div>
                        </a>
                        <a href="/levels?game=animals">
                            <div className="card p-3 mr-2 mb-3">
                                <div className="card-header">
                                    <img src={namesAnimalsPlantsLogo} style={{width: "200px", height: "150px"}}/>
                                </div>
                                <div className="card-body"><div className="title">{constants.NAMES_ANIMALS_PLANTS} </div></div>
                            </div>
                        </a>
                

                        <a href="/levels?game=anagram">
                            <div className="card p-3 mr-2 mb-3">
                                <div className="card-header">
                                    <img src={anagramLogo} style={{width: "200px", height: "150px"}} />
                                </div>
                                <div className="card-body"><div className="title">{constants.ANAGRAM}<br/></div></div>
                            </div>
                        </a>

                        <a href="/levels?game=wordex">
                            <div className="card p-3 mr-2 mb-3">
                                <div className="card-header">
                                    <img src={wordexLogo} style={{width: "200px", height: "150px"}}/>
                                </div>
                                <div className="card-body"><div className="title">{constants.WORDEX}</div></div>
                            </div>
                        </a>
                        <a href="/levels?game=wordsearch">
                            <div className="card p-3 mr-2 mb-3">
                                <div className="card-header">
                                    <img src={wordSearchLogo} style={{width: "200px", height: "150px"}}/>
                                </div>
                                <div className="card-body"><div className="title">{constants.WORD_SEARCH}</div></div>
                            </div>
                        </a>
                        <a href="/levels?game=hangman/category">
                            <div className="card p-3 mr-2 mb-3">
                                <div className="card-header">
                                    <img src={hangmanLogo} style={{width: "200px", height: "150px"}}/>
                                </div>
                                <div className="card-body"><div className="title">{constants.HANGMAN}</div></div>
                            </div>
                        </a>
                        <a href="/levels?game=wordofwonders">
                            <div className="card p-3 mr-2 mb-3">
                                <div className="card-header">
                                    <img src={wordsOfWondersLogo} style={{width: "200px", height: "150px"}}/>
                                </div>
                                <div className="card-body"><div className="title">{constants.WORD_OF_WONDERS}</div></div>
                            </div>
                        </a>

                    </div>
                </div>
            </Router>
        </div>
    );
}
