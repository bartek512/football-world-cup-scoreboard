package FootballWorldCupScoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest {

    private Game game;

    private static final String HOME_TEAM_ERROR_MESSAGE = "Home team cannot be null or empty.";

    private static final String AWAY_TEAM_ERROR_MESSAGE = "Away team cannot be null or empty.";

    private static final String SAME_TEAMS_ERROR_MESSAGE = "Home team and away team cannot be the same.";

    private static final String TOO_LONG_TEAM_NAME = "Team name cannot be longer than 35 characters.";

    private static final String POLAND = "Poland";

    private static final String SPAIN = "Spain";

    @Test
    void shouldStartGameWithInitialScoreZero() {
        //given
        // New Game object
        Game game = new Game(POLAND, SPAIN);

        //when
        // Call startGame method
        game.startGame();

        //then
        // Check if score is set to 0-0
        assertEquals(0, game.getHomeScore());
        assertEquals(0, game.getAwayScore());
    }

    @Test
    void shouldThrowExceptionWhenHomeTeamIsNull() {
        //when
        // Create wrong Game object
        // Should throw IllegalArgumentException with correct message
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Game(null, SPAIN) // Wyjątek rzucany w konstruktorze
        );

        assertEquals(HOME_TEAM_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAwayTeamIsNull() {
        //when
        // Create wrong Game object
        // Should throw IllegalArgumentException with correct message
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Game(POLAND, null)
        );

        assertEquals(AWAY_TEAM_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenAwayTeamIsEmpty() {
        //when
        // Create wrong Game object
        // Should throw IllegalArgumentException with correct message
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Game(POLAND, "")
        );

        assertEquals(AWAY_TEAM_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenHomeTeamIsEmpty() {
        //when
        // Create wrong Game object
        // Should throw IllegalArgumentException with correct message
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Game("", SPAIN)
        );

        assertEquals(HOME_TEAM_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExcepetionWhenBothTeamsAreNull() {
        //when
        // Create wrong Game object
        // Should throw IllegalArgumentException with correct message
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Game(null, null)
        );

        assertEquals(HOME_TEAM_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExcepetionWhenBothTeamsAreEmpty() {
        //when
        // Create wrong Game object
        // Should throw IllegalArgumentException with correct message
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Game("", "")
        );

        assertEquals(HOME_TEAM_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenTeamsAreTheSame() {
        //when
        // Create wrong Game object
        // Should throw IllegalArgumentException with correct message
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Game(POLAND, POLAND)
        );

        assertEquals(SAME_TEAMS_ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenHomeTeamNameExceedsMaxLength() {
        //given
        // Too long away team name
        String longName = POLAND.repeat(6);

        //when
        // Create game with too long home team name
        //then
        // Should throw IllegalArgumentException with correct message
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Game(longName, SPAIN)
        );

        assertEquals(TOO_LONG_TEAM_NAME, exception.getMessage());
    }
}
