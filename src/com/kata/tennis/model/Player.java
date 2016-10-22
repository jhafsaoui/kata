package com.kata.tennis.model;

import java.util.Map;

public class Player {

	private String lastName;
	private String firstName;

	private boolean advantage;

	public Player(String lastName, String firstName) {

		this.lastName = lastName;
		this.firstName = firstName;

		this.advantage = false;
	}

	public String getLastName() {
		return this.lastName;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public boolean hasAdvantage() {
		return this.advantage;
	}

	public void takeAdvantage() {
		this.advantage = true;
	}

	public void giveUpAdvantage() {
		this.advantage = false;
	}

	public void score(Map<Player, Integer> points, boolean tieBreak) {

		int previousPoint = points.get(this);

		if(!tieBreak) {
			switch (previousPoint) {
	
				case 0:
					points.put(this, 15);
					break;
	
				case 15:
					points.put(this, 30);
					break;
	
				case 30:
					points.put(this, 40);
					break;
		
				default:
					break;
			}
		} else {
			points.put(this, previousPoint + 1);
		}
	}

	@Override
	public boolean equals(Object obj) {

		if(obj instanceof Player) {
			return this.firstName.equals(((Player)obj).firstName) && this.lastName.equals(((Player)obj).lastName);
		}
		return false;
	}
}