package com.book_list.specification;

import java.util.List;

import com.book_list.entities.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

	private static Specification<Book> createSpecification(Filter input) {
		switch (input.getOperator()) {

		case EQUALS:
			return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(criteriaBuilder.lower(root.get(input.getField())),
					(String) input.getValue());

		default:
			throw new RuntimeException("Operation not supported yet");
		}
	}

	public static Specification<Book> getSpecificationFromFilters(List<Filter> filter) {
		Specification<Book> specification = createSpecification(filter.remove(0));
		for (Filter input : filter) {
			specification = specification.and(createSpecification(input));
		}
		return specification;
	}
}
