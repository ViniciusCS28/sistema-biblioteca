package com.book_list.resources.exceptions.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer statusCode;
	private List<String> message;
}
