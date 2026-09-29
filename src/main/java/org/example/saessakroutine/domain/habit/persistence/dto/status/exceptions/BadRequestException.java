package org.example.saessakroutine.domain.habit.persistence.dto.status.exceptions;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException{
    public BadRequestException(){
        super();
    }
}
