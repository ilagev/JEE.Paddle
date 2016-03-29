package api;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import business.api.Uris;
import business.controllers.TrainingController;
import business.wrapper.AvailableTime;
import business.wrapper.TrainingWrapper;

public class ReserveResourceFunctionalTesting {
    
    RestService restService;
    
    String tokenTrainer;
    
    List<String> tokensPlayer;

    @Before
    public void init() {
        restService = new RestService();
        tokenTrainer = restService.registerAndLoginTrainer("trainer");
        tokensPlayer = new ArrayList<>();
        for (int i = 0; i < TrainingController.MAX_TRAINEES + 1; i++) {
            tokensPlayer.add(restService.registerAndLoginPlayer("player" + i));
        }
        restService.createCourt("1");
        restService.createCourt("2");
        restService.createCourt("10");
    }

    @Test
    public void testshowAvailability() {
        String token = restService.registerAndLoginPlayer("player");
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DAY_OF_YEAR, 1);
        day.set(Calendar.HOUR_OF_DAY, 12);
        new RestBuilder<String>(RestService.URL).path(Uris.RESERVES).basicAuth(token, "").body(new AvailableTime(1, day)).post().build();
        day.set(Calendar.HOUR_OF_DAY, 14);
        new RestBuilder<String>(RestService.URL).path(Uris.RESERVES).basicAuth(token, "").body(new AvailableTime(2, day)).post().build();
        String day2 = "" + day.getTimeInMillis();
        String response = new RestBuilder<String>(RestService.URL).path(Uris.RESERVES).path(Uris.AVAILABILITY).basicAuth(token, "")
                .param("day", day2).clazz(String.class).get().build();
        LogManager.getLogger(this.getClass()).info("testshowAvailability (" + response + ")");
    }

    @Test
    public void testReserveCourt() {
        String token = restService.registerAndLoginPlayer("player");
        Calendar day = Calendar.getInstance();
        day.add(Calendar.DAY_OF_YEAR, 1);
        day.set(Calendar.HOUR_OF_DAY,12);
        new RestBuilder<String>(RestService.URL).path(Uris.RESERVES).basicAuth(token, "").body(new AvailableTime(1, day)).post().build();
        day.set(Calendar.HOUR_OF_DAY,14);
        new RestBuilder<String>(RestService.URL).path(Uris.RESERVES).basicAuth(token, "").body(new AvailableTime(2, day)).post().build();
    }
    
    @Test
    public void testExistingTrainingAtThatTime() {
        try {
            Calendar date = Calendar.getInstance();
            TrainingWrapper training = new TrainingWrapper(10, date);
            new RestBuilder<TrainingWrapper>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenTrainer, "").body(training).post().build();
            new RestBuilder<TrainingWrapper>(RestService.URL).path(Uris.RESERVES).basicAuth(tokensPlayer.get(0), "").body(new AvailableTime(10, date)).post().build();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.BAD_REQUEST, httpError.getStatusCode());
            LogManager.getLogger(this.getClass()).info(
                    "testExistingTrainingAtThatTime (" + httpError.getMessage() + "):\n    " + httpError.getResponseBodyAsString());
        }
    }

    @After
    public void deleteAll() {
        new RestService().deleteAll();
    }

}
