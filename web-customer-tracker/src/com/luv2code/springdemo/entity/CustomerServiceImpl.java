package com.luv2code.springdemo.entity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.luv2code.springdemo.dao.CustomerDao;
import com.luv2code.springdemo.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	// need to inject customer dao
	@Autowired
	private CustomerDao customerDao;

	@Override
	@Transactional
	public List<Customer> getCustomers(int theSortfield) {

		return customerDao.getCustomers(theSortfield);
	}

	@Transactional
	@Override
	public void saveCustomer(Customer theCustomer) {
		customerDao.saveCustomer(theCustomer);

	}

	@Transactional
	@Override
	public Customer getCustomer(int theId) {

		return customerDao.getCustomer(theId);
	}

	@Transactional
	@Override
	public void deleteCustomer(int theId) {
		customerDao.deleteCustomer(theId);

	}

}
