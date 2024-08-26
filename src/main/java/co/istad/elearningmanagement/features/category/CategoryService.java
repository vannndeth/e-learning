package co.istad.elearningmanagement.features.category;

import co.istad.elearningmanagement.features.category.dto.CategoryCreateRequest;
import co.istad.elearningmanagement.features.category.dto.CategoryResponse;
import co.istad.elearningmanagement.features.category.dto.CategoryUpdateRequest;
import co.istad.elearningmanagement.features.category.dto.PopularCategoryResponse;

import java.util.List;

public interface CategoryService {

    void createCategory(CategoryCreateRequest categoryCreateRequest);

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(String id);

    List<PopularCategoryResponse> getPopularCategories();

    void updateCategory(String id, CategoryUpdateRequest categoryUpdateRequest);

    void disableCategory(String id);

    void enableCategory(String id);

    void deleteCategory(String id);

}
