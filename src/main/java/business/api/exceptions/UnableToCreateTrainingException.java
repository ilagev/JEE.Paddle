package business.api.exceptions;

public class UnableToCreateTrainingException extends ApiException {
    
    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "No se puede crear la clase de padel";

    public static final int CODE = 1;

    public UnableToCreateTrainingException() {
        this("");
    }

    public UnableToCreateTrainingException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
