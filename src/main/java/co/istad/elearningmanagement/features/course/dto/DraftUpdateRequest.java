package co.istad.elearningmanagement.features.course.dto;

import jakarta.validation.constraints.NotNull;

public record DraftUpdateRequest (

        @NotNull(message = "Status is required")
        Boolean status
){
}
