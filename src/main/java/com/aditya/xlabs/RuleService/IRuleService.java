package com.aditya.xlabs.RuleService;

import java.util.List;

import com.aditya.xlabs.Entity.XLSpaceShipInstance;

public interface IRuleService {

	boolean validateAllowedCountOfShots(XLSpaceShipInstance instanceWithCurrentTurn, List<String> salvo, String ruleType);
	
	int calculateAllowedCountOfShots(XLSpaceShipInstance instanceWithCurrentTurn, String ruleType);

}
