package com.example.messagingstompwebsocket;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.messagingstompwebsocket.Greeting;
import com.example.messagingstompwebsocket.games.Player;
import com.example.messagingstompwebsocket.games.Score;
import com.example.messagingstompwebsocket.utilities.DBManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin
@RestController
public class DBController {
	DBManager dbm = new DBManager();
	String userID;
	int level;
	
	  // Aggregate root
	  // tag::get-aggregate-root[]
	  @GetMapping("/players")
	  LinkedList<Player> getPlayers() throws SQLException {
	    return dbm.getAllPlayers();
	  }
	  // end::get-aggregate-root[]

	  @PostMapping("/newplayer")
	  void newPlayer(@RequestBody Player newPlayer) {
	    dbm.recordPlayer(newPlayer.getID(), newPlayer.getName());
	  }

	  // Single item
	  
	  
	  @GetMapping("/playerstats")
	  LinkedList<Score> getPlayerStats(String name, String id, String game) throws SQLException {
		
	    return dbm.getScores(name, id, game);
	  }
//	  
//	  @GetMapping("/employees/{id}")
//	  Employee one(@PathVariable Long id) {
//	    
//	    return repository.findById(id)
//	      .orElseThrow(() -> new EmployeeNotFoundException(id));
//	  }
//
//	  @PutMapping("/employees/{id}")
//	  Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
//	    
//	    return repository.findById(id)
//	      .map(employee -> {
//	        employee.setName(newEmployee.getName());
//	        employee.setRole(newEmployee.getRole());
//	        return repository.save(employee);
//	      })
//	      .orElseGet(() -> {
//	        newEmployee.setId(id);
//	        return repository.save(newEmployee);
//	      });
//	  }
//
//	  @DeleteMapping("/employees/{id}")
//	  void deleteEmployee(@PathVariable Long id) {
//	    repository.deleteById(id);
//	  }
}
