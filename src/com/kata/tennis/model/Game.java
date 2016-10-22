package com.kata.tennis.model;

import java.util.HashMap;
import java.util.Map;

public class Game {

	private Player playerOne;
	private Player playerTwo;
	private Player winner;

	private Map<Player, Integer> points;

	private boolean tieBreak;
	private boolean deuce;
	private boolean over;

	public Game(Set set, boolean tieBreak) {

		this.tieBreak = tieBreak;

		this.playerOne = set.getPlayerOne();
		this.playerTwo = set.getPlayerTwo();
		this.winner = null;

		this.deuce = false;

		this.points = new HashMap<>();
		this.points.put(this.playerOne, 0);
		this.points.put(this.playerTwo, 0);
	}

	public Player getWinner() {
		return this.winner;
	}

	public boolean isDeuce() {
		return this.deuce;
	}

	public boolean isOver() {
		return this.over;
	}

	public void playerOneScores() {

		if(!this.tieBreak) {

			if(this.playerOne.hasAdvantage()) {
				this.endGame(this.playerOne);
				return;
			}
	
			if(this.deuce) {
				this.playerOne.takeAdvantage();
				this.deuce = false;
				return;
			}
	
			if(this.playerTwo.hasAdvantage()) {
				this.playerTwo.giveUpAdvantage();
				this.deuce = true;
				return;
			}
	
			//No deuce and player one score was 40 thus game is over
			if(this.points.get(this.playerOne) == 40) {
				this.endGame(this.playerOne);
				return;
			}

			this.playerOne.score(this.points, false);
			this.checkDeuce();

		} else {
			this.playerOne.score(this.points, true);

			int numberOfPointsWonByPlayerOne = this.points.get(this.playerOne);
			int numberOfPointsWonByplayerTwo = this.points.get(this.playerTwo);

			//Having reached 7 player one must be leading by at least 2 points for the game to be over
			if(numberOfPointsWonByPlayerOne >= 7 && numberOfPointsWonByPlayerOne - numberOfPointsWonByplayerTwo >= 2) {
				this.endGame(this.playerOne);
			}
		}
	}

	public void playerTwoScores() {

		if(!this.tieBreak) {

			if(this.playerTwo.hasAdvantage()) {
				this.endGame(this.playerTwo);
				return;
			}
	
			if(this.deuce) {
				this.playerTwo.takeAdvantage();
				this.deuce = false;
				return;
			}
	
			if(this.playerOne.hasAdvantage()) {
				this.playerOne.giveUpAdvantage();
				this.deuce = true;
				return;
			}
	
			//No deuce and player two score was 40 thus game is over
			if(this.points.get(this.playerTwo) == 40) {
				this.endGame(this.playerTwo);
				return;
			}

			this.playerTwo.score(this.points, false);
			this.checkDeuce();

		} else {
			this.playerTwo.score(this.points, true);

			int numberOfPointsWonByPlayerOne = this.points.get(this.playerOne);
			int numberOfPointsWonByplayerTwo = this.points.get(this.playerTwo);

			//Having reached 7 player two must be leading by at least 2 points for the game to be over
			if(numberOfPointsWonByplayerTwo >= 7 && numberOfPointsWonByplayerTwo - numberOfPointsWonByPlayerOne >= 2) {
				this.endGame(this.playerTwo);
			}
		}
	}

	public String getScore() {
		StringBuilder builder = new StringBuilder("POINTS : ")
									.append(this.playerOne.getLastName())
									.append(" ")
									.append(this.playerOne.getFirstName())
									.append(" ")
									.append(this.points.get(this.playerOne))
									.append(" | ")
									.append(this.playerTwo.getLastName())
									.append(" ")
									.append(this.playerTwo.getFirstName())
									.append(" ")
									.append(this.points.get(this.playerTwo));
		return builder.toString();
	}

	private void endGame(Player winner) {
		this.winner = winner;
		this.over = true;
	}

	private void checkDeuce() {

		int firstPlayerScore = this.points.get(this.playerOne);
		int secondPlayerScore = this.points.get(this.playerTwo);

		if(firstPlayerScore == 40 && secondPlayerScore == 40) {
			this.deuce = true;
		}
	}
}