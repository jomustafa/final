package com.example.messagingstompwebsocket.modules;

import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.messagingstompwebsocket.games.verbal.splitWords.SplitWords;
import com.example.messagingstompwebsocket.games.verbal.splitWords.SplitWord;

@Component
public class SplitWordModule {

	private static final Logger log = LoggerFactory.getLogger(SplitWordModule.class);
	private final SocketIONamespace namespace;
	public SplitWords splitWord = new SplitWords(1);
	boolean isValid = false;

	@Autowired
	public SplitWordModule(SocketIOServer server) {
		this.namespace = server.addNamespace("");
		this.namespace.addConnectListener(onConnected());
		this.namespace.addDisconnectListener(onDisconnected());
		this.namespace.addEventListener("chat", SplitWord.class, onChatReceived());
	}

	private DataListener<SplitWord> onChatReceived() {
		return (client, data, ackSender) -> {
			Object[] args = { data.getFirstPart(), data.getSecondPart() };
			isValid = splitWord.isValidAction(args) == 0 ? false : true;
		};
	}

	private DisconnectListener onDisconnected() {
		return client -> {
			log.debug("Client[{}] - Disconnected from chat module.", client.getSessionId().toString());
		};
	}

	public ConnectListener onConnected() {
		return client -> {

			log.debug("Client[{}] - Connected to chat module through '{}'", client.getSessionId().toString(), null);
			namespace.getBroadcastOperations().sendEvent("all_split_words", splitWord);
			namespace.getBroadcastOperations().sendEvent("result", isValid);

		};
	}

}
