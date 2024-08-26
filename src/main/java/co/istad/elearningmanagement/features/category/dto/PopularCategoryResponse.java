package co.istad.elearningmanagement.features.category.dto;

import lombok.Builder;

@Builder
public record PopularCategoryResponse(
        String name,
        String icon,
        Integer totalCourses
) {
}
