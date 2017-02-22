package com.aditya.xlabs.ValidationService;

import com.aditya.xlabs.Request.RequestForFiringSalvoOfShots;
import com.aditya.xlabs.Request.RequestForNewGame;

public interface IValidationService {

	boolean validateRequestForNewGame(RequestForNewGame request, String apiType);

	boolean validateRequestForFiringShots(RequestForFiringSalvoOfShots request, String gameID);

	boolean validateRequestForGameStatus(String gameID);
	
	boolean validateRequestFromCorrectSource(String gameID, String ipAddress, String port);
	
}
