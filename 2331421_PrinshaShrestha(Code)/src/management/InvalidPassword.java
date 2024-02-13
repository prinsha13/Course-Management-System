package management;
public class InvalidPassword extends Exception {

    private static final long serialVersionUID = -3342413540547235170L;

    public InvalidPassword(String message) {
        super(message);
    }
}
