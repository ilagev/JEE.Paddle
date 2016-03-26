package business.controllers;

import java.util.Calendar;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import business.wrapper.TrainingWrapper;
import data.daos.CourtDao;
import data.daos.ReserveDao;
import data.daos.TrainingDao;
import data.daos.UserDao;
import data.entities.Reserve;
import data.entities.Training;
import data.entities.User;

@Controller
public class TrainingController {
    
    private static final int TRAINING_DURATION = 1; //hours
    
    private TrainingDao trainingDao;
    
    private ReserveDao reserveDao;

    private CourtDao courtDao;
    
    private UserDao userDao;
    
    @Autowired
    public void setTrainingDao(TrainingDao trainingDao) {
        this.trainingDao = trainingDao;
    }
    
    @Autowired
    public void setReserveDao(ReserveDao reserveDao) {
        this.reserveDao = reserveDao;
    }
    
    @Autowired
    public void setCourtDao(CourtDao courtDao) {
        this.courtDao = courtDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String createTraining(String username, TrainingWrapper trainingWrapper) {
        if (this.existsReservation(trainingWrapper.getStartTime(), trainingWrapper.getCourtId())) {
            return "Ya hay una reserva de pista a esa hora";
        } else if (this.existsTrainingThatWeek(trainingWrapper.getStartTime(), username)) {
            return "Ya hay programada una clase del entrenador " + username + " en esta semana";
        } else {
            Calendar startTime = trainingWrapper.getStartTime();
            Calendar endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR_OF_DAY, TRAINING_DURATION);
            
            Training training = new Training(
                    courtDao.findById(trainingWrapper.getCourtId()),
                    userDao.findByUsernameOrEmail(username),
                    new HashSet<User>(),
                    startTime,
                    endTime);
            
            trainingDao.save(training);
        }
        return null;
    }

    private boolean existsTrainingThatWeek(Calendar startTime, String username) {
        Calendar beginningOfThatWeek = (Calendar) startTime.clone();
        beginningOfThatWeek.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        beginningOfThatWeek.set(Calendar.HOUR_OF_DAY, 0);
        beginningOfThatWeek.set(Calendar.MINUTE, 0);
        beginningOfThatWeek.set(Calendar.SECOND, 0);
        
        Calendar endingOfThatWeek = (Calendar) startTime.clone();
        endingOfThatWeek.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        endingOfThatWeek.set(Calendar.HOUR_OF_DAY, 23);
        endingOfThatWeek.set(Calendar.MINUTE, 59);
        endingOfThatWeek.set(Calendar.SECOND, 59);
        
        for (Reserve reserve : reserveDao.findByDateBetween(beginningOfThatWeek, endingOfThatWeek)) {
            if (reserve.getUser().getUsername() == username) {
                return true;
            }
        }
        
        return false;
    }

    private boolean existsReservation(Calendar startTime, int courtId) {
        return reserveDao.findByCourtAndDate(courtDao.findById(courtId), startTime) != null;
    }

}
