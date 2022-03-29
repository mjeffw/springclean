package us.hypermediocrity.springclean.domain.port;

import us.hypermediocrity.springclean.domain.entity.Customer;

public interface CustomerPort {

  Customer getCustomer(String customerId);

}
