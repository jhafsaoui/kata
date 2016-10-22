package com.kata.tennis.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.kata.tennis.exception.NotAllowedActionException;
import com.kata.tennis.model.Game;
import com.kata.tennis.model.Player;
import com.kata.tennis.model.Set;

public class TennisSetTest {

	@Test
	public void testNewSetReturnsZeroAll() {

		Set tennisSet = new Set(new Player("SAMPRAS", "Pete"), new Player("AGASSI", "Andre"));

		assertEquals("GAMES : SAMPRAS Pete 0 | AGASSI Andre 0", tennisSet.getScore());
	}

	@Test
	public void testNewGameReturnsZeroAll() throws NotAllowedActionException {

		Set tennisSet = new Set(new Player("SAMPRAS", "Pete"), new Player("AGASSI", "Andre"));

		tennisSet.playNewGame();

		assertEquals("POINTS : SAMPRAS Pete 0 | AGASSI Andre 0", tennisSet.getOngoingGame().getScore());
	}

	@Test
	public void testPlayerOneScoresFifteen() throws NotAllowedActionException {

		Set tennisSet = new Set(new Player("SAMPRAS", "Pete"), new Player("AGASSI", "Andre"));

		tennisSet.playNewGame();
		Game ongoingGame = tennisSet.getOngoingGame();

		ongoingGame.playerOneScores();

		assertEquals("POINTS : SAMPRAS Pete 15 | AGASSI Andre 0", ongoingGame.getScore());
	}

	@Test
	public void testPlayerOneScoresFifteenAndPlayerTwoScoresThirty() throws NotAllowedActionException {

		Set tennisSet = new Set(new Player("SAMPRAS", "Pete"), new Player("AGASSI", "Andre"));

		tennisSet.playNewGame();
		Game ongoingGame = tennisSet.getOngoingGame();

		ongoingGame.playerTwoScores();

		assertEquals("POINTS : SAMPRAS Pete 0 | AGASSI Andre 15", ongoingGame.getScore());

		ongoingGame.playerOneScores();

		assertEquals("POINTS : SAMPRAS Pete 15 | AGASSI Andre 15", ongoingGame.getScore());

		ongoingGame.playerTwoScores();

		assertEquals("POINTS : SAMPRAS Pete 15 | AGASSI Andre 30", ongoingGame.getScore());
	}

	@Test
	public void testDeuce() throws NotAllowedActionException {

		Set tennisSet = new Set(new Player("SAMPRAS", "Pete"), new Player("AGASSI", "Andre"));

		tennisSet.playNewGame();
		Game ongoingGame = tennisSet.getOngoingGame();

		ongoingGame.playerOneScores();
		ongoingGame.playerTwoScores();

		ongoingGame.playerOneScores();
		ongoingGame.playerTwoScores();

		ongoingGame.playerOneScores();
		ongoingGame.playerTwoScores();

		assertEquals(true, ongoingGame.isDeuce());
	}

	@Test
	public void testPlayerOneHasAdvantage() throws NotAllowedActionException {

		Set tennisSet = new Set(new Player("SAMPRAS", "Pete"), new Player("AGASSI", "Andre"));

		tennisSet.playNewGame();
		Game ongoingGame = tennisSet.getOngoingGame();

		ongoingGame.playerOneScores();
		ongoingGame.playerTwoScores();

		ongoingGame.playerOneScores();
		ongoingGame.playerTwoScores();

		ongoingGame.playerOneScores();
		ongoingGame.playerTwoScores();

		ongoingGame.playerOneScores();
		ongoingGame.playerTwoScores();

		ongoingGame.playerOneScores();

		assertEquals(true, tennisSet.getPlayerOne().hasAdvantage());
	}

