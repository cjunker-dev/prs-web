package com.prs.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.Vendor;
import com.prs.db.VendorRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/vendors")
public class VendorController {
	@Autowired
	VendorRepo vendorRepo;
	
	@GetMapping("/")
	public List<Vendor> getAll(){
		return vendorRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Vendor> getById(@PathVariable int id) {
		return vendorRepo.findById(id);
	}
	
	@PostMapping("/")
	public Vendor create(@RequestBody Vendor vendor) {
		return vendorRepo.save(vendor);
	}
	
	@PutMapping("/")
	public Vendor update(@RequestBody Vendor vendor) {
		return vendorRepo.save(vendor);
	}
	
	@DeleteMapping("/{id}")
	public Vendor delete(@PathVariable int id) {
		Optional <Vendor> vendor = vendorRepo.findById(id);
		if (vendor.isPresent()) {
			vendorRepo.delete(vendor.get());
		}
		else {
			System.out.println("Deletion error, user was not found.");
		}
		return vendor.get();
	}
	
	
}
