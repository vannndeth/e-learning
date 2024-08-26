package co.istad.elearningmanagement.features.course.dto;

import java.util.List;

public record SectionCreateRequest(
        String title,
        Integer orderNo,
        List<VideoCreateRequest> videos
) {
}