	@Test
	public void testPlayerOneGivesUpAdvantage() throws NotAllowedActionException {

		Set tennisSet = new Set(new Player("SAMPRAS", "Pete"), new Player("AGASSI", "Andre"));

		tennisSet.playNewGame();
		Game ongoingGame = tennisSet.getOngoingGame();

		ongoingGame.playerOneScores();
		ongoingGame.playerTwoScores();

		ongoingGame.playerOneScores();
		ongoingGame.playerTwoScores();

		ongoingGame.playerOneScores();
		ongoingGame.playerTwoScores();

		ongoingGame.playerOneScores();

		ongoingGame.playerTwoScores();

		assertEquals(true, ongoingGame.isDeuce());
	}

	@Test
	public void testplayerOneWinsGame() throws NotAllowedActionException {

		Set tennisSet = new Set(new Player("SAMPRAS", "Pete"), new Player("AGASSI", "Andre"));

		tennisSet.playNewGame();
		Game ongoingGame = tennisSet.getOngoingGame();

		ongoingGame.playerOneScores();
		ongoingGame.playerTwoScores();

		ongoingGame.playerOneScores();
		ongoingGame.playerTwoScores();

		ongoingGame.playerOneScores();

		ongoingGame.playerOneScores();

		assertEquals(tennisSet.getPlayerOne(), ongoingGame.getWinner());
	}

	@Test
	public void testPlayerTwoWinsGameAfterTakingAdvantage() throws NotAllowedActionException {

		Set tennisSet = new Set(new Player("SAMPRAS", "Pete"), new Player("AGASSI", "Andre"));

		tennisSet.playNewGame();
		Game ongoingGame = tennisSet.getOngoingGame();

		ongoingGame.playerOneScores();
		ongoingGame.playerTwoScores();

		ongoingGame.playerOneScores();
		ongoingGame.playerTwoScores();

		ongoingGame.playerOneScores();
		ongoingGame.playerTwoScores();

		ongoingGame.playerTwoScores();

		ongoingGame.playerTwoScores();

		assertEquals(tennisSet.getPlayerTwo(), ongoingGame.getWinner());
	}

	@Test
	public void testPlayingNewGameAllowedIfNoOngoingGame() {

		Set tennisSet = new Set(new Player("SAMPRAS", "Pete"), new Player("AGASSI", "Andre"));

		try {
			tennisSet.playNewGame();

		} catch (NotAllowedActionException e) {
			throw new AssertionError();
		}
	}

	@Test
	public void testPlayingNewGameAllowedIfOngoingGameIsOver() {

		Set tennisSet = new Set(new Player("SAMPRAS", "Pete"), new Player("AGASSI", "Andre"));

		try {
			tennisSet.playNewGame();
			this.playerOneWinsGame(tennisSet);
			tennisSet.playNewGame();

		} catch (NotAllowedActionException e) {
			throw new AssertionError();
		}
	}

	@Test
	public void testPlayingNewGameNotAllowedIfOngoingGameIsNotOverYet() {

		Set tennisSet = new Set(new Player("SAMPRAS", "Pete"), new Player("AGASSI", "Andre"));

		try {
			tennisSet.playNewGame();
			tennisSet.playNewGame();
			throw new AssertionError();

		} catch (NotAllowedActionException e) {
			assertEquals("Ongoing game is not over yet.", e.getMessage());
		}
	}

	@Test
	public void testPlayerOneLeadsTwoGamesToOne() throws NotAllowedActionException {

		Set tennisSet = new Set(new Player("SAMPRAS", "Pete"), new Player("AGASSI", "Andre"));

		tennisSet.playNewGame();
		this.playerOneWinsGame(tennisSet);
		tennisSet.playNewGame();
		this.playerTwoWinsGame(tennisSet);
		tennisSet.playNewGame();
		this.playerOneWinsGame(tennisSet);
		tennisSet.playNewGame();

		assertEquals("GAMES : SAMPRAS Pete 2 | AGASSI Andre 1", tennisSet.getScore());
	}

