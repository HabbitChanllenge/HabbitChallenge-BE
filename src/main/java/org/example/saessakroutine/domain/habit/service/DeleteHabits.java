package org.example.saessakroutine.domain.habit.service;

import lombok.RequiredArgsConstructor;
import org.example.saessakroutine.domain.entity.Habit;
import org.example.saessakroutine.domain.habit.persistence.dto.status.exceptions.NoContentsException;
import org.example.saessakroutine.domain.repository.HabitRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteHabits {
    private final HabitRepository habitRepository;

    public int Delete (Long id){
        System.out.println("ID: " + id);
        Habit habit = habitRepository.findById(id).orElseThrow(()-> new NoContentsException());
        habit.getUser().updateAllHabits(false);
        habit.getUser().updateCompletedHabitsDelete();
        habitRepository.deleteById(id);
        return 200; //컨트롤러에서 반환할때 메시지를 출력하기 위해서
    }
}