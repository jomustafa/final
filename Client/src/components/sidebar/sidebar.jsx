import React, { Component, useState } from "react";
import "bootstrap/dist/css/bootstrap.css";
import style from "./sidebar.css";
import Cookies from "universal-cookie";
import Timer from "../sidebar/timer.jsx";
class Sidebar extends Component {
    constructor() {
        super();
        const cookies = new Cookies();
        let constants;
        if (cookies.get("language") === "en") {
            constants = require("../../assets/metatext/constants");
        } else {
            constants = require("../../assets/metatext/constantsgr");
        }
        this.state = {
            constants: constants,
            correctWords: new Array(0).fill(" "),
        };
    }

    updateFunction = () => {
        if (this.props.correctWords.length === 0) {
            this.state.correctWords = new Array(this.props.elements).fill(" ");
        } else {
            for (let i = 0; i < this.props.correctWords.length; i++) {
                this.state.correctWords[i] = this.props.correctWords[i];
            }
        }
    };

    alertThing = () =>{
      alert("hhh");
    }
    render() {
        return (
            <div className="text-center">
                {this.updateFunction()}
                <div className="found justify-content-center">
                    {this.state.constants.WORDS_FOUND_LABEL}
                </div>
                <div className="border border-success scoresbar mb-2">
                    {" "}
                    {this.props.correctWords.length +
                        " " +
                        this.state.constants.FROM +
                        " " +
                        this.state.correctWords.length}
                </div>

                {this.state.correctWords.map((correctWord) => (
                    <div
                        className="border border-dark resultrow rounded mb-1"
                        id={correctWord}
                    >
                        {correctWord}
                    </div>
                ))}

                {/* <Timer onTimerFinish={this.alertThing}/> */}
            </div>
        );
    }
}

export default Sidebar;
