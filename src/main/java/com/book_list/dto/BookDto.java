package com.book_list.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.book_list.entities.Category;

import lombok.Data;

@Data
public class BookDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotBlank(message = "Title is mandatory")
	private String title;
	
	@NotBlank(message = "Author is mandatory")
	private String author;
	
	@NotNull(message = "Year of publication is mandatory")
	private Integer yearPublished;
	
	@NotBlank(message = "Publishing company is mandatory")
	private String publishingCompany;
	
	@NotNull(message = "Edition is mandatory")
	private Integer edition;
	
	@NotBlank(message = "Isbn is mandatory")
	private String isbn;
	
	private String shortDescription;
	
	@NotNull(message = "Number of pages is mandatory")
	private Integer numberOfPages;
	
	@NotNull(message = "Category is mandatory")
	private Category category;
}
