package us.hypermediocrity.springclean.domain.port;

import java.util.Optional;

import us.hypermediocrity.springclean.domain.entity.Customer;

public interface CustomerPort {
  Optional<Customer> getCustomer(String customerId);
}
