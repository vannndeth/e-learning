package co.istad.elearningmanagement.mapper;

import co.istad.elearningmanagement.domain.Category;
import co.istad.elearningmanagement.features.category.dto.CategoryCreateRequest;
import co.istad.elearningmanagement.features.category.dto.CategoryResponse;
import co.istad.elearningmanagement.features.category.dto.CategoryUpdateRequest;
import co.istad.elearningmanagement.features.category.dto.PopularCategoryResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {CustomMapper.class})
public interface CategoryMapper {


    Category mapRequestToCategory(CategoryCreateRequest categoryCreateRequest);

    CategoryResponse mapToCategoryResponse(Category category);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "icon", source = "icon")
    @Mapping(target = "totalCourses", source = "id", qualifiedByName = "mapTotalCourse")
    PopularCategoryResponse mapToPopularCategoryResponse(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapCategoryUpdateRequest(@MappingTarget Category category, CategoryUpdateRequest categoryUpdateRequest);

}
