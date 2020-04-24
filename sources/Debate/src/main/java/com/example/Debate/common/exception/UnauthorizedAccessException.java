package com.example.Debate.common.exception;

public class UnauthorizedAccessException extends  RuntimeException{

    public UnauthorizedAccessException(String msg){
        super(msg);
    }

}
