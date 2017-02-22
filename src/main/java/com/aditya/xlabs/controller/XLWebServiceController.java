package com.aditya.xlabs.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.aditya.xlabs.Entity.Game;
import com.aditya.xlabs.Entity.XLSpaceShipInstance;
import com.aditya.xlabs.Request.RequestForFiringSalvoOfShots;
import com.aditya.xlabs.Request.RequestForNewGame;
import com.aditya.xlabs.Response.ResponseForFiredSalvoOfShots;
import com.aditya.xlabs.Response.ResponseForGameStatus;
import com.aditya.xlabs.Response.ResponseForNewGame;
import com.aditya.xlabs.RuleService.IRuleService;
import com.aditya.xlabs.RuleService.impl.RuleServiceImpl;
import com.aditya.xlabs.SpaceService.ISpaceShipService;
import com.aditya.xlabs.SpaceService.impl.SpaceShipServiceImpl;
import com.aditya.xlabs.ValidationService.IValidationService;
import com.aditya.xlabs.ValidationService.impl.ValidationServiceImpl;
import com.aditya.xlabs.enums.APITypeEnum;

@RestController
@RequestMapping("/xl-spaceship")
public class XLWebServiceController {

	ISpaceShipService spaceService = new SpaceShipServiceImpl();
	IValidationService validationService = new ValidationServiceImpl();
	IRuleService ruleService = new RuleServiceImpl();
	// Game Storage Machine for GameID To Game Object Mapper.
	public static Map<String, Game> activeGamesStore = new HashMap<String, Game>();
	
	@RequestMapping(value = "/{bonjour}", method = RequestMethod.GET)
	public String helloWorld(@PathVariable String bonjour) {
		String result="Hello "+bonjour;  
		return result;
	}

	// Protocol API : Request For New game Start.
	@RequestMapping(value = "/protocol/game/new", method = RequestMethod.POST)
	public ResponseEntity<ResponseForNewGame> startNewGame(@RequestBody RequestForNewGame request) {
		// Validate the Request
		ResponseForNewGame response;
		if(validationService.validateRequestForNewGame(request, APITypeEnum.PROTOCOL_API.code())){
			response = spaceService.generateResponseForNewGame(request);
			return new ResponseEntity<ResponseForNewGame>(response, HttpStatus.CREATED);
		} else{
			response = null;
			return new ResponseEntity<ResponseForNewGame>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	// Protocol API : To Play Game Actuals.
	@RequestMapping(value = "/protocol/game/{gameID}", method = RequestMethod.PUT)
	public ResponseEntity<ResponseForFiredSalvoOfShots> fireSalvoOfShots(@PathVariable String gameID, 
			@RequestBody RequestForFiringSalvoOfShots request, HttpServletRequest r) {
		// Validate the Request
		ResponseForFiredSalvoOfShots response;
		if(validationService.validateRequestForFiringShots(request, gameID) && 
				validationService.validateRequestFromCorrectSource(gameID, r.getRemoteAddr(),String.valueOf(r.getRemotePort()))){
			Game currentlyActiveGame = activeGamesStore.get(gameID);
			if(null == currentlyActiveGame.getEndTime()){
				// Fetch the Opponent Instance, whose ships are to be destroyed.
				XLSpaceShipInstance instanceWithCurrentTurn = spaceService.findInstanceWithCurrentTurn(currentlyActiveGame);
					if(ruleService.validateAllowedCountOfShots(instanceWithCurrentTurn,request.getSalvo(),currentlyActiveGame.getRules())){
						XLSpaceShipInstance instanceWhoseShipsAreToBeDestroyed = spaceService.findInstanceWhoseShipsAreToBeDestroyed(currentlyActiveGame);
						response = spaceService.fireSalvoOfShotsAt(request, instanceWhoseShipsAreToBeDestroyed, instanceWithCurrentTurn, currentlyActiveGame.getRules());
						spaceService.determineNextTurnOrGameStatus(response,currentlyActiveGame,instanceWhoseShipsAreToBeDestroyed,instanceWithCurrentTurn);
						return new ResponseEntity<ResponseForFiredSalvoOfShots>(response, HttpStatus.ACCEPTED);
					} else {
						// Rules doesn't satisfies
						response = null;
						return new ResponseEntity<ResponseForFiredSalvoOfShots>(response, HttpStatus.FORBIDDEN);
					}
			} else {
				// Game has already been won by some player
				response = null; 
				return new ResponseEntity<ResponseForFiredSalvoOfShots>(response, HttpStatus.NOT_FOUND);
			}
		} else{
			response = null;
			return new ResponseEntity<ResponseForFiredSalvoOfShots>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	// User API : To Play Game with Opponent.
	@RequestMapping(value = "/user/game/{gameID}/fire", method = RequestMethod.PUT)
	public ResponseEntity<ResponseForFiredSalvoOfShots> receiveSalvoOfShots(@PathVariable String gameID, @RequestBody RequestForFiringSalvoOfShots request) {
		final String uri = "http://localhost:8080/SpaceBattleSimulator/xl-spaceship/protocol/game/{id}";
		Map<String, String> params = new HashMap<String, String>();
	    params.put("id", gameID);
	    RestTemplate restTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	    HttpEntity<RequestForFiringSalvoOfShots> entity = new HttpEntity<RequestForFiringSalvoOfShots>(new RequestForFiringSalvoOfShots(request.getSalvo()), headers);
	    ResponseEntity<ResponseForFiredSalvoOfShots> result = restTemplate.exchange(uri, HttpMethod.PUT, entity, 
	    		ResponseForFiredSalvoOfShots.class,params);
		return result;
	}
	
	
	// User API : Game Status Check
	@RequestMapping(value = "/user/game/{gameID}", method = RequestMethod.GET)
	public ResponseEntity<ResponseForGameStatus> getGameStatus(@PathVariable String gameID) {
		// Validate the Request
		ResponseForGameStatus response;
		if(validationService.validateRequestForGameStatus(gameID)){
			response = spaceService.findStatusOfGame(activeGamesStore.get(gameID), gameID);
			return new ResponseEntity<ResponseForGameStatus>(response, HttpStatus.ACCEPTED);
		} else{
			response = null;
			return new ResponseEntity<ResponseForGameStatus>(response, HttpStatus.BAD_REQUEST);
		}

	}
	
	// User API : Auto Pilot Mode
	@RequestMapping(value = "/user/game/{gameID}/auto", method = RequestMethod.POST)
	public ResponseEntity<Void> startAutoPilot(@PathVariable String gameID) {
		// Validate the Request
		if(validationService.validateRequestForGameStatus(gameID)){
			spaceService.startAutoPilotMode(activeGamesStore.get(gameID));
			return new ResponseEntity<Void>(HttpStatus.OK);
		} else{
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}

	}
	
	// User API : Challenge Game To Another Player.
	@RequestMapping(value = "/user/game/new", method = RequestMethod.POST)
	public ResponseEntity<Void> challengeGameToAnotherPlayer(@RequestBody RequestForNewGame request, UriComponentsBuilder ucBuilder) {
		// Validate the Request
		ResponseForNewGame response;
		if(validationService.validateRequestForNewGame(request, APITypeEnum.USER_API.code())){
			response = spaceService.generateResponseForNewGame(request);
			HttpHeaders headers = new HttpHeaders();
	        headers.setLocation(ucBuilder.path("/user/game/{id}").buildAndExpand(response.getGame_id()).toUri());
	        headers.setContentType(MediaType.TEXT_HTML);
	        return new ResponseEntity<Void>(headers, HttpStatus.SEE_OTHER);
	    } else{
			response = null;
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	}

}
