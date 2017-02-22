package com.aditya.xlabs.RuleService.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;

import com.aditya.xlabs.Entity.AClassShip;
import com.aditya.xlabs.Entity.WingerShip;
import com.aditya.xlabs.Entity.XLSpaceShipInstance;
import com.aditya.xlabs.RuleService.IRuleService;
import com.aditya.xlabs.factory.SpaceShip;
import com.aditya.xlabs.utilities.IPropertyReaderService;
import com.aditya.xlabs.utilities.impl.PropertyReaderServiceImpl;

public class RuleServiceImplTest {
	
	IPropertyReaderService propReader = new PropertyReaderServiceImpl();
	IRuleService ruleService = new RuleServiceImpl();
	
	
	@Test
	public void testWithOneNegativeForStandard_validateAllowedCountOfShots() {
		XLSpaceShipInstance instanceWithCurrentTurn = Mockito.mock(XLSpaceShipInstance.class);
		SpaceShip wingerInstance = new WingerShip(-1);
		SpaceShip aClassInstance = new AClassShip(1);
		Map<String, SpaceShip> m = new HashMap<String, SpaceShip>();
		m.put("01", wingerInstance);
		m.put("d4", aClassInstance);
		Mockito.when(instanceWithCurrentTurn.getPosnToShipsMap()).thenReturn(m);
		boolean result = ruleService.validateAllowedCountOfShots(instanceWithCurrentTurn, Arrays.asList("af"), "standard");
		Assert.assertTrue("Expecting Value as True, as No. of LIVE Ships are 1", result);
	}
	
	@Test
	public void testWithNEGATIVEForStandard_validateAllowedCountOfShots() {
		XLSpaceShipInstance instanceWithCurrentTurn = Mockito.mock(XLSpaceShipInstance.class);
		SpaceShip wingerInstance = new WingerShip(-1);
		SpaceShip aClassInstance = new AClassShip(-3);
		Map<String, SpaceShip> m = new HashMap<String, SpaceShip>();
		m.put("01", wingerInstance);
		m.put("d4", aClassInstance);
		Mockito.when(instanceWithCurrentTurn.getPosnToShipsMap()).thenReturn(m);
		boolean result = ruleService.validateAllowedCountOfShots(instanceWithCurrentTurn, Arrays.asList("af"), "standard");
		Assert.assertNotSame("Expecting Value as false" , true , result);
	}
	
	@Test
	public void testWithZEROForStandard_validateAllowedCountOfShots() {
		XLSpaceShipInstance instanceWithCurrentTurn = Mockito.mock(XLSpaceShipInstance.class);
		SpaceShip wingerInstance = new WingerShip(0);
		SpaceShip aClassInstance = new AClassShip(0);
		Map<String, SpaceShip> m = new HashMap<String, SpaceShip>();
		m.put("01", wingerInstance);
		m.put("d4", aClassInstance);
		Mockito.when(instanceWithCurrentTurn.getPosnToShipsMap()).thenReturn(m);
		boolean result = ruleService.validateAllowedCountOfShots(instanceWithCurrentTurn, Arrays.asList("af"), "standard");
		Assert.assertNotSame("Expecting Value as false" , true , result);
	}
	
	@Test
	public void testWithNullInstance_validateAllowedCountOfShots() {
		boolean result = ruleService.validateAllowedCountOfShots(null, Arrays.asList("af"), "standard");
		Assert.assertEquals("Expecting Value as false", false, result);
	}
	
	
	@Test
	public void testPositiveForStandard_validateAllowedCountOfShots() {
		XLSpaceShipInstance instanceWithCurrentTurn = Mockito.mock(XLSpaceShipInstance.class);
		SpaceShip wingerInstance = new WingerShip(2);
		SpaceShip aClassInstance = new AClassShip(2);
		Map<String, SpaceShip> m = new HashMap<String, SpaceShip>();
		m.put("01", wingerInstance);
		m.put("d4", aClassInstance);
		Mockito.when(instanceWithCurrentTurn.getPosnToShipsMap()).thenReturn(m);
		boolean result = ruleService.validateAllowedCountOfShots(instanceWithCurrentTurn,  Arrays.asList("af","04"), "standard");
		Assert.assertEquals("Expecting Value as true", true, result);
	}
	
	
	@Test
	public void testPositiveForDesperation_validateAllowedCountOfShots() {
		XLSpaceShipInstance instanceWithCurrentTurn = Mockito.mock(XLSpaceShipInstance.class);
		Mockito.when(instanceWithCurrentTurn.getEligibleCountOfShotsForNextTurn()).thenReturn(0);
		boolean result = ruleService.validateAllowedCountOfShots(instanceWithCurrentTurn, Arrays.asList("af"), "desperation");
		Assert.assertTrue("Expecting Value as 1, as This is DEFAULT value declared in config file", result);
	}
	
	
	@Test
	public void testWithNullInstanceForDesperation_validateAllowedCountOfShots() {
		boolean result = ruleService.validateAllowedCountOfShots(null, Arrays.asList("af"), "desperation");
		Assert.assertTrue("Expecting Value as False as instance is NULL", !result);
	}
	
	
	@Test
	public void testWithPreviousValuePositiveForDesperation_validateAllowedCountOfShots() {
		XLSpaceShipInstance instanceWithCurrentTurn = Mockito.mock(XLSpaceShipInstance.class);
		Mockito.when(instanceWithCurrentTurn.getEligibleCountOfShotsForNextTurn()).thenReturn(3);
		boolean result = ruleService.validateAllowedCountOfShots(instanceWithCurrentTurn, Arrays.asList("af","b1","e9"), "desperation");
		Assert.assertTrue("Expecting Value as 7, as In Previous Turn 7 Ships were destrroyed", result);
	}
	
	
	
