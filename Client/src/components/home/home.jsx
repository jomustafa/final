import React, { useEffect, useState, useRef } from "react";
import Cookies from "universal-cookie";
import Modal from "react-modal";
import "./home.css";
import axios from "axios";
import * as Stomp from "stompjs";
import SockJS from "sockjs-client";
import Navbar from "../navigation/Navbar";
let activeUserID = 0;
let constants;

export default function Home() {

  const socket = useRef(new SockJS('/brainbright-websocket'));
  const stompClient = useRef(Stomp.over(socket.current));

  const cookies = new Cookies();

  if (cookies.get("language") === "en") {
    constants = require("../../assets/metatext/constants");
  } else {
    constants = require("../../assets/metatext/constantsgr");
  }

  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [renameModalIsOpen, setRenameModalIsOpen] = useState(false);
  const [adminModalIsOpen, setAdminModalIsOpen] = useState(false);
  const [name, setName] = useState();
  const [users, setUsers] = useState(() => {
    return cookies.get("allUsers") ? cookies.get("allUsers") : [];
  });
  const [user, setUser] = useState();
  const [adminUsername, setAdminUsername] = useState();
  const [adminPassword, setAdminPassword] = useState();


  useEffect(() => {
    stompClient.current.connect({}, function () {
      this.subscribe("/user/topic/userauth", function (data) {
        if (data.body == 1) {
          localStorage.setItem("logged", "admin");
          checkLogged();
        }
      });
    });
  }, []);

  function handleLogin() {
    if (adminUsername === "admin@brainbright.com") {
      stompClient.current.send("/app/userauth", {}, adminPassword);
    }
  }

  function checkLogged() {
    if (window.localStorage.getItem("logged") !== null) {
      window.location.href = "/statistics";
    }
  }

  function addUser(name) {
    let id = Date.now();
    let user = {
      name: name,
      id: id,
    };
    let array = users;
    users.push(user);
    setUsers(array);
    cookies.set("allUsers", array);

    axios.post("/newplayer", user);
  }

  function deleteUser() {
    let userID = activeUserID;

    let users = cookies.get("allUsers");

    if (users.length > 1) {
      let id = 0;
      users.forEach((el, index) => {
        if (el.id === user.id) {
          id = index;
        }
      });

      Array.from(document.getElementsByClassName("userSelection")).forEach(
        (el) => {
          el.style.backgroundColor = "white";
        }
      );
      users.splice(id, 1);
      cookies.set("allUsers", users);
      setUsers(users);
      if (users.length > 0) {
        changeUser(users[0]);
      }
    } else {
      setUsers([]);
      setUser();
      cookies.remove("currentUser");
      cookies.remove("username");
      cookies.remove("allUsers");
    }
    axios.post("/deleteplayer", user);

    // if (cookies.get("allUsers")) {
    //     console.log(users);
    //     changeUser(cookies.get("allUsers")[0]);
    // }
  }

  function renameUser(changedName) {
    let user = {
      id: activeUserID,
      name: cookies.get("username"),
    };

    let users = cookies.get("allUsers");
    console.log(users);
    let pos = 0;
    users.forEach((el, index) => {
      if (el.id === activeUserID) {
        pos = index;
      }
    });

    console.log(pos);
    user.name = changedName;
    users[pos] = user;
    cookies.set("allUsers", users);
    setUsers(users);
    console.log(cookies.get("allUsers"));
    axios.post("/renameplayer", user);
  }
  function changeUser(user) {
    cookies.set("currentUser", user.id);
    cookies.set("username", user.name);
    console.log(cookies.get("currentUser"));

    if (document.getElementById(activeUserID))
      document.getElementById(activeUserID).style.backgroundColor = "white";
    console.log(user.id);
    document.getElementById(user.id).style.backgroundColor = "#81d4fa";
    activeUserID = user.id;
    setUser(user);
  }

  return (
    <div>
      <Navbar />

      <div className="container">

        <div className="row">
          <div className="col-md-12">
            <div className="mt-5 text-center">
              <h4 className="intro">{constants.INTRO}</h4>
            </div>
            {/* <Router> */}
            <div className="mx-5">
              {/* user names */}
              <div className="user-names">
                {/* {console.log(cookies.get('allUsers'))} */}
                {users.map((user) => {
                  return (
                    <div
                      className="border border-light userSelection"
                      id={user.id}
                      onClick={() => changeUser(user)}
                    >
                      {user.name}
                    </div>
                  );
                })}
              </div>
              <div className="row justify-content-center mt-5 mb-2">
                <div className="modal">
                  <Modal isOpen={modalIsOpen}>
                    <div class="form-group">
                      <label for="player_name">{constants.NEW_PLAYER_BODY}</label>
                      <input
                        type="player_name"
                        className="form-control"
                        placeholder="Name"
                        onChange={(e) => setName(e.target.value)}
                      />
                    </div>

                    <button
                      className="btn-warning"
                      onClick={() => {
                        addUser(name);
                        setModalIsOpen(false);
                      }}
                    >
                      {constants.ADD_BTN}
                    </button>
                    <button
                      className="btn-warning"
                      onClick={() => setModalIsOpen(false)}
                    >
                      {constants.CANCEL_BTN}
                    </button>
                  </Modal>
                  <Modal isOpen={renameModalIsOpen}>
                    <div class="form-group">
                      <label for="player_name">{constants.NEW_PLAYER_BODY}</label>
                      <input
                        type="player_name"
                        className="form-control"
                        placeholder="Name"
                        onChange={(e) => setName(e.target.value)}
                      />
                    </div>
                    <button
                      className="btn-warning"
                      onClick={() => {
                        renameUser(name);
                        setRenameModalIsOpen(false);
                      }}
                    >
                      {constants.RENAME_PLAYER}
                    </button>
                    <button
                      className="btn-warning"
                      onClick={() => setRenameModalIsOpen(false)}
                    >
                      Cancel
                  </button>
                  </Modal>
                  <Modal isOpen={adminModalIsOpen} className="custom-modal-style">
                    <div className="mt-5 ml-5">
                      <form>
                        <div class="form-group">
                          <label for="exampleInputEmail1">
                            {constants.EMAIL}
                          </label>
                          <input
                            type="email"
                            class="form-control"
                            id="exampleInputEmail1"
                            aria-describedby="emailHelp"
                            placeholder="Enter email"
                            onChange={(e) => setAdminUsername(e.target.value)}
                          />
                        </div>
                        <div class="form-group">
                          <label for="exampleInputPassword1">
                            {constants.PASSWORD}
                          </label>
                          <input
                            type="password"
                            class="form-control"
                            id="exampleInputPassword1"
                            placeholder="Password"
                            onChange={(e) => setAdminPassword(e.target.value)}
                          />
                        </div>
                      </form>
                      <button
                        class="btn-warning"
                        onClick={() => {
                          handleLogin();
                          setAdminModalIsOpen(false);
                        }}
                      >
                        {constants.SUBMIT}
                      </button>
                      <button
                        className="btn-warning"
                        onClick={() => setAdminModalIsOpen(false)}
                      >
                        Cancel
                    </button>
                    </div>
                  </Modal>
                </div>

                {/* {user ? (
                <button
                  className="btn btn-secondary mr-2 col-md-6 mb-2"
                  onClick={() => {
                    window.location.replace("/categories");
                  }}
                >
                  {constants.START}
                </button>
              ) : (
                <button
                  className="btn btn-secondary mr-2 col-md-6 mb-2"
                  disabled="true"
                >
                  {" "}
                  {constants.START}
                </button>
              )} */}
                <button
                  className="btn btn-secondary mr-2 col-md-6 mb-2"
                  onClick={() => {
                    window.location.replace("/categories");
                  }}
                >
                  {constants.START}
                </button>

                <button
                  className="btn btn-secondary mr-2 col-md-6 mb-2"
                  onClick={() => setModalIsOpen(true)}
                >
                  {constants.NEW_PLAYER}
                </button>

                {user ? (
                  <button
                    className="btn btn-secondary col-md-6 mr-2 mb-2"
                    onClick={deleteUser}
                  >
                    {" "}
                    {constants.DELETE_PLAYER}{" "}
                  </button>
                ) : (
                  <button
                    className="btn btn-secondary col-md-6 mr-2 mb-2"
                    disabled="true"
                  >
                    {" "}
                    {constants.DELETE_PLAYER}{" "}
                  </button>
                )}

                {user ? (
                  <button
                    className="btn btn-secondary col-md-6 mr-2 mb-2"
                    onClick={setRenameModalIsOpen}
                  >
                    {" "}
                    {constants.RENAME_BTN}{" "}
                  </button>
                ) : (
                  <button
                    className="btn btn-secondary col-md-6 mr-2 mb-2"
                    disabled="true"
                  >
                    {" "}
                    {constants.RENAME_BTN}{" "}
                  </button>
                )}
                <button
                  className="btn btn-warning col-md-6 mr-2 mb-2"
                  onClick={setAdminModalIsOpen}
                >
                  {constants.ADMIN}
                </button>

              </div>
            </div>

            {/* </Router> */}
          </div>
          <div className="col-md-6"></div>
        </div>
      </div>
    </div>
  );
}
