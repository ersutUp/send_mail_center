package top.ersut.mail.send.web.exception.base;

public class BaseRuntimeException extends RuntimeException {

    public BaseRuntimeException(){
        super();
    }

    public BaseRuntimeException(String message){
        super(message);
    }

    public BaseRuntimeException(Throwable throwable){
        super(throwable);
    }

    public BaseRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
