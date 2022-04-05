package us.hypermediocrity.springclean.domain.usecase.transfer;

import java.util.List;

public record InvoiceView(String customerName, String accountNumber, String id, String date, List<LineItemView> items,
    String total) {
}
