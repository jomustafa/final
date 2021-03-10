package com.example.messagingstompwebsocket;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.example.messagingstompwebsocket.games.visual.findTheObjects.HiddenObject;
import com.example.messagingstompwebsocket.games.visual.findTheObjects.MemoryQuest;

@Controller
public class UserController {


	@MessageMapping("/userauth")
	@SendToUser("/topic/userauth")
	public int authenticate(String passwordAdmin) { 
		String password = "adminadmin";
		System.out.println(password + passwordAdmin +"TEEEEEEEEEEST");
		if(password.equals(passwordAdmin)) {
			return 1;
		}
		return 0;
		
	}
}
