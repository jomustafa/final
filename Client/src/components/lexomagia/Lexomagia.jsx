import React, { useEffect, useState } from "react";
import Navbar from "../navigation/Navbar";
import Menu from "../menu/menu";
import Sidebar from "../sidebar/sidebar";
import Cookies from "universal-cookie";
import clg from "crossword-layout-generator"
import anagramLogo from '../../assets/images/redcircle.png';
import style from './style.css'
import axios from "axios";
import Timer from "../sidebar/timer";
import swal from "sweetalert";

let link = window.location.toString();
let level = Number(link.split("=")[1]);

const Lexomagia = () => {
    const [levelRender, setLevelRender] = useState(level)
    const [correctWordsRender, setCorrectWordsRender] = useState([])
    const cookies = new Cookies();
    const [letterStack, setLetterStack] = useState([])
    const [mouseDown, setMouseDown] = useState(false)
    const [pause, setPause] = useState(false);
    const [words, setWords] = useState([]);
    const [letters, setLetters] = useState([])
    const [table, setTable] = useState({ table: [] });

    //Change this according to the timer, each element corresponds to a level, so 200 seconds for the first 3 levels, 190 for 4, 180 for 5
    const timer = [200, 200, 200, 190, 180]
    const diameter = 400
    const center = diameter / 2;
    const letterRadius = diameter*0.35
    let constants;
    if (cookies.get("language") === "en") {
        constants = require("../../assets/metatext/constants");
    } else {
        constants = require("../../assets/metatext/constantsgr");
    }

    const handleOnMouseOver = (event, letterButton, index) => {
        if (mouseDown) {
            setLetterStack((curr) => {
                if (curr.some(({ id }) => letterButton.id === id)) {
                    return curr
                } else {
                    setLetters((curr) => {
                        let arr = [...curr]
                        arr[index].isSelected = true
                        return arr
                    })
                    return [...curr, letterButton]
                }
            })

        }
    }

    function timerOver() {
        swal(constants.UNFORTUNATELY_YOU_LOST, "", "error", {
            buttons: {
                retry: {
                    text: constants.TRY_AGAIN,
                    value: "retry",
                },
                goback: {
                    text: constants.BACK_TO_MENU_BTN,
                    value: "back",
                },
            }
        }
        ).then((value) => {
            console.log(value);
            switch (value) {
                case "back":
                    window.location.replace("/photographic");
                    break;

                case "retry":
                    window.location.reload();
                    break;

                default:
                    window.location.replace("/photographic");
                    break;
            }
        });
    }



    //Changes the state of the mouse, which is used to determine game logic
    useEffect(() => {
        const handleOnMouseDown = () => { setMouseDown(true); }
        const handleOnMouseUp = () => {
            setMouseDown(false);
            setLetterStack((curr) => {
                let word = curr.map(el => el.letter)
                console.log(curr)
                word = word.join("");
                words.map(({ startx, starty, answer, orientation }) => {
                    if (answer == word) {

                        setTable((currTable) => {
                            //adjust for them using indices that do not start from 0 but from 1
                            startx--;
                            starty--;
                            let newTable = { ...currTable }
                            if (orientation === "across") {
                                for (let i = startx; i < startx + answer.length; i++) {
                                    newTable.table[starty][i].isVisible = true
                                }
                            } else {
                                for (let i = starty; i < starty + answer.length; i++) {
                                    newTable.table[i][startx].isVisible = true
                                }
                            }
                            return newTable
                        })
                        setCorrectWordsRender((curr) => {
                            let newArr = [...curr]
                            if (!curr.includes(word)) newArr.push(answer)
                            if (newArr.length == words.length) {
                                swal(constants.FINISHED_LEVEL_LABEL, "", "success", {
                                    buttons: {
                                        retry: {
                                            text: constants.CONTINUE_BTN,
                                            value: "next",
                                        },
                                        goback: {
                                            text: constants.BACK_TO_MENU_BTN,
                                            value: "back",
                                        },
                                    }
                                }
                                ).then((value) => {
                                    console.log(value);
                                    switch (value) {
                                        case "back":
                                            window.location.replace("/verbal");
                                            break;

                                        case "next":
                                            window.location.replace("/wordofwonders?lvl=" + (levelRender + 1));
                                            break;

                                        default:
                                            window.location.replace("/verbal");
                                            break;
                                    }
                                });
                            }
                            return newArr
                        })
                    }
                })
                // if (words.map(el => el.answer).includes(word)) {
                //     alert('good job');
                // } else {
                //     console.log(words)
                //     console.log(word)
                //     alert('bad job')
                // }
                return []
            });
            setLetters((curr) => {
                let arr = [...curr]
                arr.forEach(el => el.isSelected = false)
                return arr
            })
        }
        window.addEventListener('mousedown', handleOnMouseDown)
        window.addEventListener('mouseup', handleOnMouseUp)
        return () => {
            window.removeEventListener("mousedown", handleOnMouseDown);
            window.removeEventListener("mouseup", handleOnMouseUp);
        }
    }, [words, correctWordsRender])

    //Adds a letterButton to the stack when clicking on a letterButton
    const handleOnMouseDown = (event, letterButton, index) => {
        setLetterStack((curr) => [...curr, letterButton])
        setLetters((curr) => {
            let arr = [...curr]
            arr[index].isSelected = true
            return arr
        })
    }

    useEffect(() => {
        axios.get('/api/test', {
            params: {
                level: levelRender,
                language: cookies.get('language')
            }
        }).then((resp) => {
            let arr = resp.data.map(el => { return { clue: "", answer: el.word } })
            console.log(arr)
            let layout = clg.generateLayout(arr)
            layout.table = layout.table.map((row) => {
                return row.map((character) => {
                    return { character, isVisible: false }
                })
            })
            let words = layout.result.filter(el => el.orientation !== "none")
            setWords(words);
            setLetters(getLetters(words));

            console.log(layout)
            setTable(layout)
        })
    }, [])


    //Given a list of words, it generates the minimum amount of letters required in order to play the game
    function getLetters(wordArray) {
        let letters = {};
        wordArray.map(({ answer }) => {
            let wordMap = {};
            [...answer].map((letter) => {
                if (wordMap[letter]) {
                    wordMap[letter] = wordMap[letter] + 1
                } else {
                    wordMap[letter] = 1;
                }
            })
            Array.from((Object.entries(wordMap))).map((entry) => {
                let [key, value] = entry
                if (!letters[key]) {
                    letters[key] = value;
                } else {
                    letters[key] = Math.max(letters[key], value)
                }
            })
        })

        let arr = []
        Array.from(Object.entries(letters)).forEach(el => {
            for (let i = 0; i < el[1]; i++) {
                arr.push(el[0])
            }
        })
        arr = arr.map((letter, index) => {
            return {
                letter,
                id: index,
                isSelected: false
            }
        })
        return arr;
    }

    return (
        <div className="container-fluid ">
            <Navbar />
            <div className="row">
                <div className="col-md-3">
                    <Menu
                        level={levelRender} playerName={cookies.get("username")}
                        instructions={constants.WORD_OF_WONDERS_INSTRUCTIONS}
                        pause={() => { setPause(value => !value) }}
                    ></Menu>
                </div>
                <div className="col-md-6 text-center all_words">
                    <div className="row">
                        {
                            table.table.map(row => {
                                return (
                                    <>
                                        {
                                            row.map((cell, index, row) => (
                                                <div style={{ minWidth: 100 / row.length + "%", padding: "5px", }} className={"col-xs-1"} key={index}>
                                                    {
                                                        <div style={{ padding: "5px", backgroundColor: cell.character == "-" ? "white" : "#A9E4E7", minHeight: "35px" }}>
                                                            <span style={{ color: cell.character == "-" ? "black" : "black" }}>
                                                                {cell.isVisible ? cell.character : ""}
                                                            </span>
                                                        </div>

                                                    }
                                                </div>))
                                        }
                                    </>
                                )

                            })
                        }
                    </div>
                    <div className="mt-3" style={{ minHeight: "30px" }}>
                        {letterStack.length > 0 ? letterStack.map((el, index) => (
                            <button key={index} style={{
                                backgroundColor: '#57e5ca',
                                color: '#1e1d62',
                                // width: '100%',
                                fontWeight: 600,
                                fontStyle: 'normal',
                                border: 0,
                                borderRadius: '4px',
                                textDecoration: 'none',
                                minHeight: "50px",
                                minWidth: "50px"
                            }}>
                                {el.letter}
                            </button>
                        )) : <div style={{
                            minHeight: "50px",
                            minWidth: "50px"
                        }} />}</div>
                    <div style={{
                        width: `${diameter/2}px`, height: `${diameter/2}px`, backgroundSize: `${diameter}px ${diameter}px`,
                        marginLeft: 'auto', marginRight: 'auto', marginTop: '30px', fill: 'white'
                    }}>


                        <div >

                            <svg viewBox={`0 0 ${diameter} ${diameter}`} xmlns="http://www.w3.org/2000/svg">
                                <circle
                                    cy={center}
                                    cx={center}
                                    r={diameter/2}
                                    fill={'#0EB2BB'}
                                />
                                {
                                    letters.map((el, index, arr) => (
                                        <g onMouseOver={(e) => { handleOnMouseOver(e, el, index); }} onMouseDown={e => { handleOnMouseDown(e, el, index) }}>
                                            <circle
                                                cy={center + (Math.sin(2 * Math.PI / arr.length * index) * letterRadius)}
                                                cx={center + (Math.cos(2 * Math.PI / arr.length * index) * letterRadius)}
                                                r={diameter*0.1}
                                                fill={el.isSelected ? "#EDE3FF" : "white"}
                                            />
                                            <text x={center + (Math.cos(2 * Math.PI / arr.length * index) * letterRadius)}
                                                y={center + (Math.sin(2 * Math.PI / arr.length * index) * letterRadius)}
                                                fill="black"
                                                text-anchor="middle"
                                                fontSize="25px"
                                                alignment-baseline="middle"
                                                pointerEvents="none"

                                            > {el.letter}</text>

                                        </g>
                                    ))
                                }
                            </svg>



                        </div>
                    </div>
                </div>
                <div className="col-md-3">
                    <Sidebar
                        level={levelRender}
                        correctWords={correctWordsRender}
                        elements={words.length}
                    ></Sidebar>
                    <Timer timer={timer[levelRender - 1]} onTimerFinish={timerOver} pause={pause} />

                </div>
            </div>
        </div >

    )
}

export default Lexomagia;