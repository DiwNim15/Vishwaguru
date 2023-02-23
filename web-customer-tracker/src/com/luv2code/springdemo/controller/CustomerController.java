package com.luv2code.springdemo.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.entity.FileModel;
import com.luv2code.springdemo.service.CustomerService;
import com.luv2code.springdemo.utils.SortUtils;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	// need to inject customer service
	@Autowired
	private CustomerService customerService;

	@Autowired
	ServletContext context;

	@GetMapping("/list")
	public String listCustomers(Model model, @RequestParam(required = false) String sort) {

		// get customers from service
		List<Customer> theCustomers = null;

		// check for the sort field
		if (sort != null) {
			int theSortField = Integer.parseInt(sort);
			theCustomers = customerService.getCustomers(theSortField);
		} else {
			// no sort field provided...default to sorting by last name
			theCustomers = customerService.getCustomers(SortUtils.LAST_NAME);
		}

		// add the customers to the model
		model.addAttribute("customers", theCustomers);

		return "list-customers";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		// create model attribute to bind data
		Customer theCustomer = new Customer();

		theModel.addAttribute("customer", theCustomer);
		return "customer-form";
	}

	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer) {
		// save the customer using our service
		customerService.saveCustomer(theCustomer);
		return "redirect:/customer/list";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {
		// get the customer fromt the database
		Customer theCustomer = customerService.getCustomer(theId);

		// set customer as a model attribute to pre-populate the form
		theModel.addAttribute(theCustomer);

		// send over to our form

		return "customer-form";

	}

	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId) {
		// delete the customer
		customerService.deleteCustomer(theId);

		return "redirect:/customer/list";
	}

	@RequestMapping(value = "/showFormForUpload", method = RequestMethod.GET)
	public ModelAndView showFormForUpload() {

		FileModel file = new FileModel();
		ModelAndView modelAndView = new ModelAndView("upload-file", "command", file);
		return modelAndView;
	}

}
