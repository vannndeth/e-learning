package co.istad.elearningmanagement.features.course.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;

public record CourseUpdateRequest(

        String title,

        @Pattern(regexp = "^[a-z0-9]+(-[a-z0-9]+)*$", message = "Slug must be alphanumeric and can contain hyphens")
        String slug,

        String instructor,

        @Pattern(regexp = "^\\d+(\\.\\d+)?$", message = "Price must be a number")
        @Pattern(regexp = "^(0|[1-9]\\d*)(\\.\\d+)?$", message = "Price must be a positive number")
        BigDecimal price,

        Integer discount,
        String category,
        String description,
        String thumbnail,
        String content,

        Boolean isPaid,
        Boolean isDrafted

) {
}
