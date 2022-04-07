package us.hypermediocrity.springclean.adapter.restapi;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import us.hypermediocrity.springclean.domain.port.Invoices;

@SpringBootTest
@AutoConfigureMockMvc
class InvoiceControllerTest {
  @Autowired MockMvc mockMvc;

  @MockBean Invoices invoices;

  @Test
  void testPing() throws Exception {
    mockMvc.perform(get("/ping")).andDo(print()).andExpect(content().string(containsString("running")));
  }

  @Test
  void testViewInvoiceBadNumber() throws Exception {
    Mockito.when(invoices.getInvoice("badinvoice")).thenReturn(Optional.empty());
    mockMvc.perform(get("/invoice/badinvoice")).andDo(print()).andExpect(status().isNotFound());
  }

  @Test
  void testViewSimpleInvoice() throws Exception {
    mockMvc.perform(get("/invoice/badinvoice")).andDo(print()).andExpect(status().isNotFound());
  }
}
