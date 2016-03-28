package business.api.exceptions;

public class ReachedMaximumTraineesException extends ApiException {

    private static final long serialVersionUID = -1344640670884805385L;

    public static final String DESCRIPTION = "No se puede apuntar mas gente a la clase";

    public static final int CODE = 1;

    public ReachedMaximumTraineesException() {
        this("");
    }

    public ReachedMaximumTraineesException(String detail) {
        super(DESCRIPTION + ". " + detail, CODE);
    }
    
}
