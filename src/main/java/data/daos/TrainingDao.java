package data.daos;

import java.util.Calendar;

import org.springframework.data.jpa.repository.JpaRepository;

import data.entities.Court;
import data.entities.Training;

public interface TrainingDao extends JpaRepository<Training, Integer>  {
    
    Training findByCourtAndStartTime(Court court, Calendar startTime);
    
    public Training findById(int id); 

}
