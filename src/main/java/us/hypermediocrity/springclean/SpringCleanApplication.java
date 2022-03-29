package us.hypermediocrity.springclean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import us.hypermediocrity.springclean.application.Application;
import us.hypermediocrity.springclean.application.ApplicationImpl;
import us.hypermediocrity.springclean.usecase.MakePayment;
import us.hypermediocrity.springclean.usecase.MakePaymentUsecase;

@SpringBootApplication
public class SpringCleanApplication {
  public static void main(String[] args) {
    SpringApplication.run(SpringCleanApplication.class, args);
  }

  // TODO I'd like to do this and still keep ApplicationImpl() package-private.
  // Alternatively, maybe move ApplicationImpl to some other package? Or use
  // Jigsaw modules?
  @Bean
  public Application getApplication() {
    return new ApplicationImpl();
  }

  @Bean
  public MakePayment getMakePayment() {
    return new MakePaymentUsecase();
  }
}
