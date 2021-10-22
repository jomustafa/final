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
    path: '/categories',
    // icon: <AiIcons.AiFillHome />,
    cName: 'nav-text'
  },
  {
    title: constants.VERBAL,
    path: '/verbal',
    // icon: <IoIcons.IoIosPaper />,
    cName: 'nav-text'
  },
  {
    title: constants.VISUAL,
    path: '/visual',
    // icon: <FaIcons.FaCartPlus />,
    cName: 'nav-text'
  },
  {
    title: constants.PHOTOGRAPHIC,
    path: '/photographic',
    // icon: <IoIcons.IoMdPeople />,
    cName: 'nav-text'
  },
  {
    title: constants.USER,
    path: '/user',
    // icon: <IoIcons.IoMdPeople />,
    cName: 'nav-text'
  },
  {
    title: constants.STATS,
    path: '/statistics',
    // icon: <IoIcons.IoMdPeople />,
    cName: 'nav-text'
  },
  {
    title: constants.LANGUAGE,
    path: '/',
    // icon: <IoIcons.IoMdPeople />,
    cName: 'nav-text'
  }
];