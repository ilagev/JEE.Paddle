package business.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import business.api.exceptions.InvalidCourtReserveException;
import business.api.exceptions.InvalidDateException;
import business.api.exceptions.InvalidUserFieldException;
import business.api.exceptions.UnableToCreateTrainingException;
import business.controllers.CourtController;
import business.controllers.TrainingController;
import business.wrapper.TrainingWrapper;

@RestController
@RequestMapping(Uris.SERVLET_MAP + Uris.TRAININGS)
public class TrainingResource {

    private TrainingController trainingController;
    
    private CourtController courtController;

    @Autowired
    public void setTrainingController(TrainingController trainingController) {
        this.trainingController = trainingController;
    }

    @Autowired
    public void setCourtController(CourtController courtController) {
        this.courtController = courtController;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public void createTraining(@AuthenticationPrincipal User activeUser, @RequestBody TrainingWrapper trainingWrapper) throws InvalidUserFieldException, UnableToCreateTrainingException, InvalidCourtReserveException, InvalidDateException {
        Validation.validateField(activeUser.getUsername(), "username");
        Validation.validateField(activeUser.getPassword(), "password");
        if (!courtController.exist(trainingWrapper.getCourtId()))
            throw new InvalidCourtReserveException("" + trainingWrapper.getCourtId());
        Validation.validateDay(trainingWrapper.getStartTime());
        
        String message = trainingController.createTraining(activeUser.getUsername(), trainingWrapper);
        if (message != null)
            throw new UnableToCreateTrainingException(message);
    }
}
