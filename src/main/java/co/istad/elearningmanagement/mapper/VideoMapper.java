package co.istad.elearningmanagement.mapper;

import co.istad.elearningmanagement.domain.Video;
import co.istad.elearningmanagement.features.course.dto.VideoCreateRequest;
import co.istad.elearningmanagement.features.course.dto.VideoResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    VideoResponse mapToVideoResponse(Video video);

    Video mapRequestToVideo(VideoCreateRequest videoCreateRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapToVideoUpdateRequest(@MappingTarget Video video, VideoCreateRequest videoCreateRequest);
}
