package org.example.saessakroutine.domain.habit.presentation.controller;

import org.example.saessakroutine.domain.habit.persistence.dto.status.StatusResponse;
import org.example.saessakroutine.domain.habit.persistence.dto.status.exceptions.NoContentsException;
import org.example.saessakroutine.domain.habit.persistence.dto.status.exceptions.NotThingException;
import org.example.saessakroutine.domain.habit.persistence.dto.status.exceptions.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OtherRoadController {
    @ExceptionHandler(BadRequestException.class) //잘못된 요청이 들어왔을때
    public ResponseEntity<StatusResponse> RequestError(){
        StatusResponse statusResponse = new StatusResponse("Bad Request", 400);
        return new ResponseEntity<>(statusResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotThingException.class) //id로 객체를 찾을 수 없을때
    public ResponseEntity<StatusResponse> Anything(){
        StatusResponse statusResponse = new StatusResponse("Internal Server Error", 500);
        return new ResponseEntity<>(statusResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NoContentsException.class) //객체 조회 할 때 예외 처리를 위해 사용
    public ResponseEntity<StatusResponse> NoContent(){
        StatusResponse statusResponse = new StatusResponse("No Content", 204);
        return new ResponseEntity<>(statusResponse, HttpStatus.NO_CONTENT);
    }
    //예외처리 하기 위한 부분이라 좀 더 생각해보기.
}
