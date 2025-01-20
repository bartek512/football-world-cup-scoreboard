package FootballWorldCupScoreboard;


public class Game {

    private final String homeTeam;

    private final String awayTeam;

    private int homeScore;

    private int awayScore;

    private static final String HOME_TEAM_ERROR_MESSAGE = "Home team cannot be null or empty.";

    private static final String AWAY_TEAM_ERROR_MESSAGE = "Away team cannot be null or empty.";

    private static final String SAME_TEAMS_ERROR_MESSAGE = "Home team and away team cannot be the same.";

    private static final String TOO_LONG_TEAM_NAME = "Team name cannot be longer than 35 characters.";

    private static final String NEGATIVE_SCORE = "Scores cannot be negative.";

    public Game(String homeTeam, String awayTeam) {
        validateTeams(homeTeam, awayTeam);

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
    }

    public void updateScore(int homeScore, int awayScore) {
        validateScore(homeScore, awayScore);
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    private static void validateScore(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException(NEGATIVE_SCORE);
        }
    }

    private void validateTeams(String homeTeam, String awayTeam) {
        validateIfTeamSetIsCorrect(homeTeam, awayTeam);
        validateTeamLenght(homeTeam, awayTeam);
    }

    private static void validateTeamLenght(String homeTeam, String awayTeam) {
        if (homeTeam.trim().length() > 35 || awayTeam.trim().length() > 35) {
            throw new IllegalArgumentException(TOO_LONG_TEAM_NAME);
        }
    }

    private void validateIfTeamSetIsCorrect(String homeTeam, String awayTeam) {
        if (homeTeam == null || homeTeam.trim().isEmpty()) {
            throw new IllegalArgumentException(HOME_TEAM_ERROR_MESSAGE);
        }

        if (awayTeam == null || awayTeam.trim().isEmpty()) {
            throw new IllegalArgumentException(AWAY_TEAM_ERROR_MESSAGE);
        }

        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException(SAME_TEAMS_ERROR_MESSAGE);
        }
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }
}
