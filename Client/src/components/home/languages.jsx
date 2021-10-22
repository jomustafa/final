import React, { useEffect, useState } from "react";
import "bootstrap/dist/css/bootstrap.css";
import './home.css';
import Cookies from 'universal-cookie';
import axios from 'axios';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";


export default function Languages() {

    const cookies = new Cookies();

    const [language, setLanguage] = useState(() => {
        return cookies.get('language') ? cookies.get('language') : null;
    });

    function chooseLng(e) {
        let lng = e.target.value;
        setLanguage(lng);
        cookies.set('language', JSON.stringify(lng));
        console.log(cookies.get('language'));

    }

    return (

        <div class="container">
            <div class="d-flex justify-content-center mt-5 mb-2">
                <span class="brainBright">Brain Bright</span>
            </div>
            <div class="d-flex justify-content-center">
                <span class="cityC">Created by CITY College</span>
            </div>
            <br />
            <div class="d-flex justify-content-center">

            </div>
            <div class="row justify-content-center mt-5">
                <a href="/user">
                    <button class="btn btn-secondary btn-lg mr-2 mt-5" value="en" style={{ height: "100px", width: "300px" }} onClick={chooseLng}>
                        ΕN
                    </button>
                </a>
                <a href="/user">
                    <button class="btn btn-secondary btn-lg mt-5" value="gr" style={{ height: "100px", width: "300px" }} onClick={chooseLng}>
                        GR
                    </button>
                </a>
            </div>
        </div>

    );
}
