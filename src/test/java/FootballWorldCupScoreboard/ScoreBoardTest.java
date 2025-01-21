package FootballWorldCupScoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScoreBoardTest {

    ScoreBoard scoreBoard;

    private static final String POLAND = "Poland";

    private static final String SPAIN = "Spain";

    private static final String ITALY = "Italy";

    private static final String GAME_EXISTS_ERROR = "A game between these teams already exists.";

    private static final String TEAM_BUSY_ERROR = "%s team is already playing another game.";

    @BeforeEach
    void setUp() {
        scoreBoard = new ScoreBoard();
    }

    @Test
    public void shouldStartGameProperly() {
        // when
        scoreBoard.startGame(POLAND, SPAIN);

        // then
        List<Game> games = scoreBoard.getGames();
        assertEquals(1, games.size());
        assertEquals(POLAND, games.get(0).getHomeTeam());
        assertEquals(SPAIN, games.get(0).getAwayTeam());
        assertEquals(0, games.get(0).getHomeScore());
        assertEquals(0, games.get(0).getAwayScore());
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
}
