package co.edu.poli.tutorias.entity.repository;

import co.edu.poli.tutorias.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

    @Query("FROM Course AS o WHERE o.code = ?1")
    List<Course> findByCode(String code);
}
