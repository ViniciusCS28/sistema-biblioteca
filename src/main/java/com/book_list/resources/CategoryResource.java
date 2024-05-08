package com.book_list.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.book_list.dto.CategoryDto;
import com.book_list.entities.Category;
import com.book_list.services.CategoryService;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

	@Autowired
	private CategoryService service;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public ResponseEntity<List<CategoryDto>> findAll() {
		List<Category> list = service.findAll();
		List<CategoryDto> listDto = list.stream().map(x -> convertToDto(x)).collect(Collectors.toList());
		return ResponseEntity.ok(listDto);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDto> findById(@PathVariable Long id) {
		Category obj = service.findById(id);
		return ResponseEntity.ok(convertToDto(obj));
	}
	
	@PostMapping
	public ResponseEntity<CategoryDto> insert(@Valid @RequestBody CategoryDto objDto) {
		Category obj = convertToEntity(objDto);
		obj = service.insert(obj);
		return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(obj));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<CategoryDto> update(@PathVariable Long id, @Valid @RequestBody CategoryDto objDto) {
		Category obj = convertToEntity(objDto);
		obj.setId(id);
		obj = service.update(id, obj);
		return ResponseEntity.ok(convertToDto(obj));
	}
	
	private CategoryDto convertToDto(Category category) {
		CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}
	
	private Category convertToEntity(CategoryDto categoryDto) {
		Category category = modelMapper.map(categoryDto, Category.class);
		return category;
	}
}
