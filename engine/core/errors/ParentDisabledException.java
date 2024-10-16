package engine.core.errors;

public class ParentDisabledException extends Exception {
    public ParentDisabledException(String message){
        super(message);
    }

    public ParentDisabledException(){
        super();
    }
}
