package com.gini.iordache.service;

import com.gini.iordache.entity.order.ServiceOrder;

public interface PdfService {
    void createPDFInvoice(ServiceOrder serviceOrder);
}
