package co.istad.elearningmanagement.features.course.dto;

import java.util.List;

public record SectionResponse(
        String title,
        Integer orderNo,
        List<VideoResponse> videos
) {
}
