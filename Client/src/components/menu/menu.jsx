import React, { Component, useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.css";
import "./menu.css";
import Player from "../player/player";
import Level from "../level/level";
import Cookies from "universal-cookie";
import Modal from "react-modal";

const cookies = new Cookies();
let constants;

if (cookies.get("language") === "en") {
  constants = require("../../assets/metatext/constants");
} else {
  constants = require("../../assets/metatext/constantsgr");
}

class Menu extends Component {
  constructor(props) {
    super(props);

    this.state = {
      constants: constants,
      openModal: false,
      openPauseModal: false,
    };
  }

  render() {
    return (
      <div className="">
        {/* PLAYER - PLAYERNAME */}
        <div className="d-flex mt-5 player-component justify-content-center">
          <p className="player mt-3">{this.state.constants.PLAYER_LABEL}</p>
          <div className="player-name p-3 ml-5 text-center">
            {this.props.playerName}
          </div>
        </div>

        {/* THE LEVEL */}
        <div className="d-flex justify-content-center level-component">
          <div className="number">{this.props.level}</div>
        </div>
        <div className="modal">
          <Modal isOpen={this.state.openModal}>
            <div class="form-group">
              <span>{this.props.instructions}</span>
            </div>
            <button
              className="btn btn-warning"
              onClick={() => this.setState({ openModal: false })}
            >
              {this.state.constants.EXIT_BTN}
            </button>
          </Modal>
        </div>
        <div style={{ width: "2000px" }}>
          <Modal isOpen={this.state.openPauseModal}>
            <div className="form-group">
              <span>{constants.PAUSE_GAME}</span>
            </div>
            <button
              className="btn btn-warning"
              onClick={() => {
                this.props.pause();
                this.setState({ openPauseModal: false });
              }}
            >
              {this.state.constants.CONTINUE_BTN}
            </button>
          </Modal>
        </div>

        {/* INSTRUCTIONS AND OTHER STUFF */}
        <button
          className="btn btn-outline-dark mb-2 w-100"
          onClick={() => this.setState({ openModal: true })}
        >
          {this.state.constants.INSTRUCTIONS_BTN}
        </button>
        <div className="row">
          <div className="d-flex col-md-6">
            <button
              className="btn btn-outline-dark mb-2  w-100"
              onClick={() => {
                this.props.pause();
                this.setState({ openPauseModal: true });
              }}
            >
              {this.state.constants.PAUSE_BTN}
            </button>
          </div>
          <div className="col-md-6">
            <button
              className="btn btn-outline-dark mb-2 w-100"
              onClick={() => {
                window.location.reload();
              }}
            >
              {this.state.constants.RESTART_BTN}
            </button>
          </div>
        </div>
      </div>
    );
  }
}

export default Menu;
