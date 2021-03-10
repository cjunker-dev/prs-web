package com.prs.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.*;
import com.prs.db.LineItemRepo;
import com.prs.db.ProductRepo;
import com.prs.db.RequestRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/line-items")
public class LineItemController {
	@Autowired
	private LineItemRepo lineItemRepo;
	@Autowired
	private RequestRepo requestRepo;
	//@Autowired
	//private ProductRepo productRepo;

	@GetMapping("/")
	public List<LineItem> getAll() {
		return lineItemRepo.findAll();
	}

	@GetMapping("/{id}")
	public Optional<LineItem> getById(@PathVariable int id) {
		return lineItemRepo.findById(id);
	}

	@PostMapping("/")
	public LineItem create(@RequestBody LineItem lineItem) {
		lineItemRepo.save(lineItem);
		recalculateTotal(lineItem.getRequest());
		return lineItem;
	}

	@PutMapping("/")
	public LineItem update(@RequestBody LineItem lineItem) {
		lineItemRepo.save(lineItem);
		recalculateTotal(lineItem.getRequest());
		return lineItem;
	}

	@DeleteMapping("/{id}")
	public LineItem delete(@PathVariable int id) {
		Optional<LineItem> lineItem = lineItemRepo.findById(id);
		if (lineItem.isPresent()) {
			lineItemRepo.delete(lineItem.get());
			recalculateTotal(lineItem.get().getRequest());
		} else {
			System.out.println("Deletion error: LineItem was not found.");
		}
		return lineItem.get();
	}

	@GetMapping("/lines-for-pr/{id}")
	public List<LineItem> allLineItems(@PathVariable int id) {
		// select * from lineitems where request id = {id}
		return lineItemRepo.findAllByRequestId(id);
	}

	public void recalculateTotal(Request request) {
		
		// when a lineitem is added, changed or deleted, call this method
		List<LineItem> lineItems = lineItemRepo.findAllByRequestId(request.getId());
		double newTotal = 0;
		// WHERE request.id matches lineitem.requestid
		for (LineItem lineItem : lineItems) {
			// for each +=, set new total
			newTotal += (lineItem.getProduct().getPrice()) * (lineItem.getQuantity());
		}
		request.setTotal(newTotal);
		requestRepo.save(request);
	}

}
