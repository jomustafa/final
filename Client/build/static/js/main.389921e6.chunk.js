(this["webpackJsonpcounter-app"]=this["webpackJsonpcounter-app"]||[]).push([[0],{29:function(e,t,a){e.exports=a.p+"static/media/logo.ee7cd8ed.svg"},30:function(e,t,a){e.exports=a(67)},35:function(e,t,a){},36:function(e,t,a){},64:function(e,t){},67:function(e,t,a){"use strict";a.r(t);var n=a(0),c=a.n(n),r=a(28),l=a.n(r);a(35),a(29),a(36);Boolean("localhost"===window.location.hostname||"[::1]"===window.location.hostname||window.location.hostname.match(/^127(?:\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}$/));a(37);var o=a(3),s=a(14),m=a.n(s),i="https://brainbright.herokuapp.com:8080";function u(){var e=Object(n.useState)([]),t=Object(o.a)(e,2),a=t[0],r=t[1],l=Object(n.useState)(),s=Object(o.a)(l,2),u=(s[0],s[1]),d=Object(n.useState)(""),v=Object(o.a)(d,2),p=(v[0],v[1]),E=Object(n.useState)([]),b=Object(o.a)(E,2),N=b[0],f=b[1],w=Object(n.useState)(""),h=Object(o.a)(w,2),g=h[0],j=h[1];function O(e){if(N.length>=2)f([]),f([e.target.innerHTML]);else{var t=N;t.push(e.target.innerHTML),f(t)}var a=m()(i);console.log(N,N.length),2===N.length&&(a.emit("result",N.join("")),p(""),f([]),a.on("result",(function(e){u(e),console.log(e),j(e?"You are correct":"You are wrong")})))}return Object(n.useEffect)((function(){m()(i).on("all_split_words",(function(e){r(e.splitWords)}))}),[]),c.a.createElement("div",{className:"row"},c.a.createElement("div",{className:"col-md-4"},c.a.createElement("div",{className:"row"},c.a.createElement("div",{className:"d-flex"},c.a.createElement("div",{className:"player"},"Player:"),c.a.createElement("div",{className:"player-name"},"Name"))),c.a.createElement("div",{className:"row"},"Level"),c.a.createElement("div",{className:"row"},"Menu")),c.a.createElement("div",{className:"col-md-6"},c.a.createElement("div",{className:"row"},a.map((function(e){return c.a.createElement("div",{className:"col-md-3",key:e.word},c.a.createElement("button",{className:"btn btn-primary text-center",onClick:O},e.firstPart))})),a.map((function(e){return c.a.createElement("div",{className:"col-md-3",key:e.word},c.a.createElement("button",{className:"btn btn-primary mb-3",onClick:O},e.secondPart))}))),c.a.createElement("div",{className:"row"},c.a.createElement("span",null),c.a.createElement("span",null,"Incorrect pair:"),c.a.createElement("div",{className:"button-class"},c.a.createElement("button",{className:"btn"},"Clear")))),c.a.createElement("div",{className:"col-md-2"},c.a.createElement("div",{className:"row"},"Round:",c.a.createElement("div",{className:"col-md-12"},g),c.a.createElement("div",{className:"col-md-12"},"2"))))}l.a.render(c.a.createElement(c.a.StrictMode,null,c.a.createElement(u,null)),document.getElementById("root")),"serviceWorker"in navigator&&navigator.serviceWorker.ready.then((function(e){e.unregister()})).catch((function(e){console.error(e.message)}))}},[[30,1,2]]]);
//# sourceMappingURL=main.389921e6.chunk.js.map