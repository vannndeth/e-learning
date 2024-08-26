package co.istad.elearningmanagement.features.course;

import co.istad.elearningmanagement.base.BaseFilter;
import co.istad.elearningmanagement.domain.Category;
import co.istad.elearningmanagement.domain.Course;
import co.istad.elearningmanagement.domain.Section;
import co.istad.elearningmanagement.domain.Video;
import co.istad.elearningmanagement.features.category.CategoryRepository;
import co.istad.elearningmanagement.features.course.dto.*;
import co.istad.elearningmanagement.mapper.CourseMapper;
import co.istad.elearningmanagement.mapper.SectionMapper;
import co.istad.elearningmanagement.mapper.VideoMapper;
import co.istad.elearningmanagement.util.FilterUtils;
import co.istad.elearningmanagement.util.ResponseUtils;
import co.istad.elearningmanagement.util.SortUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CategoryRepository categoryRepository;
    private final CourseMapper courseMapper;
    private final SectionMapper sectionMapper;
    private final VideoMapper videoMapper;
    private final BaseFilter<Course> baseFilter;

    private final MongoTemplate mongoTemplate;

    @Override
    public void createCourse(CourseCreateRequest courseCreateRequest) {

        if(courseRepository.existsByTitle(courseCreateRequest.title())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course already exists");
        }

        Category category = categoryRepository.findByName(courseCreateRequest.category()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category doesn't exist!"));

        Course course = courseMapper.mapRequestToCourse(courseCreateRequest);

        course.setCategory(category.getName());
        course.setIsDrafted(true);
        course.setIsPaid(false);
        courseRepository.save(course);
    }

    @Override
    public VideoResponse createVideo(String id, VideoCreateRequest videoCreateRequest) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course doesn't exist!"));

        // Find the section within the course based on sectionOrderNo
        Section section = course.getSections().stream()
                .filter(s -> s.getOrderNo().equals(videoCreateRequest.sectionOrderNo()))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Section doesn't exist!"));

        // Map VideoCreateRequest to Video entity using VideoMapper
        Video newVideo = videoMapper.mapRequestToVideo(videoCreateRequest);

        // Add the new video to the section's video list
        section.getVideos().add(newVideo);

        // Update the course in the repository
        courseRepository.save(course);

        // Convert the newly added Video to a VideoResponse DTO and return
        return videoMapper.mapToVideoResponse(newVideo);


    }

    @Override
    public SectionResponse createSection(String id, SectionCreateRequest sectionCreateRequest) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course doesn't exist!"));

        // Map SectionCreateRequest to Section entity using SectionMapper
        Section newSection = sectionMapper.mapRequestToSection(sectionCreateRequest);

        // Map each VideoCreateRequest to Video entity using VideoMapper
        List<Video> videos = sectionCreateRequest.videos().stream()
                .map(videoMapper::mapRequestToVideo)
                .toList();
        newSection.setVideos(videos);

        // Add the new section to the course's section list
        course.getSections().add(newSection);

        // Update the course in the repository
        courseRepository.save(course);

        // Convert the newly added Section to a SectionResponse DTO and return
        return sectionMapper.mapToSectionResponse(newSection);

    }


    @Override
    public CourseResponse getCourseById(String id) {
        return courseRepository.findById(id)
                .map(courseMapper::mapToCourseResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course doesn't exist!"));
    }

    @Override
    public CourseResponse getCourseBySlug(Response response, String slug) {

        return courseRepository.findAll()
                .stream()
                .filter(course -> course.getSlug().equals(slug))
                .map(courseMapper::mapToCourseResponse)
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course doesn't exist!"));
    }

    @Override
    public Page<?> courseBodyFilters(FilterDto filterDto, Response response, int page, int size) {

        // Build the query with filters
        Query query = baseFilter.buildQuery(filterDto, Course.class);

        // Create a Pageable object for pagination
        Pageable pageable = PageRequest.of(page, size);

        // Apply pagination to the query
        List<Course> courses = mongoTemplate.find(query.with(pageable), Course.class);

        // Get the total count of records without pagination
        long totalRecords = mongoTemplate.count(query, Course.class);

        // Map courses to the appropriate DTOs
        return ResponseUtils.mapCoursesResponse(courses, response, courseMapper, pageable, totalRecords);
    }


    @Override
    public Page<?> getCourseFilters(String filterAnd, String filterOr, String orders, Response response, int page, int size) {
        Query query = new Query();

        // Add AND filters
        if (filterAnd != null && !filterAnd.isEmpty()) {
            List<Criteria> andCriteria = FilterUtils.parseFilterCriteria(filterAnd);
            query.addCriteria(new Criteria().andOperator(andCriteria.toArray(new Criteria[0])));
        }

        // Add OR filters
        if (filterOr != null && !filterOr.isEmpty()) {
            List<Criteria> orCriteria = FilterUtils.parseFilterCriteria(filterOr);
            query.addCriteria(new Criteria().orOperator(orCriteria.toArray(new Criteria[0])));
        }

        // Add sorting
        if (orders != null && !orders.isEmpty()) {
            Sort sort = SortUtils.parseSortOrders(orders);
            query.with(sort);
        }

        // Apply pagination
        PageRequest pageRequest = PageRequest.of(page, size);
        query.with(pageRequest);

        // Execute query to get courses
        List<Course> courses = mongoTemplate.find(query, Course.class);

        // Clone the query for count operation to avoid conflict
        Query countQuery = Query.of(query).limit(-1).skip(-1);
        long totalRecords = mongoTemplate.count(countQuery, Course.class);

        // Map the results using ResponseUtils
        return ResponseUtils.mapCoursesResponse(courses, response, courseMapper, pageRequest, totalRecords);
    }


    @Override
    public List<CourseResponse> getCourseByInstructor(String instructor) {

        return courseRepository.findAll()
                .stream()
                .filter(course -> course.getInstructor().equals(instructor))
                .map(courseMapper::mapToCourseResponse)
                .toList();
    }

    @Override
    public Page<?> getAllCourses(Response response, int page, int size) {

        // Create a Pageable object for pagination
        Pageable pageable = PageRequest.of(page, size);

        // Fetch courses with pagination
        List<Course> courses = courseRepository.findAll(pageable).getContent();

        // Get the total count of courses
        long totalRecords = courseRepository.count();

        // Map courses to the appropriate DTOs
        return ResponseUtils.mapCoursesResponse(courses, response, courseMapper, pageable, totalRecords);
    }


    @Override
    public Page<?> getCoursePublic(Response response, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // Fetch paginated and filtered courses from the repository
        Page<Course> coursePage = courseRepository.findByIsDraftedFalse(pageable);

        long totalRecords = coursePage.getTotalElements();

        // Map courses to the appropriate DTOs
        return ResponseUtils.mapCoursesResponse(coursePage.getContent(), response, courseMapper, pageable, totalRecords);
    }

    @Override
    public Page<?> getCoursePrivate(Response response, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // Fetch paginated and filtered courses from the repository
        Page<Course> coursePage = courseRepository.findByIsDraftedTrue(pageable);

        long totalRecords = coursePage.getTotalElements();

        // Map courses to the appropriate DTOs
        return ResponseUtils.mapCoursesResponse(coursePage.getContent(), response, courseMapper, pageable, totalRecords);
    }

    @Override
    public Page<?> getCourseFree(Response response, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // Fetch paginated and filtered courses from the repository
        Page<Course> coursePage = courseRepository.findByIsPaidFalse(pageable);

        long totalRecords = coursePage.getTotalElements();

        // Map courses to the appropriate DTOs
        return ResponseUtils.mapCoursesResponse(coursePage.getContent(), response, courseMapper, pageable, totalRecords);
    }


    @Override
    public void updateCourse(String id, CourseUpdateRequest courseUpdateRequest) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course doesn't exist!"));

        courseMapper.mapCourseUpdateRequest(course, courseUpdateRequest);
        courseRepository.save(course);
    }

    @Override
    public void updateCourseDraft(String id, DraftUpdateRequest draftUpdateRequest) {
            Course course = courseRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            "Course doesn't exist!"));

            course.setIsDrafted(draftUpdateRequest.status());
            courseRepository.save(course);
    }

    @Override
    public void updateIsPaid(String id, IsPaidUpdateRequest isPaidUpdateRequest) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course doesn't exist!"));

        course.setIsPaid(isPaidUpdateRequest.status());
        courseRepository.save(course);
    }

    @Override
    public void updateThumbnail(String id, ThumbnailUpdateRequest thumbnailUpdateRequest) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course doesn't exist!"));

        course.setThumbnail(thumbnailUpdateRequest.thumbnail());
        courseRepository.save(course);
    }

    @Override
    public void updateVideo(String id, VideoUpdateRequest videoUpdateRequest) {

    }

    @Override
    public void enableCourse(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course doesn't exist!"));

        course.setIsDisabled(false);
        courseRepository.save(course);
    }

    @Override
    public void disableCourse(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course doesn't exist!"));

        course.setIsDisabled(true);
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Course doesn't exist!"));
        courseRepository.delete(course);
    }

}
