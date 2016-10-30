/**
 * 
 */
package com.lunch.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.lunch.bean.Location;
import com.lunch.util.Color;

/**
 * @author Shiva
 * LunchVenue entity contains variables and operations that can be performed on its variables
 */
public class LunchVenue {

	public Map<String, Location> locationMap = new HashMap<String, Location>(); 
	public Set<String> locationsToBeAvoided = new HashSet<String>();
	public Set<String> peggyStartLocations = new HashSet<String>();
	public Set<String> samStartLocations = new HashSet<String>();
	public PriorityQueue<String> feasibleLocations = new PriorityQueue<String>();
	
	/**
	 * Adds a new location to the map of locations
	 * @param locationName Name of the location
	 * @param locationMap Map of location name to its Location instance
	 * @return The newly added Location
	 */
	public Location addLocation(String locationName, Map<String, Location> locationMap) {
		Location location = new Location();
		location.setName(locationName);
		location.setColor(Color.WHITE);
		locationMap.put(location.getName(), location);
		return location;
	}
	
	/**
	 * Labels all the locations with WHITE color
	 */
	public void resetColors() {
		for(Location eachLocation:locationMap.values()) {
			eachLocation.setColor(Color.WHITE);
		}
	}
	
	/**
	 * Performs Breadth First Search on the Locations with start points as StartLocations
	 * @param locationMap - Map of location name to its Location instance
	 * @param startLocations - Set of start locations
	 * @param locationsToBeAvoided - Set of locations to be avoided during the traversal
	 * @param isUpstream - Boolean value, true if the traversal is reverse/upward. False if the traversal is usual/downstream
	 * @return Priority List of locations that are reachable in the traversal
	 */
	public PriorityQueue<String> doBFS(Map<String, Location> locationMap, Set<String> startLocations, Set<String> locationsToBeAvoided, Boolean isUpstream) {
		Queue<Location> locationQueue = new LinkedList<Location>();
		PriorityQueue<String> feasibleLocations = new PriorityQueue<String>();
		for(String eachLocation : startLocations) {
			if(!locationsToBeAvoided.contains(eachLocation)) {
				// label any visited vertex being added to the queue to BLACK
				locationMap.get(eachLocation).setColor(Color.BLACK);
				locationQueue.add(locationMap.get(eachLocation));
			}
		}
		// usual BFS
		if(!isUpstream) {
			while(locationQueue.size()>0) {
				Location nextLocation = locationQueue.poll();
				feasibleLocations.add(nextLocation.getName());
				if(nextLocation.getAdjOut()!=null && nextLocation.getAdjOut().size()>0) {
					for(String eachAdjacentLocation : nextLocation.getAdjOut()) {
						// Add the location to queue only if it is not in the list of avoidable locations and hasn't been 
						// previously visited or with WHITE label
						if(!locationsToBeAvoided.contains(eachAdjacentLocation) && 
								locationMap.get(eachAdjacentLocation).getColor().equals(Color.WHITE)) {
							locationQueue.add(locationMap.get(eachAdjacentLocation));
							locationMap.get(eachAdjacentLocation).setColor(Color.BLACK);
						}
					}
				}				
			}
		}
		// BFS in opposite direction
		else {
			while(locationQueue.size()>0) {
				Location nextLocation = locationQueue.poll();
				feasibleLocations.add(nextLocation.getName());
				if(nextLocation.getAdjIn()!=null && nextLocation.getAdjIn().size()>0) {
					for(String eachAdjacentLocation : nextLocation.getAdjIn()) {
						// Add the location to queue only if it is not in the list of avoidable locations and hasn't been 
						// previously visited or with white label
						if(!locationsToBeAvoided.contains(eachAdjacentLocation) && 
								locationMap.get(eachAdjacentLocation).getColor().equals(Color.WHITE)) {
							locationQueue.add(locationMap.get(eachAdjacentLocation));
							locationMap.get(eachAdjacentLocation).setColor(Color.BLACK);
						}
					}
				}
			}
		}
		return feasibleLocations;		
	}	
}