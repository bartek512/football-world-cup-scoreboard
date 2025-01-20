package FootballWorldCupScoreboard;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {

    private List<Game> games = new ArrayList<>();

    public void startGame(String homeTeam, String awayTeam) {
        games.add(new Game(homeTeam, awayTeam));
    }

    public List<Game> getGames() {
        return games;
    }
}
