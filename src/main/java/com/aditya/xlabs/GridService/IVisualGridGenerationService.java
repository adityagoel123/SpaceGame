package com.aditya.xlabs.GridService;

import java.util.Map;

import com.aditya.xlabs.factory.SpaceShip;

public interface IVisualGridGenerationService {

	String generateGridForThisInstance(Map<String, SpaceShip> posnToShipsMap);
	
}
