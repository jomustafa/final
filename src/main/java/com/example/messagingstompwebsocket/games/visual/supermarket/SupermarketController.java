package com.example.messagingstompwebsocket.games.visual.supermarket;

import java.util.ArrayList;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import com.example.messagingstompwebsocket.games.visual.hiddenObjects.HiddenQuests;
import com.example.messagingstompwebsocket.games.visual.hiddenObjects.Object;
import com.example.messagingstompwebsocket.games.visual.supermarket.Product;
import com.example.messagingstompwebsocket.utilities.DBManager;

@Controller
public class SupermarketController {

	DBManager dbm = new DBManager();


	@MessageMapping("/getrandomproducts")
	@SendToUser("/topic/getrandomproducts")
	public ArrayList<String> getRandomProducts(SimpMessageHeaderAccessor headerAccessor, int level) {

		Supermarket sm = (Supermarket) headerAccessor.getSessionAttributes().get("game");

		ArrayList<String> productNameAndImage = new ArrayList<String>();
		System.out.println(sm);
		System.out.println(level);
		// 1 for yes - 0 for no)
		for (Product p : sm.chooseRandomProductsForLevel(level)) {
			productNameAndImage.add(p.getProductName());
			// getProductImage returns the path of the image
			productNameAndImage.add(p.getProductImage());
		}

		return productNameAndImage;
	}

	@MessageMapping("/getproducts")
	@SendToUser("/topic/getproducts")
	public ArrayList<String> getProducts(SimpMessageHeaderAccessor headerAccessor, int level) { // check if the action was valid(checks button if the word is
														// correct,

		Supermarket sm = new Supermarket(level);
		headerAccessor.getSessionAttributes().put("game", sm);
		ArrayList<String> productNameAndImage = new ArrayList<String>();

		// 1 for yes - 0 for no)
		for (Product p : sm.getProductList()) {
			productNameAndImage.add(p.getProductName());
			// getProductImage returns the path of the image
			productNameAndImage.add(p.getProductImage());
		}

		return productNameAndImage;
	}
}
