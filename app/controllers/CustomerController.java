package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import javax.inject.Inject;


import com.fasterxml.jackson.databind.JsonNode;

import model.Customer;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import services.CustomerService;
import views.html.*;

public class CustomerController extends Controller {

	@Inject
	HttpExecutionContext ec;

	@Inject
	CustomerService customerService;

	@BodyParser.Of(BodyParser.Json.class)
	public CompletionStage<Result> sort() {

		return CompletableFuture.supplyAsync(() -> {
			return customerService.processJsNode(request().body().asJson());
			
		}, ec.current()).thenApply(customerSortedList -> ok(Json.toJson(customerSortedList)));
	}

	public Result uploadview() {
		return ok(uploadview.render());
	}

	public CompletionStage<Result> upload() throws FileNotFoundException {
		MultipartFormData<File> body = request().body().asMultipartFormData();
		FilePart<File> customerFile = body.getFile("customer");
		if (customerFile != null) {
			File file = customerFile.getFile();
			JsonNode jsNode = Json.parse(new FileInputStream(file));
			
			return CompletableFuture.supplyAsync(() -> {
				List<Customer> customerList = customerService.processJsNode(jsNode);
				return customerList;
			}, ec.current()).thenApply(customerSortedList -> ok(Json.toJson(customerSortedList)));
			
		} else {
			flash("error", "Missing file");
			return CompletableFuture.completedFuture(badRequest());
		}
	}

}
