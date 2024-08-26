package co.istad.elearningmanagement.util;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class SortUtils {

    public static Sort parseSortOrders(String orders) {
        List<Sort.Order> sortOrders = new ArrayList<>();
        String[] orderConditions = orders.split(",");
        for (String orderCondition : orderConditions) {
            String[] parts = orderCondition.split("\\|");
            if (parts.length == 2) {
                String field = parts[0];
                Sort.Direction direction = "desc".equalsIgnoreCase(parts[1]) ? Sort.Direction.DESC : Sort.Direction.ASC;
                sortOrders.add(new Sort.Order(direction, field));
            }
        }
        return Sort.by(sortOrders);
    }
}
