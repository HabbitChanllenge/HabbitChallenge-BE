package org.example.saessakroutine.domain.repository;

import org.example.saessakroutine.domain.entity.DailyHabit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DayRepository extends JpaRepository<DailyHabit, Long> {
}
//자식 엔티티에서 상속받은 부모의 변수에도 접근 할 수 있으므로 자식 엔티티의 레포지토리로도 부모 클래스에서 상속받은 변수에 접근할 수 있다.
