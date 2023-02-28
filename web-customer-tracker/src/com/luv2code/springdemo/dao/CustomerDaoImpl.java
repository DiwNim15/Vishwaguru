package com.luv2code.springdemo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.utils.SortUtils;

@Repository
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Customer> getCustomers(int theSortField) {

		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();

		// determine sort field
		String theFieldNameString = null;

		switch (theSortField) {
		case SortUtils.FIRST_NAME:
			theFieldNameString = "firstName";
			break;
		case SortUtils.LAST_NAME:
			theFieldNameString = "lastName";
			break;
		case SortUtils.EMAIL:
			theFieldNameString = "email";
			break;
		default:
			theFieldNameString = "lastName";

		}
		// create a query..sort by theSortfield
		Query<Customer> theQuery = currentSession.createQuery("from Customer order by " + theFieldNameString,
				Customer.class);

		// execute the query and get result list
		List<Customer> customers = theQuery.getResultList();

		// return the results

		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {

		Session currSession = sessionFactory.getCurrentSession();

		currSession.saveOrUpdate(theCustomer);

	}

	@Override
	public Customer getCustomer(int theId) {
		Session currSession = sessionFactory.getCurrentSession();

		Customer theCustomer = currSession.get(Customer.class, theId);

		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {

		Session currSession = sessionFactory.getCurrentSession();

//		Customer theCustomer = currSession.get(Customer.class, theId);
//
//		currSession.delete(theCustomer);

		Query theQuery = currSession.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", theId);
		theQuery.executeUpdate();
	}

}
