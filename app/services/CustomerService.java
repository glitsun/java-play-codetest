package services;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import model.Customer;

public interface CustomerService {
	
	public List<Customer> processJsNode(JsonNode jsnode);

}
