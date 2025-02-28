package com.nttdata.app.account.transaction.controller;

import com.nttdata.app.account.transaction.model.TransactionClientResponse;
import com.nttdata.app.account.transaction.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("/app/reports")
@RequiredArgsConstructor
public class ReportController {


    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<Flux<TransactionClientResponse>> getAccountState(@RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                           @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                           @RequestParam("client") Long idClient) {
        return ResponseEntity.ok(reportService.getTransactionReport(startDate, endDate, idClient));
    }

    @GetMapping(value="/pdf",produces = MediaType.APPLICATION_PDF_VALUE)
    public Mono<ResponseEntity<byte[]>> generatePdf(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                             @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                             @RequestParam("client") Long idClient){
        return reportService.getTransactionReportPdf(startDate, endDate, idClient)
                .map(pdfBytes -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transacciones.pdf")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(pdfBytes))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
