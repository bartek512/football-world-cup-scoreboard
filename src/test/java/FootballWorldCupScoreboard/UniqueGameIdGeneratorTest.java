package FootballWorldCupScoreboard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UniqueGameIdGeneratorTest {

    @Test
    public void generateUniqueGameIdTest() {
        // given
        // Two teams
        String homeTeam = "POLAND";
        String awayTeam = "ITALY";

        // and
        // Expected id
        // P=16, O=15, L=12, A=1, N=14, D=4 + I=9, T=20, A=1, L=12, Y=25
        String expectedId = "920112251615121144";

        // when
        // Generate Id
        String actualId = UniqueGameIdGenerator.generateUniqueGameId(homeTeam, awayTeam);

        // then
        // ID should be generated properly
        assertEquals(expectedId, actualId);
    }

    @Test
    public void generateEmptyStringWhenBothTeamsAreNull() {
        // given
        // Two  null teams
        String homeTeam = null;
        String awayTeam = null;

        // and
        // Expected id
        String expectedId = "";

        // when
        // Generate Id
        String actualId = UniqueGameIdGenerator.generateUniqueGameId(homeTeam, awayTeam);

        // then
        // ID should be empty
        assertEquals(expectedId, actualId);
    }

    @Test
    public void generateIdWhenOneOfTeamsIsNull() {
        // given
        // One  null team
        String homeTeam = "POLAND";
        String awayTeam = null;

        // and
        // Expected id
        // P=16, O=15, L=12, A=1, N=14, D=4
        String expectedId = "1615121144";

        // when
        // Generate Id
        String actualId = UniqueGameIdGenerator.generateUniqueGameId(homeTeam, awayTeam);

        // then
        // ID should be generated properly
        assertEquals(expectedId, actualId);
    }
}
