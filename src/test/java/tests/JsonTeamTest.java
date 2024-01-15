package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.json.Json;
import pages.json.TeamJson;

import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonTeamTest {
    ObjectMapper objMap = new ObjectMapper();
    ClassLoader classLoader = JsonTeamTest.class.getClassLoader();


    @Test
    @DisplayName("BetBoom Team Json information season test")
    void resultTeamInJsonInformationTest() throws Exception {
        try (
                InputStream inputStream = classLoader.getResourceAsStream("ResultTeamSeason.json")
        ) {
            assert inputStream != null;
            try (InputStreamReader inpReader = new InputStreamReader(inputStream)) {
                TeamJson json = objMap.readValue(inpReader, TeamJson.class);
                assertThat(json.team).isEqualTo("BeatBoom");
                assertThat(json.draft).contains("NightFall", "TORONTOTOKIO", "GPK", "PURE", "SAVE");
                assertThat(json.season).isEqualTo("PGL");
                assertThat(json.winRate).isEqualTo("75%");
                assertThat(json.prizeQuantity).contains(
                        "1st PGL",
                        "3st LIMA Major",
                        "6th Berlin Major",
                        "3-4th Riyad Major");
            }
        }
    }
}
