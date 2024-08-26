package co.istad.elearningmanagement.features.category.dto;

import jakarta.validation.constraints.NotEmpty;

public record CategoryCreateRequest(

        @NotEmpty(message = "Category name is required")
        String name,

        String icon
) {
}
