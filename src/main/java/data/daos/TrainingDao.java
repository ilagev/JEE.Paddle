package data.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import data.entities.Training;

public interface TrainingDao extends JpaRepository<Training, Integer>  {
    
    public Training findById(int id); 

}
