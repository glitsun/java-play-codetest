package services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import org.joda.time.format.ISODateTimeFormat;

import com.fasterxml.jackson.databind.JsonNode;

import model.Customer;

@Singleton
public class CustomerServiceImpl implements CustomerService {

	@Override
	public List<Customer> processJsNode(JsonNode jsnode) {
		List<Customer> customerList = new ArrayList<Customer>();
		 jsnode.forEach((jsNode)->{								
			 Customer customer = new Customer(jsNode.findPath("id").asInt(),
					 jsNode.findPath("name").textValue(),
					 ISODateTimeFormat.dateTimeParser().withOffsetParsed().parseDateTime(jsNode.findPath("duetime").textValue()),
					 ISODateTimeFormat.dateTimeParser().withOffsetParsed().parseDateTime(jsNode.findPath("jointime").textValue()));
			 customerList.add(customer);
		 });
		 List<Customer> customerListSorted = customerList.stream().sorted().collect(Collectors.toList());
		 return customerListSorted;
	}

}
