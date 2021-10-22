import React from "react";
import "bootstrap/dist/css/bootstrap.css";
import Cookies from "universal-cookie";
import Navbar from "../navigation/Navbar";

let constants;

export default function ClientComponent() {
    const cookies = new Cookies();
    if (cookies.get("language") === "en") {
        constants = require("../../assets/metatext/constants");
    } else {
        constants = require("../../assets/metatext/constantsgr");
    }
    let link = window.location.toString();
    let level = Number(link.split("=")[1]);

    return (
        <div className="container-fluid">
            <Navbar/>
            <div className="row">
                <div className="col-md-3"></div>
                <div className="col-md-6 text-center mt-5">
                    <div className="mb-5">
                        <h3>{constants.PLEASE_SELECT_CATEGORY}</h3>
                        <a href={"/hangman/occupation?lvl=" + level}><button className="btn mr-2" value="Occupation">{constants.OCCUPATIONS_CATEGORY}</button></a>
                        <a href={"/hangman/animals?lvl=" + level}><button className="btn" value="Animals">{constants.ANIMALS_CATEGORY}</button></a>
                        <a href={"/hangman/plants?lvl=" + level}><button className="btn mr-2" value="Plants">{constants.PLANTS_CATEGORY}</button></a>
                        <a href={"/hangman/sayings?lvl=" + level}><button className="btn mr-2" value="Sayings">{constants.SAYINGS_CATEGORY}</button></a>

                    </div>
                </div>
                <div className="col-md-3"></div>
            </div>
        </div>
    );

}
