package co.istad.elearningmanagement.mapper;

import co.istad.elearningmanagement.domain.Course;
import co.istad.elearningmanagement.domain.Section;
import co.istad.elearningmanagement.features.course.CourseRepository;
import co.istad.elearningmanagement.features.course.dto.SectionResponse;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomMapper {

    private final CourseRepository courseRepository;
    private final SectionMapper sectionMapper;

    @Named("mapTotalCourse")
    public Integer mapTotalCourse(String categoryId) {
        // Add logging here to check if the categoryId is being correctly passed
        System.out.println("Counting courses for category ID: " + categoryId);
        Integer count = courseRepository.countByCategory(categoryId);
        System.out.println("Total courses found: " + count);
        return count;
    }

    @Named("mapSectionResponse")
    public List<SectionResponse> mapSectionResponse(List<Section> sections) {

        return sections.stream().map(sectionMapper::mapToSectionResponse).collect(Collectors.toList());
    }

}
