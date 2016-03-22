package business.api.exceptions;

public class AlreadyExistCourtIdException extends ApiException {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "Ya existe la pista";

    public static final int CODE = 1;

    public AlreadyExistCourtIdException() {
        this("");
    }

    public AlreadyExistCourtIdException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
