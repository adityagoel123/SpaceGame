package com.aditya.xlabs.XLSpaceShipInstanceService.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.aditya.xlabs.Entity.Player;
import com.aditya.xlabs.Entity.XLSpaceShipInstance;
import com.aditya.xlabs.XLSpaceShipInstanceService.IXLShipInstanceService;
import com.aditya.xlabs.factory.SpaceShip;
import com.aditya.xlabs.utilities.IPropertyReaderService;
import com.aditya.xlabs.utilities.impl.PropertyReaderServiceImpl;

public class XLShipInstanceServiceImplTest {

	IPropertyReaderService propReader = new PropertyReaderServiceImpl();
	IXLShipInstanceService xlInstanceService = new XLShipInstanceServiceImpl();
	
	
	@Test
	public void testWhetherShipsAreGettingPlacedOnGrid_placeShipsAtRandomPositionsInGrid() {
		Map<String, SpaceShip> value = new HashMap<String, SpaceShip>(0);
		// Mocking the Instance Here.
		XLSpaceShipInstance testInstance = Mockito.mock(XLSpaceShipInstance.class);
		Mockito.when(testInstance.getPosnToShipsMap()).thenReturn(value);
		xlInstanceService.placeShipsAtRandomPositionsInGrid(testInstance);
		Assert.assertNotSame(0,testInstance.getPosnToShipsMap().size());
	}
	
	@Test
	public void shipsMustBePlacedWithinGridOnly_placeShipsAtRandomPositionsInGrid(){
		Map<String, SpaceShip> value = new HashMap<String, SpaceShip>(0);
		// Mocking the Instance Here.
		XLSpaceShipInstance testInstance = Mockito.mock(XLSpaceShipInstance.class);
		Mockito.when(testInstance.getPosnToShipsMap()).thenReturn(value);
		xlInstanceService.placeShipsAtRandomPositionsInGrid(testInstance);
		Set<String> positionsPlacedAt = testInstance.getPosnToShipsMap().keySet();
		for(String s: positionsPlacedAt) {
			Assert.assertTrue("Placement Of Ships Is Not Expected outside Grid Row", allowedGridIndex.contains(s.charAt(0)));
			Assert.assertTrue("Placement Of Ships Is Not Expected outside Grid Col", allowedGridIndex.contains(s.charAt(1)));
		}
	}

	@Test
	public void CheckNextTurnMustNotBeDisturbed_instantiateXLSpaceShipInstance() {
		Player testPlayer = Mockito.mock(Player.class);
		XLSpaceShipInstance xlInstance = xlInstanceService.instantiateXLSpaceShipInstance(testPlayer,"127.0.0.1", "8081");
		Assert.assertNotSame("Next Turn must be false.", true, xlInstance.isNextTurnBelongsToThisInstance());
	}
	
	
	@Test
	public void checkWhetherThisXLSpaceInstanceOwnedByCorrectOwner_instantiateXLSpaceShipInstance() {
		Player testPlayer = Mockito.mock(Player.class);
		Mockito.when(testPlayer.getFullName()).thenReturn("check");
		XLSpaceShipInstance xlInstance = xlInstanceService.instantiateXLSpaceShipInstance(testPlayer, "127.0.0.1", "8081");
		Assert.assertEquals("Instance owned by.", "check", xlInstance.getOwnerOfInstance().getFullName());
	}

	
	@Test
	public void initializingXLSpaceInstanceWithoutPlayerObject_instantiateXLSpaceShipInstance() {
		XLSpaceShipInstance xlInstance = xlInstanceService.instantiateXLSpaceShipInstance(null,"127.0.0.1", "8081");
		Assert.assertNull("Instance must NOT be intialized", xlInstance);
	}

	@Test
	public void testWhetherXLSpaceShipInstanceGettingInitialized_instantiateXLSpaceShipInstance() {
		Player testPlayer = Mockito.mock(Player.class);
		XLSpaceShipInstance xlInstance = xlInstanceService.instantiateXLSpaceShipInstance(testPlayer,"127.0.0.1", "8081");
		Assert.assertNotNull("Instance must be intialized", xlInstance);
	}
	
	List<Character> allowedGridIndex = null;
	@Before 
	public void initializeValidIndexRange(){
		allowedGridIndex = new ArrayList<Character>();
		for(int i=0;i<16;i++){
			allowedGridIndex.add(Integer.toHexString(i).charAt(0));
		}
	}
	
	@After 
	public void method() {
		allowedGridIndex = null;
	}
	
}
