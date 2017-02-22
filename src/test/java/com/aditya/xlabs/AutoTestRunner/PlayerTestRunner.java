package com.aditya.xlabs.AutoTestRunner;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.aditya.xlabs.PlayerService.impl.PlayerServiceImplTest;

public class PlayerTestRunner {

	public static void main(String args[]){
		
		Result result = JUnitCore.runClasses(PlayerServiceImplTest.class);
		
		for(Failure fail : result.getFailures()){
			System.out.println(fail.toString());
		}
		System.out.println(result.getFailureCount());
	}
}
