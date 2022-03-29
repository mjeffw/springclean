package us.hypermediocrity.springclean.domain.port;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import us.hypermediocrity.springclean.domain.entity.Money;

public class InvoiceView {
  private String customerName;
  private String accountNumber;
  private String id;
  private String date;
  private List<LineItemView> items = new ArrayList<>();
  private String total;

  void customerName(String name) {
    this.customerName = name;
  }

  void accountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  void invoiceId(String id) {
    this.id = id;
  }

  void invoiceDate(String date) {
    this.date = date;
  }

  void addLineItem(LineItemView item) {
    items.add(item);
  }

  void invoiceTotal(Money total) {
    this.total = total.toString();
  }

  public String customerName() {
    return customerName;
  }

  public String accountNumber() {
    return accountNumber;
  }

  public String invoiceId() {
    return id;
  }

  public String invoiceDate() {
    return date;
  }

  public String invoiceTotal() {
    return total;
  }

  public List<LineItemView> lineItems() {
    return Collections.unmodifiableList(items);
  }
}
