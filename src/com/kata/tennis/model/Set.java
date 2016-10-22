package com.kata.tennis.model;

import java.util.HashMap;
import java.util.Map;

import com.kata.tennis.exception.NotAllowedActionException;

public class Set {

	private Player playerOne;
	private Player playerTwo;
	private Player winner;

	private Map<Player, Integer> games;

	private Game ongoingGame;

	private boolean over;

	public Set(Player playerOne, Player playerTwo) {

		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
		this.winner = null;

		this.games = new HashMap<>();
		this.games.put(this.playerOne, 0);
		this.games.put(this.playerTwo, 0);

		this.ongoingGame = null;

		this.over = false;
	}

	public Player getPlayerOne() {
		return this.playerOne;
	}

	public Player getPlayerTwo() {
		return this.playerTwo;
	}

	public Player getWinner() {
		return this.winner;
	}

	public void checkSetOver() {

		if(!this.over) {

			int numberOfGamesWonByPlayerOne = this.games.get(this.playerOne);
			int numberOfGamesWonByplayerTwo = this.games.get(this.playerTwo);
		
			if(numberOfGamesWonByPlayerOne == 6 && numberOfGamesWonByplayerTwo <= 4 || numberOfGamesWonByPlayerOne == 7 && numberOfGamesWonByplayerTwo <= 6) {
				this.endSet(this.playerOne);
				this.over = true;
			}
		
			if(numberOfGamesWonByPlayerOne <=4 && numberOfGamesWonByplayerTwo == 6 || numberOfGamesWonByPlayerOne <= 6 && numberOfGamesWonByplayerTwo == 7) {
				this.endSet(this.playerTwo);
				this.over = true;
			}
		}
	}

	public Game getOngoingGame() {
		return this.ongoingGame;
	}

	public void playNewGame() throws NotAllowedActionException {

		if(this.ongoingGame != null) {

			if(this.ongoingGame.isOver()) {

				this.updateScore();

				if(this.over) {
					this.endSet(this.playerOne);
					throw new NotAllowedActionException("Set is over and won by : "+this.winner.getFirstName()+" "+this.winner.getLastName()+".");

				} else if(this.isTieBreak()) {
					//Tie break parameter for the game i set to true
					this.ongoingGame = new Game(this, true);

				} else {
					this.ongoingGame = new Game(this, false);
				}

			} else {
				throw new NotAllowedActionException("Ongoing game is not over yet.");
			}

		} else {
			this.ongoingGame = new Game(this, false);
		}
	}

	public void updateScore() {

		if(this.ongoingGame.isOver()) {
			Player gameWinner = this.ongoingGame.getWinner();
	
			//Games score is updated
			if(this.playerOne.equals(gameWinner)) {
				this.games.put(this.playerOne, this.games.get(this.playerOne) + 1);
	
			} else if(this.playerTwo.equals(gameWinner)) {
				this.games.put(this.playerTwo, this.games.get(this.playerTwo) + 1);
			}
			this.ongoingGame = null;
			this.checkSetOver();
		}
	}

	public String getScore() {
		StringBuilder builder = new StringBuilder("GAMES : ")
									.append(this.playerOne.getLastName())
									.append(" ")
									.append(this.playerOne.getFirstName())
									.append(" ")
									.append(this.games.get(this.playerOne))
									.append(" | ")
									.append(this.playerTwo.getLastName())
									.append(" ")
									.append(this.playerTwo.getFirstName())
									.append(" ")
									.append(this.games.get(this.playerTwo));
		return builder.toString();
	}

	private boolean isTieBreak() {

		int numberOfGamesWonByPlayerOne = this.games.get(this.playerOne);
		int numberOfGamesWonByplayerTwo = this.games.get(this.playerTwo);

		if(numberOfGamesWonByPlayerOne == 6 && numberOfGamesWonByplayerTwo == 6) {
			return true;
		}
		return false;
	}

	private void endSet(Player winner) {
		this.winner = winner;
		this.over = true;
	}
}