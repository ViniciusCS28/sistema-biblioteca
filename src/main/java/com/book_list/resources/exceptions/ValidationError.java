package com.book_list.resources.exceptions;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"status_code", "message"})
public class ValidationError implements Serializable {
	private static final long serialVersionUID = 1L;

	@JsonProperty("status_code")
	private Integer statusCode;
	private List<String> message;
}
