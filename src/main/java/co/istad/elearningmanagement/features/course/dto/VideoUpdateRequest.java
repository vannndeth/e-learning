package co.istad.elearningmanagement.features.course.dto;

import co.istad.elearningmanagement.domain.Video;

import java.math.BigDecimal;
import java.util.List;

public record VideoUpdateRequest(
        String sectionOrderNo,
        List<VideoUpdate> videos
) {
}
