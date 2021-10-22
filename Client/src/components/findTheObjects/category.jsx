import React from "react";
import "bootstrap/dist/css/bootstrap.css";
import Cookies from "universal-cookie";
import Navbar from "../navigation/Navbar";

let constants;

export default function ClientComponent() {
    let link = window.location.toString();
    let level =  Number(link.split("=")[1]);
    const cookies = new Cookies();
    if (cookies.get("language") === "en") {
        constants = require("../../assets/metatext/constants");
    } else {
        constants = require("../../assets/metatext/constantsgr");
    }

    return (
        <div className="container-fluid">
            <Navbar/>
            <div className="row">
                <div className="col-md-3"></div>
                <div className="col-md-6 text-center mt-5">
                    <div className="mb-5">
                        <h3>{constants.PLEASE_SELECT_CATEGORY}</h3>
                        <a href={"/findtheobjects/kitchen?lvl=" +level}><button className="btn mr-2" value="Kitchen">{constants.KITCHEN}</button></a>
                        <a href={"/findtheobjects/livingroom?lvl="+level}><button className="btn" value="Living">{constants.LIVING_ROOM}</button></a>
                    </div>
                </div>
                <div className="col-md-3"></div>
            </div>
        </div>
    );

}
