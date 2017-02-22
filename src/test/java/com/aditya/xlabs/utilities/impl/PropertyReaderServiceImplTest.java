package com.aditya.xlabs.utilities.impl;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.junit.Test;

import com.aditya.xlabs.utilities.IPropertyReaderService;

public class PropertyReaderServiceImplTest {

	String result = "";
	InputStream inputStream;
	
	IPropertyReaderService propReader = new PropertyReaderServiceImpl();
	
	@Test
	public void testPositive_readPropertyKey() throws IOException {
		String readValue = propReader.readPropertyKey("max.Capacity.ToBear.Shots.By.AngleShip");
		Assert.assertEquals("Value should be read properly..", "2", readValue);
	}
	
	@Test
	public void testNull_readPropertyKey() throws IOException {
		String readValue = propReader.readPropertyKey(null);
		Assert.assertEquals("Blank Value shud be returned..", "", readValue);
	}
	
	@Test
	public void testEmptyKey_readPropertyKey() throws IOException {
		String readValue = propReader.readPropertyKey("");
		Assert.assertEquals("Blank Value shud be returned..", "", readValue);
	}
	
	@Test
	public void testNotFoundKey_readPropertyKey() throws IOException {
		String readValue = propReader.readPropertyKey("hariBol");
		Assert.assertEquals("Blank Value shud be returned..", "", readValue);
	}
	
}

