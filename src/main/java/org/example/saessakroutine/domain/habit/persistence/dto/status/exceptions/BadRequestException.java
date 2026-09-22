package org.example.saessakroutine.domain.habit.persistence.dto.status.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
