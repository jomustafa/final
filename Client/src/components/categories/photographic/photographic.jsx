import React, { useEffect, useState } from "react";
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";
import Cookies from "universal-cookie";
import Navbar from "../../navigation/Navbar";
import findPatternsLogo from '../../../assets/images/findpatterns.jpg';
import findNextBoxLogo from '../../../assets/images/findnextbox.jpg';
import actQuicklyLogo from '../../../assets/images/actquickly.jpg';

let constants;

export default function Photographic() {
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
                        <a href="/levels?game=findpatterns">
                            <div className="card p-3 mr-3 mt-5">
                                <div className="card-header">
                                    <img src={findPatternsLogo} style={{ width: "200px", height: "150px" }} />
                                </div>
                                <div className="card-body"><div className="title">{constants.FIND_PATTERNS}</div></div>
                            </div>
                        </a>

                        {/* <a href="/photographic">
                            <div className="card p-3 mr-3 mt-5">
                                <div className="card-header">
                                    <img src={findNextBoxLogo} style={{ width: "200px", height: "150px" }} />
                                </div>
                                <div className="card-body"><div className="title">{constants.FIND_NEXT_BOX}</div></div>
                            </div>
                        </a> */}

                        <a href="/levels?game=reaction">
                            <div className="card p-3 mr-3 mt-5">
                                <div className="card-header">
                                    <img src={actQuicklyLogo} style={{ width: "200px", height: "150px" }} />
                                </div>
                                <div className="card-body"><div className="title">{constants.QUICK_REACTIONS}</div></div>
                            </div>
                        </a>

                    </div>
                </div>
            </Router>
        </div>
    );
}
