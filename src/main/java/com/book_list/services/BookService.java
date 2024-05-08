package com.book_list.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.book_list.services.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.book_list.entities.Book;
import com.book_list.repositories.BookRepository;
import com.book_list.specification.BookSpecification;
import com.book_list.specification.Filter;

@Service
public class BookService {

	@Autowired
	private BookRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;

	public List<Book> findAll() {
		return repository.findAll();
	}
	
	public Book findById(Long id) {
		Optional<Book> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public Book insert(Book obj) {
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}
	}
	
	public Book update(Long id, Book obj) {
		Optional<Book> optionalEntity = repository.findById(id);
		if (optionalEntity.isEmpty()) {
			throw new ResourceNotFoundException(id);
		}
		Book entity = optionalEntity.get();
		updateData(entity, obj);
		return repository.save(entity);
	}
	
	private void updateData(Book entity, Book obj) {
		modelMapper.map(obj, entity);
	}
	
	public List<Book> getQueryResult(List<Filter> filters){
        if (filters.size() == 0) {
        	return new ArrayList<Book>();
        }
        return repository.findAll(BookSpecification.getSpecificationFromFilters(filters));    
    }
}
