package co.istad.elearningmanagement.mapper;

import co.istad.elearningmanagement.domain.Course;
import co.istad.elearningmanagement.domain.Section;
import co.istad.elearningmanagement.features.course.dto.*;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {CustomMapper.class})
public interface CourseMapper {

    CourseResponse mapToCourseResponse(Course course);

    @Mapping(target = "sections", source = "sections", qualifiedByName = "mapSectionResponse")
    CourseResponseDetail mapToCourseResponseDetail(Course course);

    Course mapRequestToCourse(CourseCreateRequest courseCreateRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapCourseUpdateRequest(@MappingTarget Course course, CourseUpdateRequest courseUpdateRequest);

}
