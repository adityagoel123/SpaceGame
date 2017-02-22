package com.aditya.xlabs.GridService.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.aditya.xlabs.GridService.IGridService;

public class GridServiceImpl implements IGridService {

	public List<String> generateShotsToGridRandomly(int noOfShotsToGenerate) {
		Random random = new Random();
		if(noOfShotsToGenerate >= 0){
			List<String> salvoOfShots = new ArrayList<String>(noOfShotsToGenerate); 
			while(salvoOfShots.size() < noOfShotsToGenerate) {
				salvoOfShots.add(Integer.toHexString(random.nextInt(16))+Integer.toHexString(random.nextInt(16)));
		    }
			return salvoOfShots;
		} else 
			return Collections.emptyList();
	}
}
