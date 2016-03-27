package api;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.junit.After;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import business.api.Uris;
import business.wrapper.AvailableTime;
import business.wrapper.TrainingWrapper;

public class TrainingResourceFunctionalTesting {
    
    RestService restService = new RestService();
    
    String tokenTrainer = restService.registerAndLoginTrainer("trainer");
    
    String tokenPlayer = restService.registerAndLoginPlayer("player");
    
    @Test
    public void testCreateTraining() {
        TrainingWrapper training;
        
        // non existent courtId
        try {
            training= new TrainingWrapper(-1, Calendar.getInstance());
            new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenTrainer, "").body(training).post().build();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.NOT_FOUND, httpError.getStatusCode());
            LogManager.getLogger(this.getClass()).info(
                    "testCreateTraining (" + httpError.getMessage() + "):\n    " + httpError.getResponseBodyAsString());
        }
        
        // existing Reservation that week
        try {
            restService.createCourt("10");
            Calendar date = Calendar.getInstance();
            new RestBuilder<String>(RestService.URL).path(Uris.RESERVES).basicAuth(tokenPlayer, "").body(new AvailableTime(10, date)).post().build();
            training = new TrainingWrapper(10, date);
            new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenTrainer, "").body(training).post().build();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.BAD_REQUEST, httpError.getStatusCode());
            LogManager.getLogger(this.getClass()).info(
                    "testCreateTraining (" + httpError.getMessage() + "):\n    " + httpError.getResponseBodyAsString());
        }
        
        // valid training
        restService.createCourt("1");
        training = new TrainingWrapper(1, Calendar.getInstance());
        new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenTrainer, "").body(training).post().build();
        
        // existing Training that hour and that trainer
        try {
            training = new TrainingWrapper(1, training.getStartTime());
            new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenTrainer, "").body(training).post().build();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.BAD_REQUEST, httpError.getStatusCode());
            LogManager.getLogger(this.getClass()).info(
                    "testCreateTraining (" + httpError.getMessage() + "):\n    " + httpError.getResponseBodyAsString());
        }
    }
    
    @After
    public void deleteAll() {
        new RestService().deleteAll();
    }

}
