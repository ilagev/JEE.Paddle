package business.api.exceptions;

public class NotFoundCourtIdException extends ApiException {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "La pista referenciada no existe";

    public static final int CODE = 1;

    public NotFoundCourtIdException() {
        this("");
    }

    public NotFoundCourtIdException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }

}
