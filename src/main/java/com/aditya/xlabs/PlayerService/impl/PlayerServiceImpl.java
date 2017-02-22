package com.aditya.xlabs.PlayerService.impl;

import com.aditya.xlabs.Entity.Player;
import com.aditya.xlabs.PlayerService.IPlayerService;

public class PlayerServiceImpl implements IPlayerService {

	public Player getSelfPlayer() {
		Player computerPlayer = new Player("player1","AssesmentPlayer");
		return computerPlayer;
	}

	
}
