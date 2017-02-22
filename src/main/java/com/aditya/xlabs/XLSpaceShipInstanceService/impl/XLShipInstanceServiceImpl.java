package com.aditya.xlabs.XLSpaceShipInstanceService.impl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.aditya.xlabs.Entity.Player;
import com.aditya.xlabs.Entity.XLSpaceShipInstance;
import com.aditya.xlabs.XLSpaceShipInstanceService.IXLShipInstanceService;
import com.aditya.xlabs.factory.SpaceShip;
import com.aditya.xlabs.utilities.IPropertyReaderService;
import com.aditya.xlabs.utilities.impl.PropertyReaderServiceImpl;

public class XLShipInstanceServiceImpl implements IXLShipInstanceService {

	IPropertyReaderService propReader = new PropertyReaderServiceImpl();
	
	public XLSpaceShipInstance instantiateXLSpaceShipInstance(Player owner, String ipAdd, String port){
		if(null != owner){
			XLSpaceShipInstance instance = new XLSpaceShipInstance();
			instance.setOwnerOfInstance(owner);
			instance.setNextTurnBelongsToThisInstance(false);
			instance.setIpAddressPlayingFrom(ipAdd);
			instance.setPortPlayingFrom(port);
			placeShipsAtRandomPositionsInGrid(instance);
			return instance;
		} else {
			return null;
		}
	}

	public void placeShipsAtRandomPositionsInGrid(XLSpaceShipInstance instance) {
		if(null != instance){
			// Generate a random index value in 2 D matrix i.e. Grid.
			Random random = new Random();
			Set<String> uniquePositionsInGrid = new HashSet<String>(5);
			while(uniquePositionsInGrid.size() < 5) {
				while (uniquePositionsInGrid.add(Integer.toHexString(random.nextInt(16))+Integer.toHexString(random.nextInt(16))) != true)
		                ;
		    }
			List<String> listOfUniquePositions = new ArrayList<String>(uniquePositionsInGrid); 
			uniquePositionsInGrid = null;
			try {
				String shipsTobeOwned = propReader.readPropertyKey("ships.ToBeOwnedBy.Every.XLSpaceShipInstance");
				if(null != shipsTobeOwned && !shipsTobeOwned.isEmpty()){
					List<String> list = new ArrayList<String>(Arrays.asList(shipsTobeOwned.split(",")));
					for(int i=0; i< list.size(); i++){
						instance.getPosnToShipsMap().put(listOfUniquePositions.get(i), 
								(SpaceShip) Class.forName("com.aditya.xlabs.Entity."+list.get(i)).getConstructor(Integer.TYPE).
									newInstance(Integer.parseInt(propReader.readPropertyKey("max.Capacity.ToBear.Shots.By."+list.get(i)))));
					}
				}
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NullPointerException e){
				e.printStackTrace();
			}
		}
	}
}
