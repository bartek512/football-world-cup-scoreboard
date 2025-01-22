package FootballWorldCupScoreboard;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ScoreBoard {

    private LinkedHashMap<String, Game> games = new LinkedHashMap<>();

    private static final String GAME_EXISTS_ERROR = "A game between these teams already exists.";

    private static final String TEAM_BUSY_ERROR = "%s team is already playing another game.";

    private static final String TEAM_NAME_PATTERN = "[a-zA-Z]+";

    private static final String TEAM_NAME_ERROR = "Team names should contain only letters (a-z, A-Z).";

    public void startGame(String homeTeam, String awayTeam) {
        String gameId = UniqueGameIdGenerator.generateUniqueGameId(homeTeam, awayTeam);
        validateGameStartConditions(homeTeam, awayTeam, gameId);
        games.put(gameId, new Game(homeTeam, awayTeam));
    }

    public LinkedHashMap<String, Game> getGames() {
        return games;
    }

    private void validateGameStartConditions(String homeTeam, String awayTeam, String gameId) {
        if (validateIfGameExists(gameId)) {
            throw new IllegalArgumentException(GAME_EXISTS_ERROR);
        }

        if (!homeTeam.matches(TEAM_NAME_PATTERN) || !awayTeam.matches(TEAM_NAME_PATTERN)) {
            throw new IllegalArgumentException(TEAM_NAME_ERROR);
        }

        validateIfTeamIsBusy(homeTeam);
        validateIfTeamIsBusy(awayTeam);
    }

    private boolean validateIfGameExists(String gameId) {
        return games.containsKey(gameId);
    }

    private void validateIfTeamIsBusy(String team) {
        if (games.values().stream()
                .anyMatch(game -> game.getHomeTeam().equalsIgnoreCase(team)
                        || game.getAwayTeam().equalsIgnoreCase(team))) {
            throw new IllegalArgumentException(String.format(TEAM_BUSY_ERROR, team));
        }
    }
}
