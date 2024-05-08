package com.book_list.specification;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Filter {

	private String field;
	private QueryOperator operator;
	private Object value;
}
