package com.nttdata.app.account.transaction.controller;

import com.nttdata.app.account.transaction.model.TransactionClientResponse;
import com.nttdata.app.account.transaction.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RestController
@RequestMapping("/app/reports")
@RequiredArgsConstructor
public class ReportController {


    private final ReportService reportService;
    private static final String REQUEST = "request";
    private static final String RESPONSE = "response";

    @GetMapping
    public ResponseEntity<Flux<TransactionClientResponse>> getAccountState(@RequestParam(name = "startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                           @RequestParam(name = "endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                           @RequestParam("client") Long idClient) {
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,"params "+startDate+" - "+endDate+" - client "+idClient,method);
        Flux<TransactionClientResponse> response = reportService.getTransactionReport(startDate, endDate, idClient);
        log.info(RESPONSE,response.count(),method);
        return ResponseEntity.ok(response);
    }

    @GetMapping(value="/pdf",produces = MediaType.APPLICATION_PDF_VALUE)
    public Mono<ResponseEntity<byte[]>> generatePdf(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                             @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                             @RequestParam("client") Long idClient){
        String method = StackWalker.getInstance()
                .walk(frames -> frames
                        .skip(1)
                        .findFirst()
                        .map(StackWalker.StackFrame::getMethodName)
                        .orElse(""));
        log.info(REQUEST,"params "+startDate+" - "+endDate+" - client "+idClient,method);
        return reportService.getTransactionReportPdf(startDate, endDate, idClient)
                .map(pdfBytes -> ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=transacciones.pdf")
                        .contentType(MediaType.APPLICATION_PDF)
                        .body(pdfBytes))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
