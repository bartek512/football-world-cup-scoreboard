package FootballWorldCupScoreboard;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {

    private List<Game> games = new ArrayList<>();

    private static final String GAME_EXISTS_ERROR = "A game between these teams already exists.";

    private static final String TEAM_BUSY_ERROR = "%s team is already playing another game.";

    public void startGame(String homeTeam, String awayTeam) {
        if (validateIfGameExists(homeTeam, awayTeam)) {
            throw new IllegalArgumentException(GAME_EXISTS_ERROR);
        }

        if (isTeamBusy(homeTeam)) {
            throw new IllegalArgumentException(String.format(TEAM_BUSY_ERROR, homeTeam));
        }

        if (isTeamBusy(awayTeam)) {
            throw new IllegalArgumentException(String.format(TEAM_BUSY_ERROR, awayTeam));
        }

        games.add(new Game(homeTeam, awayTeam));
    }

    public List<Game> getGames() {
        return games;
    }

    private boolean validateIfGameExists(String homeTeam, String awayTeam) {
        return games.stream().anyMatch(game -> game.getHomeTeam().equals(homeTeam) &&
                game.getAwayTeam().equals(awayTeam)) || games.stream().anyMatch(
                game -> game.getHomeTeam().equals(awayTeam) &&
                        game.getAwayTeam().equals(homeTeam));
    }

    private boolean isTeamBusy(String team) {
        return games.stream()
                .anyMatch(game -> game.getHomeTeam().equals(team) || game.getAwayTeam().equals(team));
    }
}
