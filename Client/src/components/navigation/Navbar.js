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
  const greekLanguage=()=>{
   //const v= document.getElementById("verbal")
   //v.style.display="block"
   cookies.set("language","gr")

  }
  const englishLanguage=()=>{
    //const v= document.getElementById("verbal")
    //v.style.display="block"
    cookies.set("language","en")
 
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
                  if(item.title===constants.VERBALBAR){
                    return (
                      <div>
                        <div id="verbal_div">
                            <li key={index} className={item.cName}>
                              <Link to={item.path}>
                                {item.icon}
                                <span>{item.title}</span>
                              </Link>
                            </li>
                          </div>
                          <div id="verbal">
                            <div class="subcat_div"><a class="subcat" href="/levels?game=splitword">{constants.SPLIT_WORDS}</a></div>
                            <div class="subcat_div"><a class="subcat" href="/levels?game=animals">{constants.NAMES_ANIMALS_PLANTS}</a></div>
                            <div class="subcat_div"><a class="subcat" href="/levels?game=anagram">{constants.ANAGRAM}</a></div>
                            <div class="subcat_div"><a class="subcat" href="/levels?game=wordex">{constants.WORDEX}</a></div>
                            <div class="subcat_div"><a class="subcat" href="/levels?game=wordsearch">{constants.WORD_SEARCH}</a></div>
                            <div class="subcat_div"><a class="subcat" href="/levels?game=hangman/category">{constants.HANGMAN}</a></div>
                            <div class="subcat_div"><a class="subcat" href="/levels?game=wordofwonders">{constants.WORD_OF_WONDERS}</a></div>



                          </div>
                      </div>
                    );
                  }
                  else if(item.title===constants.VISUALBAR){
                    return (
                      <div>
                        <div id="visual_div">
                        <li key={index} className={item.cName}>
                          <Link to={item.path}>
                            {item.icon}
                            <span>{item.title}</span>
                          </Link>
                        </li>
                        </div>
                        <div id="visual">
                          <div class="subcat_div"><a class="subcat" href="/levels?game=coloredboxes">{constants.COLORED_BOXES}</a></div>
                          <div class="subcat_div"><a class="subcat" href="/levels?game=findpairs">{constants.FIND_PAIRS}</a></div>
                          <div class="subcat_div"><a class="subcat" href="/levels?game=spotDifferences">{constants.SPOT_DIFFERENCES}</a></div>
                          <div class="subcat_div"><a class="subcat" href="/levels?game=findtheobjects/category">{constants.FIND_NEXT}</a></div>
                          <div class="subcat_div"><a class="subcat" href="/levels?game=supermarket">{constants.SUPERMARKET}</a></div>
                          <div class="subcat_div"><a class="subcat" href="/levels?game=findnextimages">{constants.FIND_NEXT_IMAGE}</a></div>
                          <div class="subcat_div"><a class="subcat" href="/levels?game=hiddenobjects">{constants.HIDDEN_OBJECTS}</a></div>

                          
                        </div>
                      </div>
                    );
                  }
                  else if(item.title===constants.ATTENTIONBAR){
                    return (
                      <div>
                        <div id="attention_div">
                        <li key={index} className={item.cName}>
                          <Link to={item.path}>
                            {item.icon}
                            <span>{item.title}</span>
                          </Link>
                        </li>
                        </div>
                        <div id="attention">
                          <div class="subcat_div"><a class="subcat" href="/levels?game=findpatterns">{constants.FIND_PATTERNS}</a></div>
                          <div class="subcat_div"><a class="subcat" href="/levels?game=splitword">{constants.QUICK_REACTIONS}</a></div>

                          
                        </div>
                      </div>
                    );
                  }
                  else if(item.title===constants.LANGUAGE){
                    return (
                      <div>
                        <div id="lang_div">
                          <li key={index} className={item.cName}>
                            <Link to={item.path}>
                              {item.icon}
                              <span>{item.title}</span>
                            </Link>
                          </li>
                        </div>
                        <div id="lang">
                          <div class="subcat_div" onClick={englishLanguage}><a class="subcat" href="/categories">{constants.ENGLISH}</a></div>
                          <div class="subcat_div" onClick={greekLanguage}><a class="subcat" href="/categories">{constants.GREEK}</a></div>
                        </div>
                      </div>
                    );
                  }
                  else{
                    return (
                      <li key={index} className={item.cName}>
                        <Link to={item.path}>
                          {item.icon}
                          <span>{item.title}</span>
                        </Link>
                      </li>
                    );

                  }
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
