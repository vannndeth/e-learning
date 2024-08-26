package co.istad.elearningmanagement.base;

import co.istad.elearningmanagement.features.course.dto.FilterDto;
import co.istad.elearningmanagement.util.FilterUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BaseFilter<T> {

    public Query buildQuery(FilterDto filterDto, Class<T> entityClass) {
        if (filterDto == null || filterDto.getSpecsDto() == null || filterDto.getSpecsDto().isEmpty()) {
            return new Query();
        }

        List<Criteria> criteriaList = filterDto.getSpecsDto().stream()
                .map(spec -> createCriteria(spec, entityClass))
                .toList();

        Criteria criteria = new Criteria();
        if (filterDto.getGlobalOperator() == FilterDto.GlobalOperator.AND) {
            criteria.andOperator(criteriaList.toArray(new Criteria[0]));
        } else {
            criteria.orOperator(criteriaList.toArray(new Criteria[0]));
        }

        return new Query().addCriteria(criteria);
    }

    private Criteria createCriteria(FilterDto.SpecsDto specs, Class<T> entityClass) {
        Object parsedValue = FilterUtils.parseValue(entityClass, specs.getColumn(), specs.getValue());
        Criteria criteria = Criteria.where(specs.getColumn());

        switch (specs.getOperation()) {
            case EQUAL:
                return criteria.is(parsedValue);
            case LIKE:
                return criteria.regex(".*" + parsedValue + ".*", "i");
            case IN:
                return criteria.in(parsedValue);
            case GREATER_THAN:
                return criteria.gt(parsedValue);
            case LESS_THAN:
                return criteria.lt(parsedValue);
            case BETWEEN:
                return criteria.gte(FilterUtils.parseValue(entityClass, specs.getColumn(), specs.getValues().get(0)))
                        .lte(FilterUtils.parseValue(entityClass, specs.getColumn(), specs.getValues().get(1)));
            case EXISTS:
                return criteria.exists(Boolean.parseBoolean(specs.getValue()));
            case NE:
                return criteria.ne(parsedValue);
            case SIZE:
                return criteria.size(Integer.parseInt(specs.getValue()));
            case ELEMENT_MATCH:
                return criteria.elemMatch(Criteria.where(specs.getSubField()).is(parsedValue));
            default:
                throw new IllegalArgumentException("Unsupported operation: " + specs.getOperation());
        }
    }
}
