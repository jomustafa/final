import React, { Component } from "react";
import { BrowserRouter as Router, Link, useLocation } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.css";
import Cookies from "universal-cookie";
import Navbar from "../navigation/Navbar";
import Player from "../player/player";
import "./menu.css";


const cookies = new Cookies();
let constants;
if (cookies.get("language") === "en") {
  constants = require("../../assets/metatext/constants");
} else {
  constants = require("../../assets/metatext/constantsgr");
}

class Levels extends Component {
  constructor() {
    super();

    let link = window.location.toString();
    let game = link.split("=")[1];
    let instructions = game.toUpperCase() + "_INSTRUCTIONS";
    this.state = {
      constants: constants,
      game: game,
      username: cookies.get("username"),
      instructions: constants[instructions],
    };
    console.log(this.state.game);
  }

  render() {

    return (
      <div className="container-fluid">
        <Navbar />
        <div className="row align-items-center">
          <div className="col col-md-3">
            <Player name={this.state.username}></Player>
          </div>
          <div className="col col-md-6 text-center">
            <div className="mb-5">
              <h2 className="select">{constants.LEVEL_OF_DIFFICULTY}</h2>
              <button
                className="btn btn-warning mr-2"
                id="levels"
                onClick={() => {
                  window.location.replace("/" + this.state.game + "?lvl=1");
                }}
              >
                1
              </button>
              <button
                className="btn btn-warning mr-2"
                id="levels"
                onClick={() => {
                  window.location.replace("/" + this.state.game + "?lvl=2");
                }}
              >
                2
              </button>
              <button
                className="btn btn-warning mr-2"
                id="levels"
                onClick={() => {
                  window.location.replace("/" + this.state.game + "?lvl=3");
                }}
              >
                3
              </button>
              <button
                className="btn btn-warning mr-2"
                id="levels"
                onClick={() => {
                  window.location.replace("/" + this.state.game + "?lvl=4");
                }}
              >
                4
              </button>
              <button
                className="btn btn-warning mr-2"
                id="levels"
                onClick={() => {
                  window.location.replace("/" + this.state.game + "?lvl=5");
                }}
              >
                5
              </button>
            </div>
          </div>
        </div>
        <div className="row align-items-center">
          <div className="col col-md-3 text-center">
            <div className="selected-game">
              <h4>{constants.SELECTED_GAME_LB} </h4>
              <img
                src={"images/" + this.state.game + ".jpg"}
                style={{ width: "200px", height: "150px" }}
              ></img>
            </div>
          </div>
        </div>
      </div>
    );
  }
}
export default Levels;
