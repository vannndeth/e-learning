package co.istad.elearningmanagement.features.course.dto;

import jakarta.validation.constraints.NotNull;

public record ThumbnailUpdateRequest(

        @NotNull(message = "Thumbnail is required")
        String thumbnail
){
}
