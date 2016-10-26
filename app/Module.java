import com.google.inject.AbstractModule;

import services.CustomerService;
import services.CustomerServiceImpl;

public class Module extends AbstractModule {

	@Override
	protected void configure() {
		bind(CustomerService.class).to(CustomerServiceImpl.class);
	}

}
