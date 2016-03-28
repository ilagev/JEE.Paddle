package business.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import business.wrapper.TrainingWrapper;
import data.daos.CourtDao;
import data.daos.ReserveDao;
import data.daos.TrainingDao;
import data.daos.UserDao;
import data.entities.Reserve;
import data.entities.Training;
import data.entities.User;

@Controller
@Transactional
public class TrainingController {
    
    public static final int TRAINING_DURATION = 1; //hours
    
    public static final int MAX_TRAINEES = 4;
    
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

    public TrainingWrapper createTraining(String username, TrainingWrapper trainingWrapper) {
        if (this.existsReservation(trainingWrapper.getStartTime(), trainingWrapper.getCourtId())) {
            return new TrainingWrapper(-1);
        } else if (this.existsTrainingThatWeek(trainingWrapper.getStartTime(), username)) {
            return new TrainingWrapper(-2);
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
            
            int id = trainingDao.save(training).getId();
            return new TrainingWrapper(id, training.getCourt().getId(), username, training.getStartTime());
        }
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

    public boolean exists(int id) {
        return trainingDao.exists(id);
    }

    public TrainingWrapper registerTraining(int id, String username) {
        Training training = trainingDao.findById(id);
        User user = userDao.findByUsernameOrEmail(username);
        if (training.getTrainees().size() >= MAX_TRAINEES)
            return new TrainingWrapper(-1);
        training.getTrainees().add(user);
        int trainingId = trainingDao.save(training).getId();
        return new TrainingWrapper(trainingId, training.getCourt().getId(), username, training.getStartTime());
    }

    public List<TrainingWrapper> showTrainings() {
        List<TrainingWrapper> trainingsWrapper = new ArrayList<>();
        List<Training> trainings = trainingDao.findAll();
        for (Training t : trainings)
            trainingsWrapper.add(new TrainingWrapper(t.getId(),
                                                     t.getCourt().getId(),
                                                     t.getTrainer().getUsername(),
                                                     t.getStartTime()));
        return trainingsWrapper;
    }

    public void deleteTrainingPlayer(int trainingId, int traineeId) {
        Training training = trainingDao.findById(trainingId);
        for (User trainee : training.getTrainees())
            if (trainee.getId() == traineeId)
                training.getTrainees().remove(trainee);
        trainingDao.save(training);
    }

}
