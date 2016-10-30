/**
 * 
 */
package com.lunch.bean;

import java.util.Set;

import com.lunch.util.Color;

/**
 * @author Shiva
 * Location class - For each location in the map, an instance of location is created
 */
public class Location {
	private String name;			// Name of the location
	private Set<String> adjOut;		// Set of adjacent locations that have an edge from location in Map
	private Set<String> adjIn;		// Set of adjacent locations that have an edge into location in Map
	private Color color;			// color is used to label the Locations as they are visited
	
	public Location() {
		super();
	}
	
	public Location(String name, Set<String> adjIn, Set<String> adjOut) {
		this.name = name;
		this.adjIn = adjIn;
		this.adjOut = adjOut;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<String> getAdjOut() {
		return adjOut;
	}
	public void setAdjOut(Set<String> adjOut) {
		this.adjOut = adjOut;
	}
	public Set<String> getAdjIn() {
		return adjIn;
	}
	public void setAdjIn(Set<String> adjIn) {
		this.adjIn = adjIn;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}	
}