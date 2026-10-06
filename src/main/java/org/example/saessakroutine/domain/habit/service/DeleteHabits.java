package org.example.saessakroutine.domain.habit.service;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.repository.HabitRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteHabits {
    private final HabitRepository habitRepository;

    public int Delete (Long id){
        habitRepository.findById(id).get().getUser().updateAllHabits(false); //전체 습관 개수 갱신
        habitRepository.deleteById(id);
        return 200; //컨트롤러에서 반환할때 메시지를 출력하기 위해서
    }
}