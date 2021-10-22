import React from 'react';
import {
  BrowserRouter as Router,
  Switch,
  Route,
  BrowserRouter,
} from "react-router-dom";
import "bootstrap/dist/css/bootstrap.css";

import SplitWord from '../src/components/splitWords/splitWords';
import NamesAnimalsPlants from "../src/components/namesAnimalsPlants/namesAnimalsPlants"
import CorrectSpelling from '../src/components/correctSpelling/correctSpelling';
import ColoredBoxes from '../src/components/coloredBoxes/coloredBoxes';
import Hangman from '../src/components/hangman/hangman';
import Anagram from '../src/components/anagram/anagram';
import Verbal from './components/categories/verbal/verbal';
import WordSearch from '../src/components/wordSearch/wordSearch';
import Wordex from '../src/components/wordex/wordex';
import User from '../src/components/home/home';
import Visual from './components/categories/visual/visual';
import Photographic from './components/categories/photographic/photographic';
import FindPairs from '../src/components/findPairs/findPairs';
import SpotDifferences from '../src/components/spotDifferences/spotDifferences';
import FindNextImage from '../src/components/findNextImage/findNextImage';
import FindPatterns from '../src/components/findPatterns/findPatterns';
import HiddenObjects from '../src/components/hiddenObjects/hiddenObject';
import Categories from './components/categories/categories';
import Stats from './components/stats/stats'
import Reaction from './components/reaction/reaction'
import Supermarket from '../src/components/supermarket/supermarket';
import Languages from '../src/components/home/languages';
import Levels from '../src/components/menu/levels';

import FindTheObjects from './components/findTheObjects/findTheObjects'
import Category from './components/findTheObjects/category';
import CategoryHangman from './components/hangman/category-hangman';

const App = () => {
  return (
    <div className="App">
      <BrowserRouter>
        <div>
          <Switch>
          <Route exact={true} path="/" component={Languages}></Route>
            <Route path="/user" component={User}></Route>
            <Route path="/categories" component={Categories}></Route>
            <Route path="/spotDifferences" component={SpotDifferences}></Route>
            <Route path="/verbal" component={Verbal}></Route>
            <Route path="/visual" component={Visual}></Route>
            <Route path="/photographic" component={Photographic}></Route>
            <Route path="/splitword" component={SplitWord}></Route>
            <Route path="/animals" component={NamesAnimalsPlants}></Route>
            <Route path="/correctspelling" component={CorrectSpelling}></Route>
            <Route path="/coloredboxes" component={ColoredBoxes}></Route>
            <Route exact={true} path="/hangman/category" component={CategoryHangman}></Route>
            <Route exact={true} path="/hangman/:category" component={Hangman}></Route>
            <Route path="/anagram" component={Anagram}></Route>
            <Route path="/wordSearch" component={WordSearch}></Route>
            <Route path="/wordex" component={Wordex}></Route>
            <Route path="/findpairs" component={FindPairs}></Route>
            <Route path="/findnextimage" component={FindNextImage}></Route>
            <Route path="/findpatterns" component={FindPatterns}></Route>
            <Route path="/hiddenobjects" component={HiddenObjects}></Route>
            <Route exact={true} path="/findtheobjects/category" component={Category}></Route>
            <Route exact={true} path="/findtheobjects/:category" component={FindTheObjects}></Route>
            <Route path="/reaction" component={Reaction}></Route>
            <Route path="/statistics" component={Stats}></Route>
            <Route path="/supermarket" component={Supermarket}></Route>
            <Route path="/levels" component={Levels}></Route>
          </Switch>
        </div>
      </BrowserRouter>
    </div>
  );
}

export default App;
