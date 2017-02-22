package com.aditya.xlabs.TestSuit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.aditya.xlabs.GameService.impl.GameServiceImplTest;
import com.aditya.xlabs.GridService.impl.GridServiceImplTest;
import com.aditya.xlabs.PlayerService.impl.PlayerServiceImplTest;
import com.aditya.xlabs.RuleService.impl.RuleServiceImplTest;
import com.aditya.xlabs.SpaceService.impl.SpaceShipServiceImplTest;
import com.aditya.xlabs.ValidationService.impl.ValidationServiceImplTest;
import com.aditya.xlabs.XLSpaceShipInstanceService.impl.XLShipInstanceServiceImplTest;
import com.aditya.xlabs.utilities.impl.ConvertorServiceImplTest;
import com.aditya.xlabs.utilities.impl.PropertyReaderServiceImplTest;

@RunWith(Suite.class)
@SuiteClasses({ GameServiceImplTest.class,
				GridServiceImplTest.class,
				PlayerServiceImplTest.class,
				RuleServiceImplTest.class,
				SpaceShipServiceImplTest.class,
				ConvertorServiceImplTest.class,
				PropertyReaderServiceImplTest.class,
				ValidationServiceImplTest.class,
				XLShipInstanceServiceImplTest.class })

public class AllTests {

}
