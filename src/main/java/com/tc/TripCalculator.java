package com.tc;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * 
 * @author shashank 
 * 
 * this class should be used to calculate the cost for a particular trip.
 */
public class TripCalculator {
	
	//trip cost per kilometer in $
	private static final double costPerKm = 0.25;
	
	//key would be location name and the value would be related JSON. this should filled with all the locations from JSON
	private static Map <String, JSONObject> locationMap = new HashMap<String, JSONObject>();

	public static void main(String[] args) {
		// initialize the class
		TripCalculator tripCalculator = new TripCalculator();
		// map should be initialize at very 1st step.
		TripCalculator.convertJsonToMap();
		//find the trip cost between provided location, this will internally calculate the distance
		tripCalculator.costOfTrip("QEW", "Highway 400");
	}
	
	/**
	 * calculate the trip cost between 2 location
	 * 
	 * @param location1
	 * @param location2
	 * @return
	 */
	public double costOfTrip(String location1, String location2) {
		System.out.println("Calculate the cost between " + location1 + " and "+ location2);
		JSONObject location1_Object = locationMap.get(location1);
		JSONObject location2_Object = locationMap.get(location2);	
		double distance = distance_Between_LatLong(location1_Object.getDouble("lat"), location1_Object.getDouble("lng"), location2_Object.getDouble("lat"), location2_Object.getDouble("lng"));
		double cost = distance*costPerKm;
		cost = new BigDecimal(cost).setScale(2, RoundingMode.HALF_UP).doubleValue();
		System.out.println("Distance :"+ new BigDecimal(distance).setScale(3, RoundingMode.HALF_UP).doubleValue() + " KM.");
		System.out.println("Cost :$"+ cost );
		return cost;
	}
	
	/**
	 * calculate the distance based on latitude and longitude
	 * 
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return
	 */
    private double distance_Between_LatLong(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371.01; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1)*Math.sin(lat2) + Math.cos(lat1)*Math.cos(lat2)*Math.cos(lon1 - lon2));
    }
    
    /**
     * initialize the location map from json file resides to resources.
     */
	public static void convertJsonToMap() {

		String resourceName = "/interchanges.json";
		
		InputStream is = TripCalculator.class.getResourceAsStream(resourceName);
		if (is == null) {
			throw new NullPointerException("Cannot find resource file " + resourceName);
		}
		
		JSONTokener tokener = new JSONTokener(is);
        JSONObject object = new JSONObject(tokener);
        JSONObject locations = object.getJSONObject("locations");
        for (int i = 1; i<= 46; i++) {
        	
        	if (!locations.has(String.valueOf(i)))continue;
        	
        	JSONObject location = locations.getJSONObject(String.valueOf(i));
        	
        	locationMap.put(location.getString("name"), location);
        }
       // System.out.println(locationMap.size());
       // System.out.println(locationMap.get("QEW"));

	}

}
