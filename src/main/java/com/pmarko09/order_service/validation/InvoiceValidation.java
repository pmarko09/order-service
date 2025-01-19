package com.pmarko09.order_service.validation;

import com.pmarko09.order_service.exception.invoice.InvoiceNotFoundException;
import com.pmarko09.order_service.model.entity.Invoice;
import com.pmarko09.order_service.repository.InvoiceRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvoiceValidation {

    public static Invoice invoiceExists(InvoiceRepository invoiceRepository, Long orderId) {
        return invoiceRepository.findByOrderId(orderId)
                .orElseThrow(() -> new InvoiceNotFoundException("Invoice for order with id: " + orderId + " not found"));
    }
}