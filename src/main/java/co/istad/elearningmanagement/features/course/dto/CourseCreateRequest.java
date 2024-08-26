package co.istad.elearningmanagement.features.course.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record CourseCreateRequest(

        @NotNull(message = "Title is required")
        String title,

        @NotNull(message = "Slug is required")
        @Pattern(regexp = "^[a-z0-9]+(-[a-z0-9]+)*$", message = "Slug must be alphanumeric and can contain hyphens")
        String slug,

        String description,
        String thumbnail,
        String content,

        @NotNull(message = "Instructor is required")
        String instructor,

        @NotNull(message = "Price is required")
        @Pattern(regexp = "^\\d+(\\.\\d+)?$", message = "Price must be a number")
        @Pattern(regexp = "^(0|[1-9]\\d*)(\\.\\d+)?$", message = "Price must be a positive number")
        BigDecimal price,

        @NotNull(message = "Category is required")
        String category
) {
}
