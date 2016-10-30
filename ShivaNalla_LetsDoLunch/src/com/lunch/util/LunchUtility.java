/**
 * 
 */
package com.lunch.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import com.lunch.bean.Location;
import com.lunch.service.LunchVenue;

/**
 * @author Shiva
 * Contains utility methods that perform i/o operations or auxiliary functions 
 */
public class LunchUtility {
	
	/**
	 * Reads data from standard input into the LunchVenue instance 
	 * @param lv LunchVenue
	 * @return LunchVenue instance
	 */
	public static LunchVenue readData(LunchVenue lv) {
		
		// Declare and Initialize BufferedReader with the Standard input 
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String eachLine;
		
		eachLine = getNextLine(br);
		if(eachLine!=null && eachLine.equals("Map:")) {
			//Read Map data as long as Avoid: is encountered 
			while((eachLine=getNextLine(br))!=null && !eachLine.equals("Avoid:")) {
				String locations[] = eachLine.trim().split(" ");
				
				Location location1;
				Location location2;
				Set<String> tempAdjLocations;
				
				// Create a new location if one doesn't exist
				location1 = lv.locationMap.get(locations[0]);
				if(location1 == null) {
					location1 = lv.addLocation(locations[0], lv.locationMap);
				}
				location2 = lv.locationMap.get(locations[1]);
				if(location2 == null) {
					location2 = lv.addLocation(locations[1], lv.locationMap);
				}
				
				// Update the adjacent locations, both in and out
				tempAdjLocations = location1.getAdjOut();
				if(location1.getAdjOut() == null) {
					tempAdjLocations = new HashSet<String>();
				}
				tempAdjLocations.add(location2.getName());
				location1.setAdjOut(tempAdjLocations);
				
				tempAdjLocations = location2.getAdjIn();
				if(location2.getAdjIn() == null) {
					tempAdjLocations = new HashSet<String>();
				}
				tempAdjLocations.add(location1.getName());
				location2.setAdjIn(tempAdjLocations);
			}
		}
		// This line contains all the locations that are to be avoided
		eachLine = getNextLine(br);
		String [] avoidLocationStrings = eachLine.trim().split(" ");
		if(avoidLocationStrings!=null && avoidLocationStrings.length>0) {
			for(String eachString : avoidLocationStrings) {
				if(lv.locationMap.get(eachString)!=null) {
					lv.locationsToBeAvoided.add(eachString);
				}
			}
		}
		//Get Peggy Start Locations
		eachLine = getNextLine(br);
		if(eachLine.equals("Peggy:")) {
			eachLine = getNextLine(br);
			String [] peggyStartLocationStrings = eachLine.trim().split(" ");
			if(peggyStartLocationStrings!=null && peggyStartLocationStrings.length>0) {
				for(String eachString : peggyStartLocationStrings) {
					if(lv.locationMap.get(eachString)!=null) {
						lv.peggyStartLocations.add(eachString);
					}
				}
			}			
		}
		//get Sam Start locations
		eachLine = getNextLine(br);
		if(eachLine.equals("Sam:")) {
			eachLine = getNextLine(br);
			String [] samStartLocationStrings = eachLine.trim().split(" ");
			if(samStartLocationStrings!=null && samStartLocationStrings.length>0) {
				for(String eachString : samStartLocationStrings) {
					if(lv.locationMap.get(eachString)!=null) {						
						lv.samStartLocations.add(eachString);
					}
				}
			}			
		}
		if(br!=null)
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		return lv;
	}
	
	/**
	 * Returns the next available line from the standard input associated with the BufferedReader instance
	 * @param br
	 * @return String next available line
	 */
	public static String getNextLine(BufferedReader br) {
		
		String nextLine = null;
		try {
			if(br!=null && (nextLine=br.readLine())!=null) {
				return nextLine;
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}	
}