	@Test
	public void testPlayerTwoWinsSetSixGamesToFour() throws NotAllowedActionException {

		Set tennisSet = new Set(new Player("SAMPRAS", "Pete"), new Player("AGASSI", "Andre"));

		tennisSet.playNewGame();
		this.playerOneWinsGame(tennisSet);

		tennisSet.playNewGame();
		this.playerTwoWinsGame(tennisSet);

		tennisSet.playNewGame();
		this.playerOneWinsGame(tennisSet);

		tennisSet.playNewGame();
		this.playerTwoWinsGame(tennisSet);

		tennisSet.playNewGame();
		this.playerOneWinsGame(tennisSet);

		tennisSet.playNewGame();
		this.playerTwoWinsGame(tennisSet);

		tennisSet.playNewGame();
		this.playerOneWinsGame(tennisSet);

		tennisSet.playNewGame();
		this.playerTwoWinsGame(tennisSet);

		tennisSet.playNewGame();
		this.playerOneWinsGame(tennisSet);

		tennisSet.playNewGame();
		this.playerOneWinsGame(tennisSet);

		tennisSet.updateScore();

		assertEquals("GAMES : SAMPRAS Pete 6 | AGASSI Andre 4", tennisSet.getScore());
		assertEquals(tennisSet.getPlayerOne(), tennisSet.getWinner());
	}

	@Test
	public void testPlayerOneLeadsTieBreakFivePointsToThree() throws NotAllowedActionException {

		Set tennisSet = new Set(new Player("SAMPRAS", "Pete"), new Player("AGASSI", "Andre"));

		this.playTieBreak(tennisSet);
		Game ongoingGame = tennisSet.getOngoingGame();

		ongoingGame.playerOneScores();
		ongoingGame.playerOneScores();
		ongoingGame.playerOneScores();
		ongoingGame.playerOneScores();
		ongoingGame.playerOneScores();

		ongoingGame.playerTwoScores();
		ongoingGame.playerTwoScores();
		ongoingGame.playerTwoScores();

		assertEquals("GAMES : SAMPRAS Pete 6 | AGASSI Andre 6", tennisSet.getScore());
		assertEquals("POINTS : SAMPRAS Pete 5 | AGASSI Andre 3", tennisSet.getOngoingGame().getScore());
	}

	@Test
	public void testPlayerOneWinsSetSevenGamesToSix() throws NotAllowedActionException {

		Set tennisSet = new Set(new Player("SAMPRAS", "Pete"), new Player("AGASSI", "Andre"));

		this.playTieBreak(tennisSet);
		Game ongoingGame = tennisSet.getOngoingGame();

		ongoingGame.playerOneScores();
		ongoingGame.playerOneScores();
		ongoingGame.playerOneScores();
		ongoingGame.playerOneScores();
		ongoingGame.playerOneScores();
		ongoingGame.playerOneScores();

		ongoingGame.playerTwoScores();
		ongoingGame.playerTwoScores();
		ongoingGame.playerTwoScores();
		ongoingGame.playerTwoScores();
		ongoingGame.playerTwoScores();
		ongoingGame.playerTwoScores();

		ongoingGame.playerOneScores();
		ongoingGame.playerOneScores();

		assertEquals("POINTS : SAMPRAS Pete 8 | AGASSI Andre 6", tennisSet.getOngoingGame().getScore());

		tennisSet.updateScore();

		assertEquals("GAMES : SAMPRAS Pete 7 | AGASSI Andre 6", tennisSet.getScore());
		assertEquals(tennisSet.getPlayerOne(), tennisSet.getWinner());
	}

