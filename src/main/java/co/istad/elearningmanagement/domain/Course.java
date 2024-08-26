package co.istad.elearningmanagement.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "courses")
public class Course {

    @Id
    private String id;
    private String title;
    private String slug;
    private String description;
    private String thumbnail;
    private String content;
    private BigDecimal price;
    private Integer discount;
    private String category;
    private String code;
    private String instructor;

    private Boolean isPaid;
    private Boolean isDrafted;
    private Boolean isDisabled;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private List<Section> sections;
}