	@Test
	public void testWithNEGATIVEForDesperation_validateAllowedCountOfShots() {
		XLSpaceShipInstance instanceWithCurrentTurn = Mockito.mock(XLSpaceShipInstance.class);
		Mockito.when(instanceWithCurrentTurn.getEligibleCountOfShotsForNextTurn()).thenReturn(-2);
		boolean result = ruleService.validateAllowedCountOfShots(instanceWithCurrentTurn, Arrays.asList("af"), "desperation");
		Assert.assertTrue("Expecting Value as -101, as No. of LIVE Ships destr", !result);
	}
	
	
	
	@Test
	public void testPositiveForXShot_validateAllowedCountOfShots() {
		XLSpaceShipInstance instanceWithCurrentTurn = Mockito.mock(XLSpaceShipInstance.class);
		boolean result = ruleService.validateAllowedCountOfShots(instanceWithCurrentTurn, Arrays.asList("af","b1","e9"), "3-shot");
		Assert.assertTrue("Expecting Value as 3, as this is X-Shot type", result);
	}
	
	
	
	@Test
	public void testWithNullInstanceForXShot_validateAllowedCountOfShots() {
		boolean result = ruleService.validateAllowedCountOfShots(null, Arrays.asList("af","b1","e9"), "3-shot");
		Assert.assertTrue("Expecting Value as -1,instance is NULL", !result);
	}
	
	
	@Test
	public void testWithPreviousValuePositiveForXShot_validateAllowedCountOfShots() {
		XLSpaceShipInstance instanceWithCurrentTurn = Mockito.mock(XLSpaceShipInstance.class);
		boolean result = ruleService.validateAllowedCountOfShots(instanceWithCurrentTurn,Arrays.asList("af"), "0-shot");
		Assert.assertTrue("Expecting Value as 0..", !result);
	}
	
	
	
	@Test
	public void testWithWrongInputForXShot_validateAllowedCountOfShots() {
		XLSpaceShipInstance instanceWithCurrentTurn = Mockito.mock(XLSpaceShipInstance.class);
		boolean result = ruleService.validateAllowedCountOfShots(instanceWithCurrentTurn,Arrays.asList("af"), "-shot");
		Assert.assertEquals("Expecting Value as -101, as No. of LIVE Ships destr", false, result);
	}

	
	
	
	@Test
	public void testPositiveForStandard_calculateAllowedCountOfShots() {
		XLSpaceShipInstance instanceWithCurrentTurn = Mockito.mock(XLSpaceShipInstance.class);
		SpaceShip wingerInstance = new WingerShip(2);
		SpaceShip aClassInstance = new AClassShip(2);
		Map<String, SpaceShip> m = new HashMap<String, SpaceShip>();
		m.put("01", wingerInstance);
		m.put("d4", aClassInstance);
		Mockito.when(instanceWithCurrentTurn.getPosnToShipsMap()).thenReturn(m);
		int result = ruleService.calculateAllowedCountOfShots(instanceWithCurrentTurn, "standard");
		Assert.assertEquals("Expecting Value as 2, as No. of LIVE Ships are 2", 2, result);
	}
	
	@Test
	public void testWithNullInstance_calculateAllowedCountOfShots() {
		int result = ruleService.calculateAllowedCountOfShots(null, "standard");
		Assert.assertEquals("Expecting Value as -1,instance is NULL", -1, result);
	}
	
