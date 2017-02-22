package com.aditya.xlabs.utilities.impl;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import com.aditya.xlabs.utilities.IConvertorService;

public class ConvertorServiceImplTest {

	IConvertorService convService = new ConvertorServiceImpl();
	
	@Test
	public void testNull_convertShotsToIndexes() {
		List<String> actualResult = convService.convertShotsToIndexes(null);
		Assert.assertTrue("ListOfIndexes should not be null", (null != actualResult)); 
	}
	
	@Test
	public void testemptyList_convertShotsToIndexes() {
		List<String> listOfIndexes = new ArrayList<String>(0);
		List<String> listOfShots = new ArrayList<String>();
		List<String> actualResult = convService.convertShotsToIndexes(listOfShots);
		Assert.assertEquals("ListOfIndexes should be same",listOfIndexes, actualResult); 
	}
	
	@Test
	public void testConvertShotsToIndex() {
		List<String> listOfIndexes = new ArrayList<String>();
		listOfIndexes.add("8C");
		listOfIndexes.add("B2");
		listOfIndexes.add("1E");
		
		List<String> listOfShots = new ArrayList<String>();
		listOfShots.add("8xC");
		listOfShots.add("Bx2");
		listOfShots.add("1xE");
		List<String> actualResult = convService.convertShotsToIndexes(listOfShots);
		Assert.assertEquals("ListOfIndexes should be same",listOfIndexes, actualResult); 
	}
	
}

