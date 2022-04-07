package us.hypermediocrity.springclean.adapter.customer;

import java.util.Optional;

import us.hypermediocrity.springclean.domain.entity.Customer;
import us.hypermediocrity.springclean.domain.port.CustomerEntityGateway;

public class CustomersAdapter implements CustomerEntityGateway {
  @Override
  public Optional<Customer> getCustomer(String customerId) {
    // TODO Auto-generated method stub
    return null;
  }
}
