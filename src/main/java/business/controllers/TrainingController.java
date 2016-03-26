package business.controllers;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import business.wrapper.TrainingWrapper;
import data.daos.CourtDao;
import data.daos.ReserveDao;
import data.daos.TrainingDao;
import data.daos.UserDao;
import data.entities.Court;
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
        if (this.existsReservation(trainingWrapper.getStartTime())) {
            return "Ya hay una reserva de pista a esa hora";
        } else if (this.existsTraining(trainingWrapper.getStartTime(), username)) {
            return "Ya hay programada una clase del entrenador " + username + " en esta semana";
        } else {
            Court court = courtDao.findById(trainingWrapper.getCourtId());
            User trainer = userDao.findByUsernameOrEmail(username);
            Set<User> trainees = new HashSet<User>();
            Calendar startTime = trainingWrapper.getStartTime();
            Calendar endTime = (Calendar) startTime.clone();
            endTime.add(Calendar.HOUR_OF_DAY, TRAINING_DURATION);
            
            Training training = new Training(court, trainer, trainees, startTime, endTime);
            trainingDao.save(training);
        }
        return null;
    }

    private boolean existsTraining(Calendar startTime, String username) {
        // TODO Auto-generated method stub
        return false;
    }

    private boolean existsReservation(Calendar startTime) {
        // TODO Auto-generated method stub
        return false;
    }

}
