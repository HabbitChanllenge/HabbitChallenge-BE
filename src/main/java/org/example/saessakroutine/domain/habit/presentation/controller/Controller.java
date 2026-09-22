package org.example.saessakroutine.domain.habit.presentation.controller;
import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.habit.persistence.dto.request.DailyHabitCreatRequest;
import org.example.saessakroutine.domain.habit.persistence.dto.request.HabitCompleteRequest;
import org.example.saessakroutine.domain.habit.persistence.dto.request.HabitsPatchRequest;
import org.example.saessakroutine.domain.habit.persistence.dto.request.WeeklyHabitCreatRequest;
import org.example.saessakroutine.domain.habit.persistence.dto.response.GetAllHabitsResponse;
import org.example.saessakroutine.domain.habit.persistence.dto.response.GetAllStreak;
import org.example.saessakroutine.domain.habit.persistence.dto.status.StatusResponse;
import org.example.saessakroutine.domain.habit.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping
@RequiredArgsConstructor //필수 인자를 가진 생성자를 자동으로 생성 (이게 있기 때문에 밑에 서비스 클래스들을 불러올 수 있는 것.
public class Controller {
    private final CreateDailyHabit createDailyHabit;
    private final CreateWeeklyHabit createWeeklyHabit;
    private final DeleteHabits deleteHabits;
    private final CompleteHabit completeHabit;
    private final ReadAllHabits readAllHabits;
    private final HabitsPatch habitsPatch;
    private final ReadAllStreak readAllStreak;

    @PostMapping("/habit/day")
    public ResponseEntity<StatusResponse> postDailyHabit(@RequestBody DailyHabitCreatRequest request){
        StatusResponse statusResponse = new StatusResponse(
                "OK",
                createDailyHabit.dalyCreate(request));
        return new ResponseEntity<>(statusResponse, HttpStatus.CREATED);
    }

    @PostMapping("/habit/week")
    public ResponseEntity<StatusResponse> postWeeklyHabit(@RequestBody WeeklyHabitCreatRequest request){
        StatusResponse statusResponse = new StatusResponse(
                "OK",
                createWeeklyHabit.weeklyCreate(request));
        return new ResponseEntity<>(statusResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/habit/{id}")
    public ResponseEntity<StatusResponse> deleteHabit(@PathVariable Long id){
        //@PathVariabl은 경로에서 받은 Id값을 받아오는 어노테이션
        StatusResponse statusResponse = new StatusResponse("OK", deleteHabits.Delete(id));
        return new ResponseEntity<>(statusResponse, HttpStatus.OK);
    }

    @PatchMapping("/habit/{id}")
    public ResponseEntity<StatusResponse> completeHabit(@PathVariable Long id, @RequestBody HabitCompleteRequest request){
        StatusResponse statusResponse = new StatusResponse(
                "OK",
                completeHabit.Complete(id, request));
        return new ResponseEntity<>(statusResponse, HttpStatus.OK);
    }

    @GetMapping("/habit")
    public List<GetAllHabitsResponse> getAllHabit (Authentication authentication){
        return readAllHabits.ReadAllHabit(authentication);
    } //여기에는 상태코드와함께 반환할 메시지가 필요없어서

    @PatchMapping("/habit/update/{id}")
    public ResponseEntity<StatusResponse> patchHabits (@PathVariable Long id, @RequestBody HabitsPatchRequest request){
        StatusResponse statusResponse = new StatusResponse(
                "OK",
                habitsPatch.Patch(id, request));
        return new ResponseEntity<>(statusResponse, HttpStatus.OK);
    }
    @GetMapping("/streaks/allStreak")
    public GetAllStreak allStreak (Authentication authentication){
        return readAllStreak.AllStreak(authentication);
    }
    //랭킹 컨트롤러
}
