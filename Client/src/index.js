import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import App from "./App";
import * as serviceWorker from "./serviceWorker";
import "bootstrap/dist/css/bootstrap.css";

App.use(express.static(__dirname + "/docs"));

ReactDOM.render(
  <App/>,
  document.querySelector('#root'));
