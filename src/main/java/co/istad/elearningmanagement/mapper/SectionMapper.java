package co.istad.elearningmanagement.mapper;

import co.istad.elearningmanagement.domain.Section;
import co.istad.elearningmanagement.features.course.dto.SectionCreateRequest;
import co.istad.elearningmanagement.features.course.dto.SectionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SectionMapper {

    SectionResponse mapToSectionResponse(Section section);

    Section mapRequestToSection(SectionCreateRequest sectionCreateRequest);

}
