package co.istad.elearningmanagement.features.category;

import co.istad.elearningmanagement.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {
    Boolean existsByName(String name);
    Optional<Category> findByName(String name);

    List<Category> findAllByName(String name);

    List<Category> findAllByIsDisabledIsFalse();
}
