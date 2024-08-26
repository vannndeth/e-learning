package co.istad.elearningmanagement.features.course.dto;

import co.istad.elearningmanagement.domain.Video;

import java.util.List;

public record VideoUpdate(
    Integer sectionOrderNo,
    String lectureNo,
    Integer orderNo,
    String title,
    String fileName

) {
}
