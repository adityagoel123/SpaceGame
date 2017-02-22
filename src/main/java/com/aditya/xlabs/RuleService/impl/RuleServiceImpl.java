package com.aditya.xlabs.RuleService.impl;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.aditya.xlabs.Entity.XLSpaceShipInstance;
import com.aditya.xlabs.RuleService.IRuleService;
import com.aditya.xlabs.enums.GameRuleTypeEnum;
import com.aditya.xlabs.factory.SpaceShip;
import com.aditya.xlabs.utilities.IPropertyReaderService;
import com.aditya.xlabs.utilities.impl.PropertyReaderServiceImpl;

public class RuleServiceImpl implements IRuleService {
	
	IPropertyReaderService propReader = new PropertyReaderServiceImpl();
    public static final int INVALID_INPUT_ALLOWED_COUNT_OF_SHOTS = -101;
 
	
	public boolean validateAllowedCountOfShots(XLSpaceShipInstance instanceWithCurrentTurn, List<String> salvo, String ruleType) {
		// if ruletye is standard, 
		// no of shots should be equal to Active Ships.
		// if ruletye is X - shots, 
		// no of shots should be equal to Fixed X.
		if(null != instanceWithCurrentTurn){
			if(null == ruleType || ruleType.equalsIgnoreCase(GameRuleTypeEnum.STANDARD.code())){
				if(calculateAllowedCountOfShots(instanceWithCurrentTurn, GameRuleTypeEnum.STANDARD.code()) == salvo.size())
					return true;
				else 
					return false;
			} else if(ruleType.contains("-shot")){
				if(calculateAllowedCountOfShots(instanceWithCurrentTurn, ruleType) == salvo.size())
					return true;
				else 
					return false;
			} else if(ruleType.equalsIgnoreCase(GameRuleTypeEnum.DESPERATION.code())){
				// User can start only with 1 shot salvos. Read this value from configuration source..
				if(calculateAllowedCountOfShots(instanceWithCurrentTurn, GameRuleTypeEnum.DESPERATION.code()) == salvo.size()){
					instanceWithCurrentTurn.setEligibleCountOfShotsForNextTurn(0);
					return true;
				} else {
					// Allowed Count Of Shots And Shots in this salvo aren't matching.
					return false;
				}
			} else if(ruleType.equalsIgnoreCase(GameRuleTypeEnum.SUPERCHARGE.code())){
				int allowedShots = calculateAllowedCountOfShots(instanceWithCurrentTurn, GameRuleTypeEnum.SUPERCHARGE.code());
				if(allowedShots ==INVALID_INPUT_ALLOWED_COUNT_OF_SHOTS){
					// First Turn
					return true;
				} else if(allowedShots == salvo.size()) {
					// Repeat Bonus Turn 
					instanceWithCurrentTurn.setEligibleCountOfShotsForNextTurn(0);
					return true;
				} else {
					// Allowed Count Of Shots And Shots in this salvo aren't matching.
					return false;
				}
			}
			return false;
		} else {
			return false;
		}
	}
	
	public int calculateAllowedCountOfShots(XLSpaceShipInstance instanceWithCurrentTurn, String ruleType) {
		if(null != instanceWithCurrentTurn){
			if (null == ruleType || ruleType.equalsIgnoreCase(GameRuleTypeEnum.STANDARD.code())) {
				Collection<SpaceShip> allShipsOfThisInstance = instanceWithCurrentTurn.getPosnToShipsMap().values();
				int allowedCountOfShots = 0;
				for (SpaceShip s : allShipsOfThisInstance) {
					if (s.getShotsThisShipCanBear() > 0) {
						allowedCountOfShots++;
					}
				}
				return allowedCountOfShots;
			} else if(ruleType.contains("-shot")){
				String noOfShots = ruleType.replace("-shot", "");
				if(null !=noOfShots && !noOfShots.isEmpty() && noOfShots!="")
					return(Integer.parseInt(noOfShots));
				else
					return INVALID_INPUT_ALLOWED_COUNT_OF_SHOTS;
			
			} else if(ruleType.equalsIgnoreCase(GameRuleTypeEnum.DESPERATION.code())){
				// User can start only with N shot salvos. Read Configuration from File.
				try {
					if(instanceWithCurrentTurn.getEligibleCountOfShotsForNextTurn() > 0){
						return(instanceWithCurrentTurn.getEligibleCountOfShotsForNextTurn());
					} else if (instanceWithCurrentTurn.getEligibleCountOfShotsForNextTurn() < 0){
						return INVALID_INPUT_ALLOWED_COUNT_OF_SHOTS;
					} else {
						return(Integer.parseInt(propReader.readPropertyKey("salvo.Of.ShotsAllowed.For.DesperationMode")));	
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (ruleType.equalsIgnoreCase(GameRuleTypeEnum.SUPERCHARGE.code())){
				// User can start with ANY Number of Salvo of Shots.Page #9.
				if(instanceWithCurrentTurn.getEligibleCountOfShotsForNextTurn() > 0){
					return(instanceWithCurrentTurn.getEligibleCountOfShotsForNextTurn());
				} else {
					return INVALID_INPUT_ALLOWED_COUNT_OF_SHOTS;	
				}
			}
			return 0;
		} else {
			return -1;
		}
	}
}
