package api;

import static org.junit.Assert.*;

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

public class TrainingResourceFunctionalTesting {
    
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
    }
    
    @Test
    public void testCreateTraining() {
        TrainingWrapper training;
        
        // non existent courtId
        try {
            training = new TrainingWrapper(-1, Calendar.getInstance());
            new RestBuilder<TrainingWrapper>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenTrainer, "").body(training).post().build();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.NOT_FOUND, httpError.getStatusCode());
            LogManager.getLogger(this.getClass()).info(
                    "testCreateTraining (" + httpError.getMessage() + "):\n    " + httpError.getResponseBodyAsString());
        }
        
        // existing Reservation that week
        try {
            restService.createCourt("10");
            Calendar date = Calendar.getInstance();
            new RestBuilder<TrainingWrapper>(RestService.URL).path(Uris.RESERVES).basicAuth(tokensPlayer.get(0), "").body(new AvailableTime(10, date)).post().build();
            training = new TrainingWrapper(10, date);
            new RestBuilder<TrainingWrapper>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenTrainer, "").body(training).post().build();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.BAD_REQUEST, httpError.getStatusCode());
            LogManager.getLogger(this.getClass()).info(
                    "testCreateTraining (" + httpError.getMessage() + "):\n    " + httpError.getResponseBodyAsString());
        }
        
        // valid training
        training = new TrainingWrapper(1, Calendar.getInstance());
        new RestBuilder<TrainingWrapper>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenTrainer, "").body(training).clazz(TrainingWrapper.class).post().build();
        
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
    
    @Test
    public void testRegisterTraining() {
        
        // valid training
        TrainingWrapper trainingBody = new TrainingWrapper(1, Calendar.getInstance());
        TrainingWrapper t = new RestBuilder<TrainingWrapper>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenTrainer, "").body(trainingBody).clazz(TrainingWrapper.class).post().build();
        
        // training not found
        try {
            new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS + "/100").basicAuth(tokensPlayer.get(0), "").put().build();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.NOT_FOUND, httpError.getStatusCode());
            LogManager.getLogger(this.getClass()).info(
                    "testRegisterTraining (" + httpError.getMessage() + "):\n    " + httpError.getResponseBodyAsString());
        }
        
        // successful case
        for (int i = 0; i < TrainingController.MAX_TRAINEES; i++)
            new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).pathId(t.getId()).basicAuth(tokensPlayer.get(i), "").put().build();

        // max players
        try {
            new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).pathId(String.valueOf(t.getId())).basicAuth(tokensPlayer.get(tokensPlayer.size() - 1), "").put().build();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.BAD_REQUEST, httpError.getStatusCode());
            LogManager.getLogger(this.getClass()).info(
                    "testRegisterTraining (" + httpError.getMessage() + "):\n    " + httpError.getResponseBodyAsString());
        }
    }
    
    @Test
    public void testShowAllTrainings() {
        int numberOfTrainings = 5;
        for (int i = 0; i < numberOfTrainings; i++) {
            Calendar date = Calendar.getInstance();
            date.add(Calendar.DAY_OF_MONTH, i + 5);
            TrainingWrapper trainingBody = new TrainingWrapper(1, date);
            new RestBuilder<TrainingWrapper>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenTrainer, "").body(trainingBody).clazz(TrainingWrapper.class).post().build();
        }
        TrainingWrapper[] list = new RestBuilder<TrainingWrapper[]>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokensPlayer.get(0), "").clazz(TrainingWrapper[].class).get().build();
        assertEquals(list.length, numberOfTrainings);
    }
    
    @Test
    public void testDeleteTrainingPlayer() {
        // create training
        Calendar date = Calendar.getInstance();
        TrainingWrapper trainingBody = new TrainingWrapper(1, date);
        TrainingWrapper training = new RestBuilder<TrainingWrapper>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokenTrainer, "").body(trainingBody).clazz(TrainingWrapper.class).post().build();
        
        // sign up some trainees
        int numberOfTrainees = 3;
        for (int i = 0; i < numberOfTrainees; i++) {
            training = new RestBuilder<TrainingWrapper>(RestService.URL).path(Uris.TRAININGS).pathId(training.getId()).basicAuth(tokensPlayer.get(i), "").clazz(TrainingWrapper.class).put().build();
        }
        
        // delete a trainee
        new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).pathId(training.getId()).path(Uris.TRAINEES).pathId(training.getTraineesIDs().get(0)).basicAuth(tokenTrainer, "").delete().build();
        
        // check number of trainings into the DB
        TrainingWrapper[] list = new RestBuilder<TrainingWrapper[]>(RestService.URL).path(Uris.TRAININGS).basicAuth(tokensPlayer.get(0), "").clazz(TrainingWrapper[].class).get().build();
        assertEquals(list[0].getTraineesIDs().size(), numberOfTrainees - 1);
        
        // non-existent training ID
        try {
            new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).pathId("-5").path(Uris.TRAINEES).pathId(training.getTraineesIDs().get(0)).basicAuth(tokenTrainer, "").delete().build();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.NOT_FOUND, httpError.getStatusCode());
            LogManager.getLogger(this.getClass()).info(
                    "testDeleteTrainingPlayer (" + httpError.getMessage() + "):\n    " + httpError.getResponseBodyAsString());
        }
        
        // non-existent trainee ID
        try {
            new RestBuilder<String>(RestService.URL).path(Uris.TRAININGS).pathId(training.getId()).path(Uris.TRAINEES).pathId("-5").basicAuth(tokenTrainer, "").delete().build();
        } catch (HttpClientErrorException httpError) {
            assertEquals(HttpStatus.NOT_FOUND, httpError.getStatusCode());
            LogManager.getLogger(this.getClass()).info(
                    "testDeleteTrainingPlayer (" + httpError.getMessage() + "):\n    " + httpError.getResponseBodyAsString());
        }
    }
    
    @After
    public void deleteAll() {
        new RestService().deleteAll();
    }

}
