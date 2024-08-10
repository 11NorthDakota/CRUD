package by.northdakota.Exception;

public class DaoException extends RuntimeException {
    public DaoException(Exception e){
        super(e);
    }
}
