/**
 * 
 */
package com.lunch.ui;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import com.lunch.service.LunchVenue;
import com.lunch.util.LunchUtility;

/**
 * @author Shiva
 *
 */
public class LetsDoLunch {
	
	/**
	 * @param args
	 * Main class that obtains the feasible set of locations
	 */
	public static void main(String[] args) {
		
		PriorityQueue<String> peggyLocations = new PriorityQueue<String>(); // Feasible locations for Peggy
		PriorityQueue<String> meetingLocations = new PriorityQueue<String>(); // Feasible locations for both Peggy and Sam
		Set<String> locationsToBeAvoided = new HashSet<String>(); // Locations that can be avoided once Peggy's locations are found
		
		LunchVenue lv = new LunchVenue(); // Create a new LunchVenue

		LunchUtility.readData(lv); // Read data from standard input into LunchVenue instance
		
		// Get all feasible locations for Peggy 
		peggyLocations = lv.doBFS(lv.locationMap, lv.peggyStartLocations, lv.locationsToBeAvoided, false);
		
		// Reset the labels of the Location map for another BFS search
		lv.resetColors();
		
		// Retain only locations feasible for Peggy while checking for Sam
		lv.samStartLocations.retainAll(peggyLocations);
		locationsToBeAvoided.addAll(lv.locationMap.keySet());
		locationsToBeAvoided.removeAll(peggyLocations);
		
		// Get the common set of locations in which Peggy and Sam can meet
		meetingLocations = lv.doBFS(lv.locationMap, lv.samStartLocations, locationsToBeAvoided, true);
		
		while(meetingLocations.size()>0) {
			System.out.println(meetingLocations.poll());
		}
	}	
}