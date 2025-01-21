package FootballWorldCupScoreboard;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard {

    private List<Game> games = new ArrayList<>();

    private static final String GAME_EXISTS_ERROR = "A game between these teams already exists.";

    private static final String TEAM_BUSY_ERROR = "%s team is already playing another game.";

    private static final String TEAM_NAME_PATTERN = "[a-zA-Z]+";

    private static final String TEAM_NAME_ERROR = "Team names should contain only letters (a-z, A-Z).";

    public void startGame(String homeTeam, String awayTeam) {
        validateGameStartConditions(homeTeam, awayTeam);
        games.add(new Game(homeTeam, awayTeam));
    }

    public List<Game> getGames() {
        return games;
    }

    private void validateGameStartConditions(String homeTeam, String awayTeam) {
        if (validateIfGameExists(homeTeam, awayTeam)) {
            throw new IllegalArgumentException(GAME_EXISTS_ERROR);
        }

        if (!homeTeam.matches(TEAM_NAME_PATTERN) || !awayTeam.matches(TEAM_NAME_PATTERN)) {
            throw new IllegalArgumentException(TEAM_NAME_ERROR);
        }

        validateIfTeamIsBusy(homeTeam);
        validateIfTeamIsBusy(awayTeam);
    }

    private boolean validateIfGameExists(String homeTeam, String awayTeam) {
        return games.stream().anyMatch(game -> game.getHomeTeam().equals(homeTeam) &&
                game.getAwayTeam().equals(awayTeam)) || games.stream().anyMatch(
                game -> game.getHomeTeam().equals(awayTeam) &&
                        game.getAwayTeam().equals(homeTeam));
    }

    private void validateIfTeamIsBusy(String team) {
        if (games.stream()
                .anyMatch(game -> game.getHomeTeam().equals(team) || game.getAwayTeam().equals(team))) {
            throw new IllegalArgumentException(String.format(TEAM_BUSY_ERROR, team));
        }
    }
}
