import React, { Component } from "react";
import "bootstrap/dist/css/bootstrap.css";
import style from "./level.css";

class Level extends Component {

    render() {
        return (
            <div className="d-flex mt-5 justify-content-center level-component">
                <div className="number">{this.props.level}</div>
            </div>
        )
    }
}

export default Level;

