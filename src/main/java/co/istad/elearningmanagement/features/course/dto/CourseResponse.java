package co.istad.elearningmanagement.features.course.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CourseResponse(
    String id,
    String title,
    String slug,
    String description,
    String thumbnail,
    String content,
    String instructor,
    BigDecimal price,
    Integer discount,
    String category,
    Boolean isPaid,
    Boolean isDrafted,
    LocalDateTime updatedAt,
    LocalDateTime createdAt
) {
}
