package FootballWorldCupScoreboard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UniqueGameIdGeneratorTest {

    @Test
    public void generateUniqueGameIdTest() {
        // given
        // Two teams
        final String homeTeam = "POLAND";
        final String awayTeam = "ITALY";

        // and
        // Expected id
        // P=16, O=15, L=12, A=1, N=14, D=4 + I=9, T=20, A=1, L=12, Y=25
        final String expectedId = "920112251615121144";

        // when
        // Generate Id
        final String actualId = UniqueGameIdGenerator.generateUniqueGameId(homeTeam, awayTeam);

        // then
        // ID should be generated properly
        assertEquals(expectedId, actualId);
    }

    @Test
    public void generateEmptyStringWhenBothTeamsAreNull() {
        // given
        // Two  null teams
        final String homeTeam = null;
        final String awayTeam = null;

        // and
        // Expected id
        final String expectedId = "";

        // when
        // Generate Id
        final String actualId = UniqueGameIdGenerator.generateUniqueGameId(homeTeam, awayTeam);

        // then
        // ID should be empty
        assertEquals(expectedId, actualId);
    }

    @Test
    public void generateIdWhenOneOfTeamsIsNull() {
        // given
        // One  null team
        final String homeTeam = "POLAND";
        final String awayTeam = null;

        // and
        // Expected id
        // P=16, O=15, L=12, A=1, N=14, D=4
        final String expectedId = "1615121144";

        // when
        // Generate Id
        final String actualId = UniqueGameIdGenerator.generateUniqueGameId(homeTeam, awayTeam);

        // then
        // ID should be generated properly
        assertEquals(expectedId, actualId);
    }
}
