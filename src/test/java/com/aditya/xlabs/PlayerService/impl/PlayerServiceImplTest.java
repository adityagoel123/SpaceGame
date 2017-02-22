package com.aditya.xlabs.PlayerService.impl;

import junit.framework.Assert;

import org.junit.Test;

import com.aditya.xlabs.Entity.Player;
import com.aditya.xlabs.PlayerService.IPlayerService;
import com.aditya.xlabs.PlayerService.impl.PlayerServiceImpl;

public class PlayerServiceImplTest {

	IPlayerService playerServTester = new PlayerServiceImpl();
	
	@Test
	public void requestForPlayerShouldReturnPlayerObject(){
		Player p = new Player("player1", "AssesmentPlayer");
		Assert.assertEquals("Player Object is expected", p, playerServTester.getSelfPlayer());
	}

	@Test
	public void requestForPlayerShouldReturnNotNull(){
		Assert.assertNotNull("Player Object is expected", playerServTester.getSelfPlayer());
	}
}
