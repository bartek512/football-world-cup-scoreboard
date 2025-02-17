package FootballWorldCupScoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ScoreBoardTest {

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

    private ScoreBoard scoreBoard;

    @BeforeEach
    void setUp() {
        scoreBoard = new ScoreBoard();
    }

    @Test
    public void shouldStartGameProperly() {
        // when
        scoreBoard.startGame(POLAND, SPAIN);

        // and
        // Generate the expected game ID
        final String expectedGameId = UniqueGameIdGenerator.generateUniqueGameId(POLAND, SPAIN);


        // then
        // Get the map of games from the scoreboard
        final LinkedHashMap<String, Game> games = scoreBoard.getGames();
        final Game game = games.get(expectedGameId);

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

        // and
        // Try to start game, between the same teams
        // then
        // Should exception with expected message
        final IllegalArgumentException exception = assertThrows(
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

        // and
        // Try to start game, between the same teams
        // then
        // Should exception with expected message
        final IllegalArgumentException exception = assertThrows(
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

        // and
        // Try to start game with one busy team
        // then
        // Should exception with expected message
        final IllegalArgumentException exception = assertThrows(
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

        // and
        // Try to start game with one busy team
        // then
        // Should exception with expected message
        final IllegalArgumentException exception = assertThrows(
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
        final IllegalArgumentException exception = assertThrows(
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
        final IllegalArgumentException exception = assertThrows(
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

        // then
        // Game should be deleted
        final LinkedHashMap<String, Game> games = scoreBoard.getGames();
        assertEquals(0, games.size());
    }

    @Test
    void shouldThrowExceptionIfGameDoesNotExist() {
        // when + then
        // Should throw exception when game is not started
        final IllegalArgumentException exception = assertThrows(
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

        // then
        // Should finish one game properly
        final LinkedHashMap<String, Game> games = scoreBoard.getGames();
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
        final LinkedHashMap<String, Game> games = scoreBoard.getGames();
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

        // then
        final Game game = scoreBoard.getGames().get(UniqueGameIdGenerator.generateUniqueGameId(POLAND, SPAIN));
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
        final Exception exception = assertThrows(IllegalArgumentException.class, () ->
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
        final Exception exception = assertThrows(IllegalArgumentException.class, () ->
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

        // then
        // Should update properly
        final Game game = scoreBoard.getGames().get(UniqueGameIdGenerator.generateUniqueGameId(POLAND, SPAIN));
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
        final Exception exception = assertThrows(IllegalArgumentException.class, () ->
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
        final Exception exception = assertThrows(IllegalArgumentException.class, () ->
                scoreBoard.updateScore(null, SPAIN, 1, 1)
        );
        assertEquals(GAME_NOT_FOUND_ERROR, exception.getMessage());
    }

    @Test
    void shouldReturnEmptyListWhenNoGamesExist() {
        // when
        final List<Game> summary = scoreBoard.getSummaryByTotalScore();

        // then
        assertNotNull(summary);
        assertTrue(summary.isEmpty());
    }

    @Test
    public void shouldReturnSingleGameWhenOnlyOneGameExists() {
        // given
        // Started game with updated score
        scoreBoard.startGame(POLAND, SPAIN);
        scoreBoard.updateScore(POLAND, SPAIN, 2, 1);

        // when
        // Get score board witch summary
        final List<Game> summary = scoreBoard.getSummaryByTotalScore();

        // then
        // Should return only one game
        assertEquals(1, summary.size());
        assertEquals(POLAND, summary.get(0).getHomeTeam());
        assertEquals(SPAIN, summary.get(0).getAwayTeam());
    }

    @Test
    public void shouldReturnGamesInReversedInsertionOrderWhenScoresAreEqual() {
        // given
        // Started game with updated score
        scoreBoard.startGame(POLAND, SPAIN); // 2+1=3
        scoreBoard.updateScore(POLAND, SPAIN, 2, 1);

        scoreBoard.startGame(GERMANY, FRANCE); // 1+2=3
        scoreBoard.updateScore(GERMANY, FRANCE, 1, 2);

        // when
        // Get score board witch summary
        final List<Game> summary = scoreBoard.getSummaryByTotalScore();

        // then
        // All games should be returned using reversed adding order
        assertEquals(2, summary.size());
        assertEquals(GERMANY, summary.get(0).getHomeTeam());
        assertEquals(FRANCE, summary.get(0).getAwayTeam());
        assertEquals(POLAND, summary.get(1).getHomeTeam());
        assertEquals(SPAIN, summary.get(1).getAwayTeam());
    }

    @Test
    public void shouldReturnGamesInInsertionOrderWhenAllScoresAreZero() {
        // given
        // Started games
        scoreBoard.startGame(POLAND, SPAIN);
        scoreBoard.startGame(GERMANY, FRANCE);
        scoreBoard.startGame(ARGENTINA, ITALY);

        // when
        // Get score board witch summary
        final List<Game> summary = scoreBoard.getSummaryByTotalScore();

        // then
        // All games should be returned using adding order
        assertEquals(3, summary.size());

        // First game
        assertEquals(ARGENTINA, summary.get(0).getHomeTeam());
        assertEquals(ITALY, summary.get(0).getAwayTeam());
        assertEquals(0, summary.get(0).getHomeScore());
        assertEquals(0, summary.get(0).getAwayScore());

        // Second game
        assertEquals(GERMANY, summary.get(1).getHomeTeam());
        assertEquals(FRANCE, summary.get(1).getAwayTeam());
        assertEquals(0, summary.get(1).getHomeScore());
        assertEquals(0, summary.get(1).getAwayScore());

        // Third game
        assertEquals(POLAND, summary.get(2).getHomeTeam());
        assertEquals(SPAIN, summary.get(2).getAwayTeam());
        assertEquals(0, summary.get(2).getHomeScore());
        assertEquals(0, summary.get(2).getAwayScore());
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
        final List<Game> summary = scoreBoard.getSummaryByTotalScore();

        // then
        // All games should be sorted properly
        assertEquals(5, summary.size());

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
        final List<Game> summary = scoreBoard.getSummaryByTotalScore();

        // then
        // All games should be sorted properly
        assertEquals(5, summary.size());

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
    public void shouldReturnSummarySortedByTotalScoreWhenOneMatchIsFinished() {
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
        // Finish one match
        scoreBoard.finishGame(SPAIN, BRAZIL);

        // and
        // Get score board witch summary
        final List<Game> summary = scoreBoard.getSummaryByTotalScore();

        // then
        // All games should be sorted properly
        assertEquals(4, summary.size());

        assertEquals(URUGUAY, summary.get(0).getHomeTeam());
        assertEquals(ITALY, summary.get(0).getAwayTeam());
        assertEquals(6, summary.get(0).getHomeScore());
        assertEquals(6, summary.get(0).getAwayScore());

        assertEquals(MEXICO, summary.get(1).getHomeTeam());
        assertEquals(CANADA, summary.get(1).getAwayTeam());
        assertEquals(0, summary.get(1).getHomeScore());
        assertEquals(5, summary.get(1).getAwayScore());

        assertEquals(ARGENTINA, summary.get(2).getHomeTeam());
        assertEquals(AUSTRALIA, summary.get(2).getAwayTeam());
        assertEquals(3, summary.get(2).getHomeScore());
        assertEquals(1, summary.get(2).getAwayScore());

        assertEquals(GERMANY, summary.get(3).getHomeTeam());
        assertEquals(FRANCE, summary.get(3).getAwayTeam());
        assertEquals(2, summary.get(3).getHomeScore());
        assertEquals(2, summary.get(3).getAwayScore());
    }
}
