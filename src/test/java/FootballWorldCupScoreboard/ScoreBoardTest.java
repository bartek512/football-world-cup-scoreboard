package FootballWorldCupScoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardTest {

    ScoreBoard scoreBoard;

    private static final String POLAND = "Poland";

    private static final String SPAIN = "Spain";

    private static final String ITALY = "Italy";

    private static final String FRANCE = "France";

    private static final String MEXICO = "Mexico";

    private static final String CANADA = "Canada";

    private static final String BRAZIL = "Brazil";

    private static final String GERMANY = "Germany";

    private static final String URUGUAY = "Uruguay";

    private static final String ARGENTINA = "Argentina";

    private static final String AUSTRALIA = "Australia";

    private static final String GAME_EXISTS_ERROR = "A game between these teams already exists.";

    private static final String TEAM_BUSY_ERROR = "%s team is already playing another game.";

    private static final String TEAM_NAME_ERROR = "Team names should contain only letters (a-z, A-Z).";

    private static final String GAME_NOT_FOUND_ERROR = "Game not found.";

    private static final String NEGATIVE_SCORE_ERROR_MESSAGE = "Scores cannot be negative.";

    @BeforeEach
    void setUp() {
        scoreBoard = new ScoreBoard();
    }

    @Test
    public void shouldStartGameProperly() {
        // when
        scoreBoard.startGame(POLAND, SPAIN);

        //and
        // Generate the expected game ID
        String expectedGameId = UniqueGameIdGenerator.generateUniqueGameId(POLAND, SPAIN);


        // then
        // Get the map of games from the scoreboard
        LinkedHashMap<String, Game> games = scoreBoard.getGames();
        Game game = games.get(expectedGameId);

        assertNotNull(game);
        assertEquals(1, games.size());
        assertEquals(POLAND, game.getHomeTeam());
        assertEquals(SPAIN, game.getAwayTeam());
        assertEquals(0, game.getHomeScore());
        assertEquals(0, game.getAwayScore());
    }

    @Test
    public void shouldThrowExceptionWhenTheSameGameExists() {
        // when
        // Start new game
        scoreBoard.startGame(POLAND, SPAIN);

        //and
        // Try to start game, between the same teams
        //then
        // Should exception with expected message
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scoreBoard.startGame(POLAND, SPAIN)
        );

        assertEquals(GAME_EXISTS_ERROR, exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenTheSameGameExistsButTeamsAreSwitched() {
        // when
        // Start new game
        scoreBoard.startGame(POLAND, SPAIN);

        //and
        // Try to start game, between the same teams
        //then
        // Should exception with expected message
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scoreBoard.startGame(SPAIN, POLAND)
        );

        assertEquals(GAME_EXISTS_ERROR, exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenHomeTeamIsBusy() {
        // when
        // Start new game
        scoreBoard.startGame(POLAND, SPAIN);

        //and
        // Try to start game with one busy team
        //then
        // Should exception with expected message
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scoreBoard.startGame(POLAND, ITALY)
        );

        assertEquals(String.format(TEAM_BUSY_ERROR, POLAND), exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenAwayTeamIsBusy() {
        // when
        // Start new game
        scoreBoard.startGame(POLAND, SPAIN);

        //and
        // Try to start game with one busy team
        //then
        // Should exception with expected message
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scoreBoard.startGame(ITALY, SPAIN)
        );

        assertEquals(String.format(TEAM_BUSY_ERROR, SPAIN), exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfHomeTeamContainsForbiddenCharacters() {
        // when + then
        // Use wrong charaster in team name
        // Then should thorw an exception with message
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scoreBoard.startGame("Pol@nd", ITALY)
        );

        assertEquals(TEAM_NAME_ERROR, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionIfAwayTeamContainsForbiddenCharacters() {
        // when + then
        // Use wrong charaster in team name
        // Then should thorw an exception with message
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scoreBoard.startGame(POLAND, "23544#$$$#%$)")
        );

        assertEquals(TEAM_NAME_ERROR, exception.getMessage());
    }

    @Test
    public void shouldFinishGameAndRemoveItFromScoreboard() {
        // when
        // Start new game
        scoreBoard.startGame(POLAND, SPAIN);

        // and
        // Finish game
        scoreBoard.finishGame(POLAND, SPAIN);

        // Then
        // Game should be deleted
        LinkedHashMap<String, Game> games = scoreBoard.getGames();
        assertEquals(0, games.size());
    }

    @Test
    void shouldThrowExceptionIfGameDoesNotExist() {
        // when + then
        // Should throw exception when game is not started
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> scoreBoard.finishGame(POLAND, SPAIN)
        );

        assertEquals(GAME_NOT_FOUND_ERROR, exception.getMessage());
    }

    @Test
    public void shouldFinishGameWithoutAffectingOthers() {
        // when
        // Start new games
        scoreBoard.startGame(POLAND, SPAIN);
        scoreBoard.startGame(ITALY, FRANCE);

        // and
        // Finish one game
        scoreBoard.finishGame(POLAND, SPAIN);

        // Then
        // Should finish one game properly
        LinkedHashMap<String, Game> games = scoreBoard.getGames();
        assertEquals(1, games.size());
        assertTrue(games.containsKey(UniqueGameIdGenerator.generateUniqueGameId(ITALY, FRANCE)));
    }

    @Test
    public void shouldFinishGameWithSwitchedTeams() {
        // when
        // Start a new game
        scoreBoard.startGame(POLAND, SPAIN);

        // and
        // Finish game
        scoreBoard.finishGame(SPAIN, POLAND);

        // Then
        // Should finish game properly
        LinkedHashMap<String, Game> games = scoreBoard.getGames();
        assertEquals(0, games.size());
    }

    @Test
    public void shouldUpdateScoreSuccessfully() {
        // when
        // Start new game
        scoreBoard.startGame(POLAND, SPAIN);

        // and
        // Try to update score
        scoreBoard.updateScore(POLAND, SPAIN, 3, 2);

        // Then
        Game game = scoreBoard.getGames().get(UniqueGameIdGenerator.generateUniqueGameId(POLAND, SPAIN));
        assertEquals(3, game.getHomeScore());
        assertEquals(2, game.getAwayScore());
    }

    @Test
    public void shouldThrowExceptionIfScoreIsNegative() {
        // when
        // Start new game
        scoreBoard.startGame(POLAND, SPAIN);

        // and
        // Try to update score with negative score
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                scoreBoard.updateScore(POLAND, SPAIN, -1, 2)
        );
        assertEquals(NEGATIVE_SCORE_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenGameDoesNotExist() {
        // when
        // Try to update score when Game does not exist
        // then
        // Should throw exception with correct message
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                scoreBoard.updateScore(POLAND, SPAIN, 1, 1)
        );
        assertEquals(GAME_NOT_FOUND_ERROR, exception.getMessage());
    }

    @Test
    public void shouldUpdateScoreSuccessfullyWhenTeamsAreSwitched() {
        // when
        // Start new game
        scoreBoard.startGame(POLAND, SPAIN);

        // and
        // Try to update score with switched teams
        scoreBoard.updateScore(SPAIN, POLAND, 2, 3);

        // Then
        // Should update properly
        Game game = scoreBoard.getGames().get(UniqueGameIdGenerator.generateUniqueGameId(POLAND, SPAIN));
        assertEquals(2, game.getHomeScore());
        assertEquals(3, game.getAwayScore());
    }


    @Test
    public void shouldThrowExceptionWhenGameIsFinished() {
        // when
        // Start new game
        scoreBoard.startGame(POLAND, SPAIN);

        // and
        // Finish match
        scoreBoard.finishGame(POLAND, SPAIN);

        // then
        // Should throw exception with correct message
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                scoreBoard.updateScore(POLAND, SPAIN, 1, 1)
        );
        assertEquals(GAME_NOT_FOUND_ERROR, exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionForNullTeamName() {
        // when
        // Try to update score when one of the team is null
        // then
        // Shouldn't find this game and should throw exception
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                scoreBoard.updateScore(null, SPAIN, 1, 1)
        );
        assertEquals(GAME_NOT_FOUND_ERROR, exception.getMessage());
    }

    @Test
    void shouldReturnEmptyListWhenNoGamesExist() {
        // when
        List<Game> summary = scoreBoard.getSummaryByTotalScore();

        // then
        assertNotNull(summary);
        assertTrue(summary.isEmpty());
    }

    @Test
    public void shouldReturnSummarySortedByTotalScore() {
        // given
        // Started games with updated scores
        scoreBoard.startGame(MEXICO, CANADA);
        scoreBoard.updateScore(MEXICO, CANADA, 0, 5);

        scoreBoard.startGame(SPAIN, BRAZIL);
        scoreBoard.updateScore(SPAIN, BRAZIL, 10, 2);

        scoreBoard.startGame(URUGUAY, ITALY);
        scoreBoard.updateScore(URUGUAY, ITALY, 6, 6);

        scoreBoard.startGame(ARGENTINA, AUSTRALIA);
        scoreBoard.updateScore(ARGENTINA, AUSTRALIA, 3, 1);

        scoreBoard.startGame(GERMANY, FRANCE);
        scoreBoard.updateScore(GERMANY, FRANCE, 2, 2);

        // when
        // Get score board witch summary
        List<Game> summary = scoreBoard.getSummaryByTotalScore();

        // then
        // All games should be sorted properly
        assertEquals(5, summary.size());
        assertEquals(SPAIN, summary.get(0).getHomeTeam());
        assertEquals(BRAZIL, summary.get(0).getAwayTeam());
        assertEquals(10, summary.get(0).getHomeScore());
        assertEquals(2, summary.get(0).getAwayScore());

        assertEquals(URUGUAY, summary.get(1).getHomeTeam());
        assertEquals(ITALY, summary.get(1).getAwayTeam());
        assertEquals(6, summary.get(1).getHomeScore());
        assertEquals(6, summary.get(1).getAwayScore());

        assertEquals(MEXICO, summary.get(2).getHomeTeam());
        assertEquals(CANADA, summary.get(2).getAwayTeam());
        assertEquals(0, summary.get(2).getHomeScore());
        assertEquals(5, summary.get(2).getAwayScore());

        assertEquals(ARGENTINA, summary.get(3).getHomeTeam());
        assertEquals(AUSTRALIA, summary.get(3).getAwayTeam());
        assertEquals(3, summary.get(3).getHomeScore());
        assertEquals(1, summary.get(3).getAwayScore());

        assertEquals(GERMANY, summary.get(4).getHomeTeam());
        assertEquals(FRANCE, summary.get(4).getAwayTeam());
        assertEquals(2, summary.get(4).getHomeScore());
        assertEquals(2, summary.get(4).getAwayScore());
    }

    @Test
    public void shouldReturnSummarySortedByTotalScoreWhenStartingGamesIsMixed() {
        // given
        // Started games with updated scores
        scoreBoard.startGame(GERMANY, FRANCE);
        scoreBoard.updateScore(GERMANY, FRANCE, 2, 2);

        scoreBoard.startGame(MEXICO, CANADA);
        scoreBoard.updateScore(MEXICO, CANADA, 0, 5);

        scoreBoard.startGame(URUGUAY, ITALY);
        scoreBoard.updateScore(URUGUAY, ITALY, 6, 6);

        scoreBoard.startGame(ARGENTINA, AUSTRALIA);
        scoreBoard.updateScore(ARGENTINA, AUSTRALIA, 3, 1);

        scoreBoard.startGame(SPAIN, BRAZIL);
        scoreBoard.updateScore(SPAIN, BRAZIL, 10, 2);

        // when
        // Get score board witch summary
        List<Game> summary = scoreBoard.getSummaryByTotalScore();

        // then
        // All games should be sorted properly
        assertEquals(5, summary.size());
        assertEquals(URUGUAY, summary.get(0).getHomeTeam());
        assertEquals(ITALY, summary.get(0).getAwayTeam());
        assertEquals(6, summary.get(0).getHomeScore());
        assertEquals(6, summary.get(0).getAwayScore());

        assertEquals(SPAIN, summary.get(1).getHomeTeam());
        assertEquals(BRAZIL, summary.get(1).getAwayTeam());
        assertEquals(10, summary.get(1).getHomeScore());
        assertEquals(2, summary.get(1).getAwayScore());

        assertEquals(MEXICO, summary.get(2).getHomeTeam());
        assertEquals(CANADA, summary.get(2).getAwayTeam());
        assertEquals(0, summary.get(2).getHomeScore());
        assertEquals(5, summary.get(2).getAwayScore());

        assertEquals(GERMANY, summary.get(3).getHomeTeam());
        assertEquals(FRANCE, summary.get(3).getAwayTeam());
        assertEquals(2, summary.get(3).getHomeScore());
        assertEquals(2, summary.get(3).getAwayScore());

        assertEquals(ARGENTINA, summary.get(4).getHomeTeam());
        assertEquals(AUSTRALIA, summary.get(4).getAwayTeam());
        assertEquals(3, summary.get(4).getHomeScore());
        assertEquals(1, summary.get(4).getAwayScore());
    }
}
