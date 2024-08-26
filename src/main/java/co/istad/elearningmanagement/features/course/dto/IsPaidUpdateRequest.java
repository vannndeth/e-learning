package co.istad.elearningmanagement.features.course.dto;

import jakarta.validation.constraints.NotNull;

public record IsPaidUpdateRequest(

        @NotNull(message = "Status is required")
        Boolean status
){
}
