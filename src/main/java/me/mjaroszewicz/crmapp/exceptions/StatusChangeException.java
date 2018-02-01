package me.mjaroszewicz.crmapp.exceptions;

public class StatusChangeException extends Exception {

    public StatusChangeException(){
        super();
    }

    public StatusChangeException(String message){
        super(message);
    }

    public StatusChangeException(Throwable t){
        super(t);
    }
}
