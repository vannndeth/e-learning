package co.istad.elearningmanagement.features.category.dto;

public record CategoryResponse(
    String id,
    String name,
    String icon,
    Boolean isDisabled
) {
}
