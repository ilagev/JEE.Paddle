package business.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import business.api.exceptions.InvalidDateException;
import business.api.exceptions.InvalidUserFieldException;
import business.api.exceptions.NotFoundCourtIdException;
import business.api.exceptions.NotFoundTrainingIdException;
import business.api.exceptions.NotFoundUserIdException;
import business.api.exceptions.ReachedMaximumTraineesException;
import business.api.exceptions.UnableToCreateTrainingException;
import business.controllers.CourtController;
import business.controllers.TrainingController;
import business.controllers.UserController;
import business.wrapper.TrainingWrapper;

@RestController
@RequestMapping(Uris.SERVLET_MAP + Uris.TRAININGS)
public class TrainingResource {

    private TrainingController trainingController;
    
    private CourtController courtController;
    
    private UserController userController;

    @Autowired
    public void setTrainingController(TrainingController trainingController) {
        this.trainingController = trainingController;
    }

    @Autowired
    public void setCourtController(CourtController courtController) {
        this.courtController = courtController;
    }
    
    @Autowired
    public void setCourtController(UserController userController) {
        this.userController = userController;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public TrainingWrapper createTraining(@AuthenticationPrincipal User activeUser, @RequestBody TrainingWrapper trainingWrapper) throws InvalidUserFieldException, UnableToCreateTrainingException, NotFoundCourtIdException, InvalidDateException {
        Validation.validateField(activeUser.getUsername(), "username");
        if (!courtController.exist(trainingWrapper.getCourtId()))
            throw new NotFoundCourtIdException("" + trainingWrapper.getCourtId());
        Validation.validateDay(trainingWrapper.getStartTime());
        
        TrainingWrapper training = trainingController.createTraining(activeUser.getUsername(), trainingWrapper);
        if (training.getId() == -1) {
            throw new UnableToCreateTrainingException("Ya hay una pista reservada para esa hora");
        } else if (training.getId() == -2) {
            throw new UnableToCreateTrainingException("El entrenador " + activeUser.getUsername() + " ya tiene cogida la semana");
        } else {
            return training;
        }
    }
    
    @RequestMapping(value = Uris.ID, method = RequestMethod.PUT)
    public TrainingWrapper registerTraining(@AuthenticationPrincipal User activeUser, @PathVariable int id) throws InvalidUserFieldException, NotFoundTrainingIdException, ReachedMaximumTraineesException {
        Validation.validateField(activeUser.getUsername(), "username");
        if (!trainingController.exists(id))
            throw new NotFoundTrainingIdException("No existe ese entrenamiento");
        TrainingWrapper t = trainingController.registerTraining(id, activeUser.getUsername());
        if (t.getId() == -1)
            throw new ReachedMaximumTraineesException("El cupo esta lleno");
        return t;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public List<TrainingWrapper> showTrainings() {
        return trainingController.showTrainings();
    }
    
    @RequestMapping(value = Uris.ID + Uris.TRAINEES + Uris.TRAINEE_ID, method = RequestMethod.DELETE)
    public void deleteTrainingPlayer(@PathVariable("id") int trainingId, @PathVariable("traineeId") int traineeId) throws NotFoundTrainingIdException, NotFoundUserIdException {
        if (!trainingController.exists(trainingId))
            throw new NotFoundTrainingIdException("No existe ese entrenamiento");
        if (!userController.exists(traineeId))
            throw new NotFoundUserIdException("No existe ese usuario");
        trainingController.deleteTrainingPlayer(trainingId, traineeId);
    }
}
