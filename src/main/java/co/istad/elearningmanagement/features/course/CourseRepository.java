package co.istad.elearningmanagement.features.course;

import co.istad.elearningmanagement.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends MongoRepository<Course, String> {
    Boolean existsByTitle(String title);

    Page<Course> findByIsDraftedFalse(Pageable pageable);

    Page<Course> findByIsPaidFalse(Pageable pageable);

    Page<Course> findByIsDraftedTrue(Pageable pageable);

    Integer countByCategory(String category);

    List<Course> findAllByCategoryAndIsDisabledFalse(String categoryName);

}
