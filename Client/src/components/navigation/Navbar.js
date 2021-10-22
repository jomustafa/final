import React, { useState } from "react";
import * as FaIcons from "react-icons/fa";
// import { FaIcon, FaStack } from 'react-fa-icon';
import * as AiIcons from "react-icons/ai";
import { Link } from "react-router-dom";
import { SidebarData } from "./SidebarData";
import "./Navbar.css";
import { IconContext } from "react-icons";
import logo from "../../assets/images/logo.jpg";
import Cookies from "universal-cookie";

let constants; 
const cookies = new Cookies();

function Navbar() {
  const [sidebar, setSidebar] = useState(false);

  const showSidebar = () => setSidebar(!sidebar);

  if (cookies.get("language") === "en") {
    constants = require("../../assets/metatext/constants");
  } else {
    constants = require("../../assets/metatext/constantsgr");
  }

  //   const nav = document.getElementsByClassName('nav-menu active');

  //   if (nav.length == 0){
  //     let container = document.getElementsByClassName('container');
  //     container.style.position = "relative";
  //     container.style.right = "10px";
  //   }

  const renderLogOut= () => {
    if(window.localStorage.getItem('logged') !== null) {
      return <li className="ml-2" style={{marginTop: "300%", color: "white"}} onClick={() => logOut()}>Log out</li>
    }
  }

  const logOut = () => {
    window.localStorage.removeItem('logged');
    window.location.href = "/"
  }

  return (
    <>
      <IconContext.Provider value={{ color: "#fff" }}>
        <div className="navbar" height="2px">

          <FaIcons.FaBars onClick={showSidebar} />
          
          {/* <FaIcon icon="camera-retro" size="lg" /> */}

          <div className="go_back" style={{color: "white", fontSize: "18px"}} onClick = { () => window.history.back() }>
            <FaIcons.FaArrowLeft />
            {" "+constants.BACK_BTN}
          </div>

          <div className="text">Brain Bright</div>
          <div className="city">
            <a
              href="https://york.citycollege.eu/"
              target="_blank"
              style={{ color: "white" }}
            >
              <img src={logo} className="logo" alt="CITY College" />
            </a>
          </div>
        </div>
        <nav className={sidebar ? "nav-menu active" : "nav-menu"}>
          <ul className="nav-menu-items" onClick={showSidebar}>
            <li className="navbar-toggle">
              <AiIcons.AiOutlineClose />
            </li>
            {SidebarData.map((item, index) => {
              if (window.localStorage.getItem('logged') === null) {
                if(item.title !== 'Statistics') {
                  return (
                    <li key={index} className={item.cName}>
                      <Link to={item.path}>
                        {item.icon}
                        <span>{item.title}</span>
                      </Link>
                    </li>
                  );
                }
              } else {
                return (
                  <li key={index} className={item.cName}>
                    <Link to={item.path}>
                      {item.icon}
                      <span>{item.title}</span>
                    </Link>
                  </li>
                );
              }

            })}
            {renderLogOut()}
          </ul>
        </nav>
      </IconContext.Provider>
    </>
  );
}

export default Navbar;
