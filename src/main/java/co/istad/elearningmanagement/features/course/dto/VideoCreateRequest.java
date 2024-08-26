package co.istad.elearningmanagement.features.course.dto;

import java.util.List;

public record VideoCreateRequest(
        Integer sectionOrderNo,
        String lectureNo,
        Integer orderNo,
        String title,
        String fileName
) {
}
