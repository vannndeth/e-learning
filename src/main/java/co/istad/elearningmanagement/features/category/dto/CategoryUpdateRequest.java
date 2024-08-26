package co.istad.elearningmanagement.features.category.dto;

import jakarta.validation.constraints.NotEmpty;

public record CategoryUpdateRequest(

        @NotEmpty(message = "Category name is required")
        String name,

        String icon
) {
}
