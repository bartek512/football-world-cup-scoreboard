package FootballWorldCupScoreboard;

import java.util.Arrays;

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
     * <p>
     * To ensure that the identifier remains the same regardless of the order of the teams,
     * the home and away teams are sorted alphabetically before generating the identifier.
     * This guarantees that "POLANDITALY" and "ITALYPOLAND" will have the same unique ID.
     *
     * @param homeTeam The name of the home team.
     * @param awayTeam The name of the away team.
     * @return A string representing the unique identifier for the match, based on the team names.
     */
    public static String generateUniqueGameId(String homeTeam, String awayTeam) {
        final String[] teams = {homeTeam == null ? "" : homeTeam.toUpperCase(),
                awayTeam == null ? "" : awayTeam.toUpperCase()};
        Arrays.sort(teams);
        final String combinedTeams = teams[0] + teams[1];
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
