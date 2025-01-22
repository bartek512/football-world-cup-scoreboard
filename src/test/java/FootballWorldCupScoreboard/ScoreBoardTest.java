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

    private static final String GAME_EXISTS_ERROR = "A game between these teams already exists.";

    private static final String TEAM_BUSY_ERROR = "%s team is already playing another game.";

    private static final String TEAM_NAME_ERROR = "Team names should contain only letters (a-z, A-Z).";

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
}
