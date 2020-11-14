import React, { useEffect, useState } from "react";
import style from "./home.css";
import { Link, Router, Route } from "react-router-dom";


export default function HomeComponent() {


    return (
        <div className="d-flex justify-content-center">
         
                <Link to="/splitword"><div className="card mr-3">First Category</div></Link>
                <Link to="/splitword"><div className="card mr-3">Second</div></Link>
                <Link to="/splitword"><div className="card mr-3">Third</div></Link>
            

        </div>
    )
}

