package com.aditya.xlabs.GridService.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.aditya.xlabs.GridService.IVisualGridGenerationService;
import com.aditya.xlabs.factory.SpaceShip;

public class VisualGridGenerationServiceImpl implements IVisualGridGenerationService {

	public String generateGridForThisInstance(Map<String, SpaceShip> posnToShipsMap) {
		List<String> posnOccupiedByShips = new ArrayList<String>(posnToShipsMap.keySet());
		
		String grid = "" + "\n";
		for(int j=0; j<16;j++){
			for(int i=0;i<16;i++){
				String currenIndex = Integer.toHexString(j)+Integer.toHexString(i);
				if(posnOccupiedByShips.contains(currenIndex)){
					grid = grid.concat("X" + "\t");
				}else {
					grid = grid.concat("." + "\t");
				}
			}
			grid = grid.concat("\n");
		}
		return grid;
	}

}
