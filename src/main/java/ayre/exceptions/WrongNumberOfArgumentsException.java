package ayre.exceptions;

public class WrongNumberOfArgumentsException extends InvalidCommandException {
    public WrongNumberOfArgumentsException(String e) {
        super(e);
    }
}
