package com.nttdata.app.account.transaction.service;

import com.nttdata.app.account.transaction.model.TransactionClientResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface ReportService {
    Flux<TransactionClientResponse> getTransactionReport(LocalDate startDate, LocalDate endDate, Long idClient);

    Mono<byte[]> getTransactionReportPdf(LocalDate startDate, LocalDate endDate, Long idClient);
}
