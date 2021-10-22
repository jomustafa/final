import React from "react";
import { BrowserRouter as Router, Switch, Route, Link } from "react-router-dom";

import VisualLogo from "../../assets/images/visual.jpg";
import VerbalLogo from "../../assets/images/verbal.jpg";
import PhotographicLogo from "../../assets/images/photographic.jpg";
import Navbar from "../navigation/Navbar";
import Cookies from "universal-cookie";
import "./categories.css";

let constants;

export default function Categories() {
  const cookies = new Cookies();

  if (cookies.get("language") === "en") {
    constants = require("../../assets/metatext/constants");
  } else {
    constants = require("../../assets/metatext/constantsgr");
  }

  return (
    <div>
      <Navbar />
      <Router>
        <div className="container">
          <div className="row justify-content-center mt-5">
            <a href="/verbal">
              <div className="card p-3 mr-2">
                <div className="card-header">
                  <img
                    src={VerbalLogo}
                    style={{ width: "200px", height: "150px" }}
                  />
                </div>
                <div className="card-body">
                  <div className="title">{constants.VERBAL}</div>
                </div>
              </div>
            </a>
            <a href="/visual">
              <div className="card p-3 mr-2">
                <div className="card-header">
                  <img
                    src={VisualLogo}
                    style={{ width: "200px", height: "150px" }}
                  />
                </div>
                <div className="card-body">
                  <div className="title">{constants.VISUAL}</div>
                </div>
              </div>
            </a>
            <a href="/photographic">
              <div className="card p-3 mr-2">
                <div className="card-header">
                  <img
                    src={PhotographicLogo}
                    style={{ width: "200px", height: "150px"}}
                  />
                </div>
                <div className="card-body">
                  <div className="title" style={{ wordWrap: "break-word"}}>{constants.PHOTOGRAPHIC}</div>
                </div>
              </div>
            </a>
          </div>
        </div>
      </Router>
    </div>
  );
}
