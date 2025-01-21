package FootballWorldCupScoreboard;

/**
 * This class provides a utility method for generating a unique identifier for a football game
 * based on the names of the home and away teams.
 */
public class UniqueGameIdGenerator {

    /**
     * Generates a unique identifier for a football match based on the home and away teams.
     * <p>
     * The identifier is created by concatenating the numeric values corresponding to each
     * letter of the team names. Each letter is converted to its alphabetical position
     * (A=1, B=2, ..., Z=26).
     *
     * @param homeTeam The name of the home team.
     * @param awayTeam The name of the away team.
     * @return A string representing the unique identifier for the match, based on the team names.
     */
    public static String generateUniqueGameId(String homeTeam, String awayTeam) {
        final String combinedTeams = homeTeam + awayTeam;
        final StringBuilder idBuilder = new StringBuilder();

        for (char c : combinedTeams.toCharArray()) {
            if (Character.isLetter(c)) {
                int number = Character.toUpperCase(c) - 'A' + 1;
                idBuilder.append(number);
            }
        }
        return idBuilder.toString().trim();
    }
}
