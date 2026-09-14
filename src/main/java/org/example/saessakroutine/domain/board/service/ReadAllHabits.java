package org.example.saessakroutine.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.board.persistence.dto.response.GetAllHabitsResponse;
import org.example.saessakroutine.domain.board.persistence.dto.status.exceptions.NoContentsException;
import org.example.saessakroutine.domain.entity.Habit;
import org.example.saessakroutine.domain.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReadAllHabits {
    private final HabitRepository habitRepository;
    public List<GetAllHabitsResponse> ReadAll(){ //전체 습관 불러오는 Response로 반환 형식 선언
        List<Habit> allHabitsResponseList = habitRepository.findAll();
        if (allHabitsResponseList.isEmpty()){
            throw new NoContentsException("조회할 습관이 없습니다."); //사용자 지정 예외
        }
        return allHabitsResponseList.stream().map(GetAllHabitsResponse::new).toList();
    }
}
