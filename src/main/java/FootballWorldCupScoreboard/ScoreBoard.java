package FootballWorldCupScoreboard;

import java.util.*;

public class ScoreBoard {

    private LinkedHashMap<String, Game> games = new LinkedHashMap<>();

    private static final String GAME_EXISTS_ERROR = "A game between these teams already exists.";

    private static final String TEAM_BUSY_ERROR = "%s team is already playing another game.";

    private static final String TEAM_NAME_PATTERN = "[a-zA-Z]+";

    private static final String TEAM_NAME_ERROR = "Team names should contain only letters (a-z, A-Z).";

    private static final String GAME_NOT_FOUND_ERROR = "Game not found.";

    public void startGame(String homeTeam, String awayTeam) {
        String gameId = UniqueGameIdGenerator.generateUniqueGameId(homeTeam, awayTeam);
        validateGameStartConditions(homeTeam, awayTeam, gameId);
        games.put(gameId, new Game(homeTeam, awayTeam));
    }

    public void finishGame(String homeTeam, String awayTeam) {
        String gameId = UniqueGameIdGenerator.generateUniqueGameId(homeTeam, awayTeam);

        if (!games.containsKey(gameId)) {
            throw new IllegalArgumentException(GAME_NOT_FOUND_ERROR);
        }

        games.remove(gameId);
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        String gameId = UniqueGameIdGenerator.generateUniqueGameId(homeTeam, awayTeam);

        Game game = games.get(gameId);
        if (game == null) {
            throw new IllegalArgumentException(GAME_NOT_FOUND_ERROR);
        }

        game.updateScore(homeScore, awayScore);
    }

    public List<Game> getSummaryByTotalScore() {
        return games.values().stream().sorted(Comparator.comparingInt((Game game) ->
                        game.getAwayScore() + game.getHomeScore())
                .reversed()).toList();
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
