import React from 'react';
import * as FaIcons from 'react-icons/fa';
import * as AiIcons from 'react-icons/ai';
import * as IoIcons from 'react-icons/io';
import Cookies from "universal-cookie";
import { common } from '@material-ui/core/colors';

let constants;

const cookies = new Cookies();

if(cookies.get("language") === "en"){
  constants = require("../../assets/metatext/constants");
} else {
  constants = require("../../assets/metatext/constantsgr");
}

export const SidebarData = [

  {
    title: constants.HOME,
    path: '/user',
    cName: 'nav-text',
  },
  {
    
    title: constants.CATEGORIESBAR,
    path: '/categories',
    cName: 'nav-text'
    
  },
  {
    title: constants.VERBALBAR,
    path: '/verbal',
    cName: 'nav-text'
  },
  {
    title: constants.VISUALBAR,
    path: '/visual',
    cName: 'nav-text'
  },
  {
    title: constants.ATTENTIONBAR,
    path: '/photographic',
    cName: 'nav-text'
  },
  {
    title: constants.LANGUAGE,
    path: '/',
    cName: 'nav-text'
  }
]; 