	@Test
	public void testPlayerTwoWinsSetSevenGamesToSix() throws NotAllowedActionException {

		Set tennisSet = new Set(new Player("SAMPRAS", "Pete"), new Player("AGASSI", "Andre"));

		this.playTieBreak(tennisSet);
		Game ongoingGame = tennisSet.getOngoingGame();

		ongoingGame.playerOneScores();
		ongoingGame.playerOneScores();
		ongoingGame.playerOneScores();
		ongoingGame.playerOneScores();
		ongoingGame.playerOneScores();

		ongoingGame.playerTwoScores();
		ongoingGame.playerTwoScores();
		ongoingGame.playerTwoScores();
		ongoingGame.playerTwoScores();
		ongoingGame.playerTwoScores();
		ongoingGame.playerTwoScores();
		ongoingGame.playerTwoScores();

		assertEquals("POINTS : SAMPRAS Pete 5 | AGASSI Andre 7", tennisSet.getOngoingGame().getScore());

		tennisSet.updateScore();

		assertEquals("GAMES : SAMPRAS Pete 6 | AGASSI Andre 7", tennisSet.getScore());
		assertEquals(tennisSet.getPlayerTwo(), tennisSet.getWinner());
	}

	@Test
	public void testPlayingNewGameNotAllowedIfOngoingSetIsOver() {

		Set tennisSet = new Set(new Player("SAMPRAS", "Pete"), new Player("AGASSI", "Andre"));

		try {
			tennisSet.playNewGame();
			this.playerOneWinsGame(tennisSet);

			tennisSet.playNewGame();
			this.playerOneWinsGame(tennisSet);

			tennisSet.playNewGame();
			this.playerOneWinsGame(tennisSet);

			tennisSet.playNewGame();
			this.playerOneWinsGame(tennisSet);

			tennisSet.playNewGame();
			this.playerOneWinsGame(tennisSet);

			tennisSet.playNewGame();
			this.playerOneWinsGame(tennisSet);

			tennisSet.playNewGame();
			throw new AssertionError();

		} catch (NotAllowedActionException e) {
			assertEquals("GAMES : SAMPRAS Pete 6 | AGASSI Andre 0", tennisSet.getScore());
			assertEquals(tennisSet.getPlayerOne(), tennisSet.getWinner());
			assertEquals("Set is over and won by : Pete SAMPRAS.", e.getMessage());
		}
	}

	private void playerOneWinsGame(Set tennisSet) throws NotAllowedActionException {

		Game ongoingGame = tennisSet.getOngoingGame();

		ongoingGame.playerOneScores();
		ongoingGame.playerOneScores();
		ongoingGame.playerOneScores();
		ongoingGame.playerOneScores();
	}

	private void playerTwoWinsGame(Set tennisSet) throws NotAllowedActionException {

		Game ongoingGame = tennisSet.getOngoingGame();

		ongoingGame.playerTwoScores();
		ongoingGame.playerTwoScores();
		ongoingGame.playerTwoScores();
		ongoingGame.playerTwoScores();
	}

	private void playTieBreak(Set tennisSet) throws NotAllowedActionException {

		tennisSet.playNewGame();
		this.playerOneWinsGame(tennisSet);

		tennisSet.playNewGame();
		this.playerTwoWinsGame(tennisSet);

		tennisSet.playNewGame();
		this.playerOneWinsGame(tennisSet);

		tennisSet.playNewGame();
		this.playerTwoWinsGame(tennisSet);

		tennisSet.playNewGame();
		this.playerOneWinsGame(tennisSet);

		tennisSet.playNewGame();
		this.playerTwoWinsGame(tennisSet);

		tennisSet.playNewGame();
		this.playerOneWinsGame(tennisSet);

		tennisSet.playNewGame();
		this.playerTwoWinsGame(tennisSet);

		tennisSet.playNewGame();
		this.playerOneWinsGame(tennisSet);

		tennisSet.playNewGame();
		this.playerTwoWinsGame(tennisSet);

		tennisSet.playNewGame();
		this.playerOneWinsGame(tennisSet);

		tennisSet.playNewGame();
		this.playerTwoWinsGame(tennisSet);

		tennisSet.playNewGame();
	}
}