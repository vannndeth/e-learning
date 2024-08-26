package co.istad.elearningmanagement.features.course.dto;

public record VideoResponse(
        Integer sectionOrderNo,
        String lectureNo,
        Integer orderNo,
        String title,
        String fileName
) {
}
