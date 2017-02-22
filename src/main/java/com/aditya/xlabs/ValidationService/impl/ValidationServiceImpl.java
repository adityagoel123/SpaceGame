package com.aditya.xlabs.ValidationService.impl;

import com.aditya.xlabs.Entity.XLSpaceShipInstance;
import com.aditya.xlabs.Request.RequestForFiringSalvoOfShots;
import com.aditya.xlabs.Request.RequestForNewGame;
import com.aditya.xlabs.SpaceService.ISpaceShipService;
import com.aditya.xlabs.SpaceService.impl.SpaceShipServiceImpl;
import com.aditya.xlabs.ValidationService.IValidationService;
import com.aditya.xlabs.controller.XLWebServiceController;
import com.aditya.xlabs.enums.APITypeEnum;

public class ValidationServiceImpl implements IValidationService {

	ISpaceShipService spaceService = new SpaceShipServiceImpl();

	public boolean validateRequestForNewGame(RequestForNewGame request, String apiType){
		boolean isValidRequest = false;
		if(null != request && apiType != null){
			// Check for Requesting Player's ID & Name (Requester to Play)
			if(apiType.equalsIgnoreCase(APITypeEnum.PROTOCOL_API.code()) && request.getUser_id() != null && request.getFull_name() != null)
				isValidRequest = true;
			else 
				return isValidRequest;
			// Check for Port And IP
			if(request.getSpaceship_protocol() != null && request.getSpaceship_protocol().getHostname() != null && request.getSpaceship_protocol().getPort() != null)
				isValidRequest = true;
			else
				isValidRequest = false;
		}		
		return isValidRequest;
		// No Validation Check for Rules, as thats optional pureley.
	}

		
	public boolean validateRequestForFiringShots(RequestForFiringSalvoOfShots request, String gameID) {
		boolean isValidRequest = false;

		// Check for Valid Game ID 
		if(null != gameID && gameID != "" && null != XLWebServiceController.activeGamesStore.get(gameID))
			isValidRequest = true;
		else 
			return isValidRequest;
		
		// Check whether request contains any shot in it OR not
		if(null != request.getSalvo() && !request.getSalvo().isEmpty())
			isValidRequest = true;
		else
			isValidRequest = false;
				
		return isValidRequest;
	}

	public boolean validateRequestForGameStatus(String gameID) {
		// Check for Valid Game ID.
		if(null != gameID && gameID !="" && null != XLWebServiceController.activeGamesStore.get(gameID))
			return true;
		else 
			return false;
				
	}


	public boolean validateRequestFromCorrectSource(String gameID, String ipAddress, String port) {
		// TODO Auto-generated method stub
		// Fetch The Instance with current Turn.
		// Match The IP & Port from where request came WITH IP & Port registered with above instance.
		boolean isValidRrequest = false;
		XLSpaceShipInstance instanceWithCurrentTurn = spaceService.findInstanceWithCurrentTurn(XLWebServiceController.activeGamesStore.get(gameID));
		if(null != instanceWithCurrentTurn && null != ipAddress && instanceWithCurrentTurn.getIpAddressPlayingFrom().equalsIgnoreCase(ipAddress)){
			// IPAddress Currently Playing Is a Valid one.
			isValidRrequest = true;
		} else {
			return false;
		}
		
		if(null != port && instanceWithCurrentTurn.getPortPlayingFrom().equalsIgnoreCase(port)){
			isValidRrequest = true;
		} else {
			return false;
		}
		return isValidRrequest;
	}

}
