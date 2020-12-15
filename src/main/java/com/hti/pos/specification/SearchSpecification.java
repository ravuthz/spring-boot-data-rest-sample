package com.hti.pos.specification;

import com.hti.pos.domain.Setting;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


public class SearchSpecification<T> implements Specification<T> {

    private List<SearchCriteria> list;

    public SearchSpecification() {
        this.list = new ArrayList<>();
    }

    public SearchSpecification(List<SearchCriteria> list) {
        this.list = list;
    }

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        for (SearchCriteria criteria : list) {
            String key = criteria.getKey();
            String value = String.valueOf(criteria.getValue());

            switch (criteria.getOperation()) {
                case GREATER_THAN:
                    predicates.add(builder.greaterThan(root.get(key), value));
                    break;
                case LESS_THAN:
                    predicates.add(builder.lessThan(root.get(key), value));
                    break;
                case GREATER_THAN_EQUAL:
                    predicates.add(builder.greaterThanOrEqualTo(root.get(key), value));
                    break;
                case LESS_THAN_EQUAL:
                    predicates.add(builder.lessThanOrEqualTo(root.get(key), value));
                    break;
                case NOT_EQUAL:
                    predicates.add(builder.notEqual(root.get(key), value));
                    break;
                case EQUAL:
                    predicates.add(builder.equal(root.get(key), value));
                    break;
                case MATCH:
                    predicates.add(builder.like(builder.lower(root.get(key)), "%" + value.toLowerCase() + "%"));
                    break;
                case MATCH_END:
                    predicates.add(builder.like(builder.lower(root.get(key)), value.toLowerCase() + "%"));
                    break;
                case MATCH_START:
                    predicates.add(builder.like(builder.lower(root.get(key)), "%" + value.toLowerCase()));
                    break;
                case IN:
                    predicates.add(builder.in(root.get(key)).value(value));
                    break;
                case NOT_IN:
                    predicates.add(builder.not(root.get(key)).in(value));
                    break;
                default:
                    throw new IllegalStateException("Unexpected search operation value: " + criteria.getOperation());
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        }
        return null;
    }
}
