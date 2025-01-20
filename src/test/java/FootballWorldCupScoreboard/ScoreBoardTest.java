package FootballWorldCupScoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScoreBoardTest {

    ScoreBoard scoreBoard;

    private static final String POLAND = "Poland";

    private static final String SPAIN = "Spain";

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
}
