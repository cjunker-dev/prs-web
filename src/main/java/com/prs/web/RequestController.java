package com.prs.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prs.business.*;
import com.prs.db.RequestRepo;

@CrossOrigin
@RestController
@RequestMapping("/api/requests")
public class RequestController {
	@Autowired
	private RequestRepo requestRepo;
	
	@GetMapping("/")
	public List<Request> getAll(){
		return requestRepo.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Request> getById(@PathVariable int id) {
		return requestRepo.findById(id);
	}
	
	@PostMapping("/")
	public Request create(@RequestBody Request request) {
		//set status to new
		request.setStatus("New");
		//set total to 0
		request.setTotal(0);
		//set submitted date to current datetime
		LocalDateTime dateSubmitted = LocalDateTime.now();
		request.setSubmittedDate(dateSubmitted);
		//set reason for rejection to null
		request.setReasonForRejection(null);
		return requestRepo.save(request);
	}
	
	@PutMapping("/")
	public Request update(@RequestBody Request request) {
		return requestRepo.save(request);
	}
	
	@DeleteMapping("/{id}")
	public Request delete(@PathVariable int id){
		Optional<Request> request = requestRepo.findById(id);
		if (request.isPresent()) {
			requestRepo.delete(request.get());
		}
		else {
			System.out.println("Deletion error: Request was not found.");
		}
		return request.get();
	}
	
	@PutMapping("/submit-review")
	public Request submitReview(@RequestBody Request request) {
		//if total is less than 50, change status to approved,
		if (request.getTotal() <= 50) {
			request.setStatus("approved");
		}
		//else change status to review
		else {
			request.setStatus("review");
		}
		//set submittedDate as currentDate
		//sql format: 'YYYY-MM-DD hh:mm:ss.mmm'
		LocalDateTime timeSubmitted = LocalDateTime.now();
		request.setSubmittedDate(timeSubmitted);
		//save this!!!
		requestRepo.save(request);
		return request;	
	}
	
	@GetMapping("/list-review/{id}")
	public List<Request> getReview(@PathVariable int id){
		//call custom method that gets all requests in review status and userID != currentID
		//make an array from request repo to iterate over
		List<Request> requestList = requestRepo.findAll();
		List<Request> reviewable = new ArrayList<>();
		for (Request request: requestList) {
			if (request.getStatus().equalsIgnoreCase("review") && request.getUser().getId() != id) {
				//add item to requestList
				reviewable.add(request);
			}
		}
		return reviewable;
	}
	
	@PutMapping("/approve")
	public Request approve(@RequestBody Request request) {
		request.setStatus("Approved");
		return request;
	}
	
	@PutMapping("/reject")
	public Request reject(@RequestBody Request request) {
		request.setStatus("Rejected");
		return request;
	}
}
