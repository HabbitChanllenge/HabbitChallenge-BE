package org.example.saessakroutine.domain.repository;

import org.example.saessakroutine.domain.entity.WeeklyHabit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeekRepository extends JpaRepository<WeeklyHabit, Long> { //실시간으로 시간정보를 받아야 하는 것이기 때문에 별도의 코드가 필요할 수 있다.
}
//자식 엔티티에서 상속받은 부모의 변수에도 접근 할 수 있으므로 자식 엔티티의 레포지토리로도 부모 클래스에서 상속받은 변수에 접근할 수 있다.