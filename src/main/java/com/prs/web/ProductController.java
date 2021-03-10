package com.prs.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.Product;
import com.prs.business.User;
import com.prs.db.ProductRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/products")
public class ProductController {
	@Autowired
	ProductRepo productRepo;
	
	@GetMapping("/")
	public List<Product> getAll(){
		return productRepo.findAll();	
	}
	
	@GetMapping("/{id}")
	public Optional<Product> getById(@PathVariable int id) {
		return productRepo.findById(id);
	}
	
	@PostMapping("/")
	public Product create(@RequestBody Product product) {
		return productRepo.save(product);
	}
	
	@PutMapping("/")
	public Product update(@RequestBody Product product) {
		return productRepo.save(product);
	}
	
	@DeleteMapping("/{id}")
	public Product delete(@PathVariable int id) {
		Optional <Product> product = productRepo.findById(id);
		if (product.isPresent()) {
			productRepo.deleteById(id);
		}
		else {
			System.out.println("Deletion error - product was not found.");
		}
		return product.get();
	}
}
