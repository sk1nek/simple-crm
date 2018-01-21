package me.mjaroszewicz.crmapp.exceptions;

public class PersistenceException extends Exception {

    PersistenceException(){
        super();
    }

    public PersistenceException(String msg){
        super(msg);
    }

}
