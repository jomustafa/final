import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.css";
import style from "./player.css";
import Cookies from "universal-cookie";

class Player extends Component {

    render() {
        let constants;

        const cookies = new Cookies();

        if (cookies.get("language") === "en") {
            constants = require("../../assets/metatext/constants");
        } else {
            constants = require("../../assets/metatext/constantsgr");
        }
        return (
            // <div className="d-flex mt-5 justify-content-center level-component">
            <div className="d-flex mt-5 player-component justify-content-center">
                <p className="player mt-3">{constants.USER}</p>
                <div className="player-name p-3 ml-5 text-center">{this.props.name}</div>
            </div>
        )
    }
}

export default Player;

