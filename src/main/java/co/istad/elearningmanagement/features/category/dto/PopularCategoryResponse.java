package co.istad.elearningmanagement.features.category.dto;

public record PopularCategoryResponse(
        String name,
        String icon,
        Integer totalCourses
) {
}
