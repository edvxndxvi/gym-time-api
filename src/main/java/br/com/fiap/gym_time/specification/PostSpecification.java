package br.com.fiap.gym_time.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import br.com.fiap.gym_time.models.Post;
import br.com.fiap.gym_time.models.PostFilter;
import jakarta.persistence.criteria.Predicate;

public class PostSpecification {
    public static Specification<Post> withFilters(PostFilter filter){
        return(root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.content() != null) {
                predicates.add(
                        cb.like(root.get("content"), "%" + filter.content() + "%"));
            }

            if (filter.date() != null) {
                predicates.add(
                        cb.equal(root.get("date"), filter.date()));
            }

            var arrayPredicates = predicates.toArray(new Predicate[0]);
            return cb.and(arrayPredicates);
        };
    }
}
