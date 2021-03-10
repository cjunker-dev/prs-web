package com.prs.db;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.business.LineItem;
import com.prs.business.User;

public interface LineItemRepo extends JpaRepository<LineItem, Integer> {
	//Spring is creating an implementation for you
	List <LineItem> findAllByRequestId(int requestId);

}
