package co.istad.elearningmanagement.util;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class FilterUtils {


    /**
     * This code below use for body filter util
     * @param entityClass
     * @param column
     * @param value
     * @return
     */
    public static <T> Object parseValue(Class<T> entityClass, String column, String value) {
        try {
            Field field = entityClass.getDeclaredField(column);
            return parseBasedOnType(field.getType(), value);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("Invalid column: " + column, e);
        }
    }

    private static Object parseBasedOnType(Class<?> type, String value) {
        if (type == String.class) return value;
        if (type == Integer.class || type == int.class) return Integer.parseInt(value);
        if (type == Double.class || type == double.class) return Double.parseDouble(value);
        if (type == Boolean.class || type == boolean.class) return Boolean.parseBoolean(value);
        if (type == LocalDate.class) return LocalDate.parse(value);
        if (type == LocalTime.class) return LocalTime.parse(value);
        if (type == LocalDateTime.class) return LocalDateTime.parse(value);

        throw new IllegalArgumentException("Unsupported type: " + type.getSimpleName());
    }


    /**
     * This code below use for param filter util
     * @param filter
     * @return
     */
    public static List<Criteria> parseFilterCriteria(String filter) {
        List<Criteria> criteriaList = new ArrayList<>();
        String[] conditions = filter.split(",");

        for (String condition : conditions) {
            String[] parts = condition.split("\\|");
            if (parts.length == 3) {
                String field = parts[0];       // Field name, e.g., "name", "address", etc.
                String operator = parts[1];    // Operator, e.g., "eq", "gt", "regex", etc.
                String value = parts[2];       // Value to compare against, e.g., "mengseu", "pp", etc.

                switch (operator.toLowerCase()) {
                    case "eq":  // Equals
                        criteriaList.add(Criteria.where(field).is(value));
                        break;
                    case "ne":  // Not Equals
                        criteriaList.add(Criteria.where(field).ne(value));
                        break;
                    case "gt":  // Greater Than
                        criteriaList.add(Criteria.where(field).gt(value));
                        break;
                    case "lt":  // Less Than
                        criteriaList.add(Criteria.where(field).lt(value));
                        break;
                    case "gte": // Greater Than or Equal To
                        criteriaList.add(Criteria.where(field).gte(value));
                        break;
                    case "lte": // Less Than or Equal To
                        criteriaList.add(Criteria.where(field).lte(value));
                        break;
                    case "in":  // In List (multiple values separated by ";")
                        criteriaList.add(Criteria.where(field).in(value.split(";")));
                        break;
                    case "nin": // Not In List (multiple values separated by ";")
                        criteriaList.add(Criteria.where(field).nin(value.split(";")));
                        break;
                    case "regex": // Regular Expression (case-insensitive)
                        criteriaList.add(Criteria.where(field).regex(value, "i"));
                        break;
                    case "exists": // Field Exists (true/false)
                        criteriaList.add(Criteria.where(field).exists(Boolean.parseBoolean(value)));
                        break;
                    default:
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid operator: " + operator);
                }
            }
        }
        return criteriaList;
    }
}
