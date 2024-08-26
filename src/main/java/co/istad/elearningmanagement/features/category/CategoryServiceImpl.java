package co.istad.elearningmanagement.features.category;

import co.istad.elearningmanagement.domain.Category;
import co.istad.elearningmanagement.features.category.dto.CategoryCreateRequest;
import co.istad.elearningmanagement.features.category.dto.CategoryResponse;
import co.istad.elearningmanagement.features.category.dto.CategoryUpdateRequest;
import co.istad.elearningmanagement.features.category.dto.PopularCategoryResponse;
import co.istad.elearningmanagement.features.course.CourseRepository;
import co.istad.elearningmanagement.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final MongoTemplate mongoTemplate;
    private final CourseRepository courseRepository;

    private final String baseUrl = "http://localhost:8080/image/";

    @Override
    public void createCategory(CategoryCreateRequest categoryCreateRequest) {

        if (categoryRepository.existsByName(categoryCreateRequest.name())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category already exists");
        }

        Category newCategory = categoryMapper.mapRequestToCategory(categoryCreateRequest);
        newCategory.setName(categoryCreateRequest.name());
        newCategory.setIcon(baseUrl + categoryCreateRequest.icon());
        newCategory.setIsDisabled(false);
        categoryRepository.save(newCategory);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {

//        if (categoryRepository.count() == 0) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Categories is empty");
//        }

        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::mapToCategoryResponse)
                .toList();
    }

    @Override
    public CategoryResponse getCategoryById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        return categoryMapper.mapToCategoryResponse(category);
    }

    @Override
    public List<PopularCategoryResponse> getPopularCategories() {

        List<Category> categories = categoryRepository.findAll();

        return categories.stream().map(category -> {

            int totalCourse = courseRepository.countByCategory(category.getName());

            return PopularCategoryResponse.builder()
                    .name(category.getName())
                    .icon(category.getIcon())
                    .totalCourses(totalCourse)
                    .build();
            })
            .sorted((category1, category2) -> category2.totalCourses().compareTo(category1.totalCourses()))
            .toList();

    }

    @Override
    public void updateCategory(String id, CategoryUpdateRequest categoryUpdateRequest) {

       Category category = categoryRepository.findById(id)
               .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

       category.setIcon(baseUrl + categoryUpdateRequest.icon());

       categoryMapper.mapCategoryUpdateRequest(category, categoryUpdateRequest);

       categoryRepository.save(category);
    }

    @Override
    public void disableCategory(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        category.setIsDisabled(true);
        categoryRepository.save(category);
    }

    @Override
    public void enableCategory(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        category.setIsDisabled(false);
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        categoryRepository.delete(category);
    }
}
