package co.istad.elearningmanagement.features.category.web;

import co.istad.elearningmanagement.features.category.CategoryService;
import co.istad.elearningmanagement.features.category.dto.CategoryCreateRequest;
import co.istad.elearningmanagement.features.category.dto.CategoryResponse;
import co.istad.elearningmanagement.features.category.dto.CategoryUpdateRequest;
import co.istad.elearningmanagement.features.category.dto.PopularCategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategory(@RequestBody @Valid CategoryCreateRequest categoryCreateRequest) {
        categoryService.createCategory(categoryCreateRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryResponse> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/popular")
    @ResponseStatus(HttpStatus.OK)
    public List<PopularCategoryResponse> getPopularCategories() {
        return categoryService.getPopularCategories();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryResponse getCategoryById(@PathVariable String id) {
        return categoryService.getCategoryById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCategory(@PathVariable String id, @RequestBody @Valid CategoryUpdateRequest categoryUpdateRequest) {
        categoryService.updateCategory(id, categoryUpdateRequest);
    }

    @PutMapping("/{id}/enable")
    @ResponseStatus(HttpStatus.OK)
    public void enableCategory(@PathVariable String id) {
        categoryService.enableCategory(id);
    }

    @PutMapping("/{id}/disable")
    @ResponseStatus(HttpStatus.OK)
    public void disableCategory(@PathVariable String id) {
        categoryService.disableCategory(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategory(@PathVariable String id) {
        categoryService.deleteCategory(id);
    }

}
