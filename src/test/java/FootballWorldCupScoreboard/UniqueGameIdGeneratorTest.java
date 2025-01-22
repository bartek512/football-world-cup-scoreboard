package FootballWorldCupScoreboard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UniqueGameIdGeneratorTest {

    @Test
    public void testGenerateUniqueGameId() {
        //given
        // Two teams
        String homeTeam = "POLAND";
        String awayTeam = "ITALY";

        //AND
        // Expected id
        // P=16, O=15, L=12, A=1, N=14, D=4 + I=9, T=20, A=1, L=12, Y=25
        String expectedId = "920112251615121144";

        String actualId = UniqueGameIdGenerator.generateUniqueGameId(homeTeam, awayTeam);

        assertEquals(expectedId, actualId);
    }
}
