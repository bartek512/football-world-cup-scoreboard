package FootballWorldCupScoreboard;


public class Game {

    private String homeTeam;

    private String awayTeam;

    private int homeScore;

    private int awayScore;

    public Game(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public void startGame() {
        this.homeScore = 0;
        this.awayScore = 0;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }
}
