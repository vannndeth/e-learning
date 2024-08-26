package co.istad.elearningmanagement.features.course.web;

import co.istad.elearningmanagement.features.course.CourseService;
import co.istad.elearningmanagement.features.course.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCourse(@RequestBody @Valid CourseCreateRequest courseCreateRequest) {
        courseService.createCourse(courseCreateRequest);
    }

    @PostMapping("/{id}/videos")
    @ResponseStatus(HttpStatus.CREATED)
    public VideoResponse createVideo(@PathVariable String id, @RequestBody @Valid VideoCreateRequest videoCreateRequest) {
        return courseService.createVideo(id, videoCreateRequest);
    }

    @PostMapping("/{id}/sections")
    @ResponseStatus(HttpStatus.CREATED)
    public SectionResponse createSection(@PathVariable String id, @RequestBody @Valid SectionCreateRequest sectionCreateRequest) {
        return courseService.createSection(id, sectionCreateRequest);
    }

    @PostMapping("/filters")
    @ResponseStatus(HttpStatus.OK)
    public Page<?> courseBodyFilters(
            @RequestBody FilterDto filterDto,
            @RequestParam(defaultValue = "SNIPPET") Response response,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return courseService.courseBodyFilters(filterDto, response, page, size);
    }

    @GetMapping("/filters")
    @ResponseStatus(HttpStatus.OK)
    public Page<?> getCourseFilters(
            @RequestParam(required = false) String filterAnd,
            @RequestParam(required = false) String filterOr,
            @RequestParam(required = false) String orders,
            @RequestParam(defaultValue = "SNIPPET") Response response,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return courseService.getCourseFilters(filterAnd, filterOr, orders, response, page, size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CourseResponse getCourseById(@PathVariable String id) {
        return courseService.getCourseById(id);
    }

    @GetMapping("/slug/{slug}")
    @ResponseStatus(HttpStatus.OK)
    public CourseResponse getCourseBySlug(@PathVariable String slug, @RequestParam(defaultValue = "SNIPPET") Response response) {
        return courseService.getCourseBySlug(response, slug);
    }

    @GetMapping("/instructor/{instructor}")
    @ResponseStatus(HttpStatus.OK)
    public List<CourseResponse> getCourseByInstructor(@PathVariable String instructor) {
        return courseService.getCourseByInstructor(instructor);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<?> getAllCourses(
            @RequestParam(defaultValue = "SNIPPET") Response response,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return courseService.getAllCourses(response, page, size);
    }

    @GetMapping("/public")
    @ResponseStatus(HttpStatus.OK)
    public Page<?> getCoursePublic(
            @RequestParam(defaultValue = "SNIPPET") Response response,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return courseService.getCoursePublic(response, page, size);
    }

    @GetMapping("/private")
    @ResponseStatus(HttpStatus.OK)
    public Page<?> getCoursePrivate(
            @RequestParam(defaultValue = "SNIPPET") Response response,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return courseService.getCoursePrivate(response, page, size);
    }

    @GetMapping("/free")
    @ResponseStatus(HttpStatus.OK)
    public Page<?> getCourseFree(
            @RequestParam(defaultValue = "SNIPPET") Response response,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return courseService.getCourseFree(response, page, size);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCourse(@PathVariable String id, @RequestBody @Valid CourseUpdateRequest courseUpdateRequest) {
        courseService.updateCourse(id, courseUpdateRequest);
    }

    @PutMapping("/{id}/visibilities")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCourseDraft(@PathVariable String id, @RequestBody @Valid DraftUpdateRequest draftUpdateRequest) {
        courseService.updateCourseDraft(id, draftUpdateRequest);
    }

    @PutMapping("/{id}/is-paid")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateIsPaid(@PathVariable String id, @RequestBody @Valid IsPaidUpdateRequest isPaidUpdateRequest) {
        courseService.updateIsPaid(id, isPaidUpdateRequest);
    }

    @PutMapping("/{id}/thumbnails")
    public void updateThumbnail(@PathVariable String id, @RequestBody @Valid ThumbnailUpdateRequest thumbnailUpdateRequest) {
        courseService.updateThumbnail(id, thumbnailUpdateRequest);
    }

    @PutMapping("/{id}/videos")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVideo(@PathVariable String id, @RequestBody @Valid VideoUpdateRequest videoUpdateRequest) {
        courseService.updateVideo(id, videoUpdateRequest);
    }

    @PutMapping("/{id}/enable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enableCourse(@PathVariable String id) {
        courseService.enableCourse(id);
    }

    @PutMapping("/{id}/disable")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disableCourse(@PathVariable String id) {
        courseService.disableCourse(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
    }


}
