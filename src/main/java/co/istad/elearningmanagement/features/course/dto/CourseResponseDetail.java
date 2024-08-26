package co.istad.elearningmanagement.features.course.dto;

import co.istad.elearningmanagement.domain.Category;
import co.istad.elearningmanagement.domain.Section;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record CourseResponseDetail(
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
        Boolean isFree,
        Boolean isPaid,
        Boolean isDrafted,
        LocalDateTime updatedAt,
        List<SectionResponse> sections
) {
}
