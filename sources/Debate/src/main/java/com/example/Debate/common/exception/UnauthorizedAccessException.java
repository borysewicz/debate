package com.example.Debate.common.exception;

public class UnauthorizedAccessException extends  RuntimeException{

    public UnauthorizedAccessException(String msg){
        super(msg);
    }

    public UnauthorizedAccessException(){
        super("You are not allowed to modify this resource");
    }

}
