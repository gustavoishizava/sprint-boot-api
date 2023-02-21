package com.example.apitest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.apitest.dto.*;

@SpringBootApplication
@RestController
@RequestMapping("/customers")
public class ApitestApplication {

	@Autowired
	private CustomerRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ApitestApplication.class, args);
	}

	@PostMapping()
	public ResponseEntity Create(@RequestBody() CustomerDto dto) {
		var customer = new Customer(dto.firstName, dto.lastName);

		repository.save(customer);

		return ResponseEntity.ok().body(customer);
	}

	@PutMapping()
	public ResponseEntity Update(@RequestBody() CustomerUpdateDto dto) {
		var customer = repository.findById(dto.id).get();
		customer.firstName = dto.firstName;
		customer.lastName = dto.lastName;

		repository.save(customer);

		return ResponseEntity.noContent().build();
	}

	@GetMapping()
	public List<Customer> Get() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public Customer Get(@PathVariable String id) {
		return repository.findById(id).get();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity Delete(@PathVariable String id) {
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
