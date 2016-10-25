package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.joda.time.format.ISODateTimeFormat;

import model.Customer;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;


public class CustomerController extends Controller {
	
	 @Inject 
	 HttpExecutionContext ec;

	 @BodyParser.Of(BodyParser.Json.class)
	 public CompletionStage<Result> sort() {	
		 
		return CompletableFuture.supplyAsync(() -> {
			List<Customer> customerList = new ArrayList<Customer>();		 
			request().body().asJson().forEach((jsNode)->{								
									 Customer customer = new Customer(jsNode.findPath("id").asInt(),
											 jsNode.findPath("name").textValue(),
											 ISODateTimeFormat.dateTimeParser().withOffsetParsed().parseDateTime(jsNode.findPath("duetime").textValue()),
											 ISODateTimeFormat.dateTimeParser().withOffsetParsed().parseDateTime(jsNode.findPath("jointime").textValue()));
									 customerList.add(customer);
								 });
			List<Customer> customerListSorted = customerList.stream().sorted().collect(Collectors.toList());
			return customerListSorted;
		}, ec.current()).thenApply(customerSortedList -> ok(Json.toJson(customerSortedList)));		
	 }
}
