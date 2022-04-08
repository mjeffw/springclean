package us.hypermediocrity.springclean.domain.usecase.common;

import java.util.List;

public record InvoiceInfo(String customerName, String accountNumber, String id, String date, List<LineItemInfo> items,
    String total) {
}
