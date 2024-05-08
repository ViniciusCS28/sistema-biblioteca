package com.book_list.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.book_list.dto.BookDto;
import com.book_list.entities.Book;
import com.book_list.services.BookService;
import com.book_list.specification.Filter;
import com.book_list.specification.QueryOperator;

@RestController
@RequestMapping(value = "/books")
public class BookResource {

	@Autowired
	private BookService service;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public ResponseEntity<List<BookDto>> findAll() {
		List<Book> list = service.findAll();
		List<BookDto> listDto = list.stream().map(x -> convertToDto(x)).collect(Collectors.toList());
		return ResponseEntity.ok(listDto);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<BookDto> findById(@PathVariable Long id) {
		Book obj = service.findById(id);
		return ResponseEntity.ok(convertToDto(obj));
	}
	
	@PostMapping
	public ResponseEntity<BookDto> insert(@Valid @RequestBody BookDto objDto) {
		Book obj = convertToEntity(objDto);
		obj = service.insert(obj);
		return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(obj));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<BookDto> update(@PathVariable Long id, @Valid @RequestBody BookDto objDto) {
		Book obj = convertToEntity(objDto);
		obj.setId(id);
		obj = service.update(id, obj);
		return ResponseEntity.ok(convertToDto(obj));
	}
	
	@GetMapping(value = "/search")
	public ResponseEntity<List<Book>> query(@RequestParam(name = "title", required = false) String title, @RequestParam(name = "isbn", required = false) String isbn) {
		Filter titleLike = Filter.builder()
				.field("title")
				.operator(QueryOperator.EQUALS)
				.value(title.toLowerCase())
				.build();
		Filter isbnLike = Filter.builder()
				.field("isbn")
				.operator(QueryOperator.EQUALS)
				.value(isbn)
				.build();
		List<Filter> filters = new ArrayList<>();
		if (titleLike.getValue() != null) {
			filters.add(titleLike);
		}
		if (isbnLike.getValue() != null) {
			filters.add(isbnLike);
		}
		List<Book> list = service.getQueryResult(filters);
		return ResponseEntity.ok(list);
	}
	
	private BookDto convertToDto(Book book) {
		BookDto bookDto = modelMapper.map(book, BookDto.class);
		return bookDto;
	}
	
	private Book convertToEntity(BookDto bookDto) {
		Book book = modelMapper.map(bookDto, Book.class);
		return book;
	}
}
