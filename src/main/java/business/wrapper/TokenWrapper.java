package business.wrapper;

public class TokenWrapper {

    private String token;

    public TokenWrapper() {
    }

    public TokenWrapper(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenWrapper [token=" + token + "]";
    }

}