	@Test
	public void testWithZEROForStandard_calculateAllowedCountOfShots() {
		XLSpaceShipInstance instanceWithCurrentTurn = Mockito.mock(XLSpaceShipInstance.class);
		SpaceShip wingerInstance = new WingerShip(0);
		SpaceShip aClassInstance = new AClassShip(0);
		Map<String, SpaceShip> m = new HashMap<String, SpaceShip>();
		m.put("01", wingerInstance);
		m.put("d4", aClassInstance);
		Mockito.when(instanceWithCurrentTurn.getPosnToShipsMap()).thenReturn(m);
		int result = ruleService.calculateAllowedCountOfShots(instanceWithCurrentTurn, "standard");
		Assert.assertEquals("Expecting Value as 0, as No. of LIVE Ships are 0", 0, result);
	}
	
	
	@Test
	public void testWithNEGATIVEForStandard_calculateAllowedCountOfShots() {
		XLSpaceShipInstance instanceWithCurrentTurn = Mockito.mock(XLSpaceShipInstance.class);
		SpaceShip wingerInstance = new WingerShip(-1);
		SpaceShip aClassInstance = new AClassShip(-3);
		Map<String, SpaceShip> m = new HashMap<String, SpaceShip>();
		m.put("01", wingerInstance);
		m.put("d4", aClassInstance);
		Mockito.when(instanceWithCurrentTurn.getPosnToShipsMap()).thenReturn(m);
		int result = ruleService.calculateAllowedCountOfShots(instanceWithCurrentTurn, "standard");
		Assert.assertEquals("Expecting Value as 0, as No. of LIVE Ships are 0", 0, result);
	}
	
	
	@Test
	public void testWithOneNegativeForStandard_calculateAllowedCountOfShots() {
		XLSpaceShipInstance instanceWithCurrentTurn = Mockito.mock(XLSpaceShipInstance.class);
		SpaceShip wingerInstance = new WingerShip(-1);
		SpaceShip aClassInstance = new AClassShip(1);
		Map<String, SpaceShip> m = new HashMap<String, SpaceShip>();
		m.put("01", wingerInstance);
		m.put("d4", aClassInstance);
		Mockito.when(instanceWithCurrentTurn.getPosnToShipsMap()).thenReturn(m);
		int result = ruleService.calculateAllowedCountOfShots(instanceWithCurrentTurn, "standard");
		Assert.assertEquals("Expecting Value as 0, as No. of LIVE Ships are 0", 1, result);
	}
	
	
	@Test
	public void testPositiveForDesperation_calculateAllowedCountOfShots() {
		XLSpaceShipInstance instanceWithCurrentTurn = Mockito.mock(XLSpaceShipInstance.class);
		Mockito.when(instanceWithCurrentTurn.getEligibleCountOfShotsForNextTurn()).thenReturn(0);
		int result = ruleService.calculateAllowedCountOfShots(instanceWithCurrentTurn, "desperation");
		Assert.assertEquals("Expecting Value as 1, as This is DEFAULT value declared in config file", 1, result);
	}
	
	@Test
	public void testWithNullInstanceForDesperation_calculateAllowedCountOfShots() {
		int result = ruleService.calculateAllowedCountOfShots(null, "desperation");
		Assert.assertEquals("Expecting Value as -1,instance is NULL", -1, result);
	}
	
	@Test
	public void testWithPreviousValuePositiveForDesperation_calculateAllowedCountOfShots() {
		XLSpaceShipInstance instanceWithCurrentTurn = Mockito.mock(XLSpaceShipInstance.class);
		Mockito.when(instanceWithCurrentTurn.getEligibleCountOfShotsForNextTurn()).thenReturn(7);
		int result = ruleService.calculateAllowedCountOfShots(instanceWithCurrentTurn, "desperation");
		Assert.assertEquals("Expecting Value as 7, as In Previous Turn 7 Ships were destrroyed", 7, result);
	}
	
	
	@Test
	public void testWithNEGATIVEForDesperation_calculateAllowedCountOfShots() {
		XLSpaceShipInstance instanceWithCurrentTurn = Mockito.mock(XLSpaceShipInstance.class);
		Mockito.when(instanceWithCurrentTurn.getEligibleCountOfShotsForNextTurn()).thenReturn(-2);
		int result = ruleService.calculateAllowedCountOfShots(instanceWithCurrentTurn, "desperation");
		Assert.assertEquals("Expecting Value as -101, as No. of LIVE Ships destr", -101, result);
	}
	
	
	
	@Test
	public void testPositiveForXShot_calculateAllowedCountOfShots() {
		XLSpaceShipInstance instanceWithCurrentTurn = Mockito.mock(XLSpaceShipInstance.class);
		int result = ruleService.calculateAllowedCountOfShots(instanceWithCurrentTurn, "7-shot");
		Assert.assertEquals("Expecting Value as 7, as this is X-Shot type", 7, result);
	}
	
	
	
	@Test
	public void testWithNullInstanceForXShot_calculateAllowedCountOfShots() {
		int result = ruleService.calculateAllowedCountOfShots(null, "7-shot");
		Assert.assertEquals("Expecting Value as -1,instance is NULL", -1, result);
	}
	
	@Test
	public void testWithPreviousValuePositiveForXShot_calculateAllowedCountOfShots() {
		XLSpaceShipInstance instanceWithCurrentTurn = Mockito.mock(XLSpaceShipInstance.class);
		int result = ruleService.calculateAllowedCountOfShots(instanceWithCurrentTurn, "0-shot");
		Assert.assertEquals("Expecting Value as 0..", 0, result);
	}
	
	
	@Test
	public void testWithWrongInputForXShot_calculateAllowedCountOfShots() {
		XLSpaceShipInstance instanceWithCurrentTurn = Mockito.mock(XLSpaceShipInstance.class);
		int result = ruleService.calculateAllowedCountOfShots(instanceWithCurrentTurn, "-shot");
		Assert.assertEquals("Expecting Value as -101, as No. of LIVE Ships destr", -101, result);
	}
	
}
