package FootballWorldCupScoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    private Game game;

    @BeforeEach
    void setup() {
        //Create new game, before each test
        game = new Game("Poland", "Spain");
    }

    @Test
    void shouldStartGameWithInitialScoreZero() {
        //when
        // Call startGame method
        game.startGame();

        //then
        // Check if score is set to 0-0
        assertEquals(0, game.getHomeScore());
        assertEquals(0, game.getAwayScore());
    }
}
