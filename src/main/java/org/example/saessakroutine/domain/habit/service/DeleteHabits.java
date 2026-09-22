package org.example.saessakroutine.domain.habit.service;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.repository.HabitRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteHabits {
    private final HabitRepository habitRepository;

    public String Delete (Long id){
        habitRepository.deleteById(id);
        habitRepository.findById(id).get().getUser().updateAllHabits(false); //전체 습관 개수 갱신
        return "습관이 성공적으로 삭제되었습니다."; //컨트롤러에서 반환할때 메시지를 출력하기 위해서
    }
}