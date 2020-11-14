var stompClient = null;
var buttonStack = [];
var splitWords = [];
function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	if (connected) {
		$("#conversation").show();
	}
	else {
		$("#conversation").hide();
	}
	$("#greetings").html("");
}

function connect() {
	var socket = new SockJS('/brainbright-websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		setConnected(true);
		console.log('Connected: ' + frame);
		stompClient.subscribe('/topic/validactionresponse', function(splitwords) {
			if (!JSON.parse(splitwords.body)) {
				showResponse("That's wrong!");
			} else {
				showResponse("Correct!");	
			}
		});
			stompClient.subscribe('/topic/splitwordlist', function(result) {
			splitWords = JSON.parse(result.body);
			initButtons();
		});
		getSplitWords();
	});
}

function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

function sendWordCombination(firstWord, secondWord) {
	stompClient.send("/app/validaction", {}, JSON.stringify({ "firstPart": firstWord, "secondPart": secondWord }));
}

function getSplitWords() {
	console.log("ayo? getsplitwords");
	stompClient.send("/app/getsplitwords", {}, null);
}

function showResponse(message) {
	document.getElementById("greetings").innerHTML = "<tr><td>" + message + "</td></tr>";
}


$(function() {
	$("form").on('submit', function(e) {
		e.preventDefault();
	});
	connect();

	$("#connect").click(function() { connect(); });
	$("#disconnect").click(function() { disconnect(); });
	$("#send").click(function() { sendWordCombination(); });
	$(".swbutton").click(function() {

		if (buttonStack.length == 0) {
			buttonStack.push(this);
		} else {
			var firstButton = buttonStack.pop();
			if (this.id == firstButton.id) {
				alert("same button");
			} else {
				console.log("" + firstButton.innerHTML + " " + this.innerHTML);
				sendWordCombination(firstButton.innerHTML, this.innerHTML);
			}
		}
	});
});

function initButtons() {
	console.log(splitWords);
	let buttons = document.getElementsByClassName("swbutton");
	for (let i = 0; i < splitWords.length; i++) {
		console.log(splitWords.size);
		buttons[2 * i].innerHTML = splitWords[i].firstPart;
		buttons[(2 * i) + 1].innerHTML = splitWords[i].secondPart;
	}
}