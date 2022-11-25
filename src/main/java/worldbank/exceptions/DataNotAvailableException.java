package worldbank.exceptions;

public class DataNotAvailableException extends Exception{
    public DataNotAvailableException(String errorMessage) {
        super(errorMessage);
    }
    public DataNotAvailableException() {
        super();
    }
}
