import React, { Component } from "react";
import { render } from "react-dom";
import Cookies from "universal-cookie";

const cookies = new Cookies();
let constants;
if (cookies.get("language") === "en") {
  constants = require("../../assets/metatext/constants");
} else {
  constants = require("../../assets/metatext/constantsgr");
}

class Timer extends Component {
    constructor(props) {
        super(props);
        this.state = {
            goal: this.props.timer,
            count: this.props.timer,
        };
    }

    componentDidUpdate(prevProps) {
        if (prevProps.pause != this.props.pause){
            if(this.props.pause===true){
               
                clearInterval(this.myInterval);
            }else{
               
                this.myInterval = setInterval(() => {
                    this.setState({
                        count: this.state.count - 1,
                    });
                    if (this.state.count === 0) {
                        clearInterval(this.myInterval)
                        this.props.onTimerFinish();
                    }
                }, 1000);
            }
            
        }
    }

    componentDidMount() {
        this.myInterval = setInterval(() => {
            this.setState({
                count: this.state.count - 1,
            });
            if (this.state.count === 0) {
                clearInterval(this.myInterval)
                this.props.onTimerFinish();
            }
        }, 1000);
    }

    componentWillUnmount() {
        clearInterval(this.myInterval);
    }

    render() {
        const { count } = this.state;
        return (
            <div>
                <svg width="300" height="50">
                    <rect width={300} height={100} style={{ fill: "#fff" }} />
                </svg>
                <svg width="300" height="50" style={{ border: "2px solid black" }}>
                    <rect width={(this.state.goal - count) / this.state.goal * 100 + "%"} height={100 + "%"} style={{ fill: "#00f", border: "solid 2px black" }} />
                </svg>
                <div>{constants.COUNT} <span id="remainingTime">{count}</span></div>
            </div>
        );
    }
}

export default Timer;
