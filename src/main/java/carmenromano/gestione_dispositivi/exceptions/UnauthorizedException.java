package carmenromano.gestione_dispositivi.exceptions;


public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message){
        super(message);
    }
}
