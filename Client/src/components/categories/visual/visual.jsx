import React from "react";
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";
import Navbar from "../../navigation/Navbar";
import findPairsLogo from '../../../assets/images/findpairs.jpg';
import nextImageLogo from '../../../assets/images/findnext.jpg';
import coloredBoxesLogo from '../../../assets/images/coloredboxes.png';
import hiddenObjectLogo from '../../../assets/images/hiddenobjects.jpg';
import findDifferencesLogo from '../../../assets/images/finddifferences.jpg';
import findObjectLogo from '../../../assets/images/findobject.jpg';
import puzzleLogo from '../../../assets/images/puzzle.jpg';
import supermarketLogo from '../../../assets/images/supermarket.jpg';
import Cookies from "universal-cookie";

let constants;

export default function Visual() {

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
                {/* the links which have /visual on as the href have not been developed or were in process of developing
                when this menu was created. Change the link to the route of the corresponding game as soon as a file for that game is created */}

                <div className="container">
                    <div className="row justify-content-center mt-5">

                        <a href="/levels?game=coloredboxes">
                            <div className="card p-3 mr-2 mb-3">
                                <div className="card-header">
                                    <img src={coloredBoxesLogo} style={{ width: "200px", height: "150px" }} />
                                </div>
                                <div className="card-body"><div className="title">{constants.COLORED_BOXES}</div></div>
                            </div>
                        </a>

                        <a href="/levels?game=findpairs">
                            <div className="card p-3 mr-2 mb-3">
                                <div className="card-header">
                                    <img src={findPairsLogo} style={{ width: "200px", height: "150px" }} />
                                </div>
                                <div className="card-body"><div className="title">{constants.FIND_PAIRS}</div></div>
                            </div>
                        </a>

                        <a href="/levels?game=spotDifferences">
                            <div className="card p-3 mr-2 mb-3">
                                <div className="card-header">
                                    <img src={findDifferencesLogo} style={{ width: "200px", height: "150px" }} />
                                </div>
                                <div className="card-body"><div className="title">{constants.SPOT_DIFFERENCES}</div></div>
                            </div>
                        </a>

                        <a href="/levels?game=findtheobjects/category">
                            <div className="card p-3 mr-2 mb-3">
                                <div className="card-header">
                                    <img src={findObjectLogo} style={{ width: "200px", height: "150px" }} />
                                </div>
                                <div className="card-body"><div className="title">{constants.MEMORY_QUEST}</div></div>
                            </div>
                        </a>

                        {/* <a href="/visual">
                            <div className="card p-3 mr-2 mb-3">
                                <div className="card-header">
                                    <img src={puzzleLogo} style={{ width: "200px", height: "150px" }} />
                                </div>
                                <div className="card-body"><div className="title">{constants.PUZZLE}</div></div>
                            </div>
                        </a> */}

                        <a href="/levels?game=supermarket">
                            <div className="card p-3 mr-2 mb-3">
                                <div className="card-header">
                                    <img src={supermarketLogo} style={{ width: "200px", height: "150px" }} />
                                </div>
                                <div className="card-body"><div className="title">{constants.SUPERMARKET}</div></div>
                            </div>
                        </a>

                        <a href="/levels?game=findnextimage">
                            <div className="card p-3 mr-2 mb-3">
                                <div className="card-header">
                                    <img src={nextImageLogo} style={{ width: "200px", height: "150px" }} />
                                </div>
                                <div className="card-body"><div className="title">{constants.FIND_NEXT_IMAGE}</div></div>
                            </div>
                        </a>

                        <a href="/levels?game=hiddenobjects">
                            <div className="card p-3 mr-2 mb-3">
                                <div className="card-header">
                                    <img src={hiddenObjectLogo} style={{ width: "200px", height: "150px" }} />
                                </div>
                                <div className="card-body"><div className="title">{constants.HIDDEN_OBJECTS}</div></div>
                            </div>
                        </a>
                    </div>
                </div>
            </Router>
        </div>
    );
}
