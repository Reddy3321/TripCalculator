package com.tc;

import org.junit.Before;
import org.junit.Test;

import org.junit.Assert;

/**
 * 
 * @author shashank 
 * 
 * Junit class for TripCalculator
 */

public class TripCalculatorTest {
	
	private final TripCalculator tripCalculator = new TripCalculator();
	
	
	@Before
	public void initMap() {
		TripCalculator.convertJsonToMap();
    }
	
	@Test
	public void testCostOfTrip() {
		Assert.assertTrue(tripCalculator.costOfTrip("QEW", "Highway 400") == 13.73);
	}

}
