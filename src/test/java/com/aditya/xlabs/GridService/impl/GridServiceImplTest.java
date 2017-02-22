package com.aditya.xlabs.GridService.impl;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.aditya.xlabs.GridService.IGridService;
import com.aditya.xlabs.GridService.impl.GridServiceImplTest;

public class GridServiceImplTest {

	IGridService gridService = new GridServiceImpl();

	List<Character> allowedGridIndex = null;
	
	@Before 
	public void initializeValidIndexRange(){
		allowedGridIndex = new ArrayList<Character>();
		for(int i=0;i<16;i++){
			allowedGridIndex.add(Integer.toHexString(i).charAt(0));
		}
	}
	
	@Test
	public void checkForNullOREmptyListOfShots(){
		Assert.assertNotNull("Empty List is Not Expected", gridService.generateShotsToGridRandomly(10));
	}

	@Test
	public void checkForCountOfShotsGenerated(){
		Assert.assertEquals("10 No. of Shots should be generated..", 10, gridService.generateShotsToGridRandomly(10).size());
	}
	
	@Test(timeout=50)
	public void checkForEfficiencyForShotsgeneration(){
		Assert.assertEquals("10K shotss should be generated under 50 ms.", 10000, gridService.generateShotsToGridRandomly(10000).size());
	}
	
	@Test
	public void emptyListShouldBeGenerated(){
		Assert.assertEquals("0 No. of Shots should be generated for negative input..", 0 , gridService.generateShotsToGridRandomly(0).size());
	}
	
	@Test
	public void checkForRangeOfShotsGenerated(){
		List<String> shots = gridService.generateShotsToGridRandomly(5);
		for(String s : shots){
			Assert.assertTrue("Row index should be lying in 0 to f range", allowedGridIndex.contains(s.charAt(0)));
			Assert.assertTrue("Col index should be lying in 0 to f range", allowedGridIndex.contains(s.charAt(1)));
		}
	}
	
	@After 
	public void method() {
		allowedGridIndex = null;
	}
}
