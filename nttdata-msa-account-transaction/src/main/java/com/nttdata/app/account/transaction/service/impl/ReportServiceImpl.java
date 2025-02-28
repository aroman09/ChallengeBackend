package com.nttdata.app.account.transaction.service.impl;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.nttdata.app.account.transaction.exception.ExceptionResponse;

import com.nttdata.app.account.transaction.model.TransactionClientResponse;
import com.nttdata.app.account.transaction.service.ReportService;
import com.nttdata.app.account.transaction.service.TransactionService;
import com.nttdata.app.account.transaction.utils.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final TransactionService transactionService;
    @Override
    public Flux<TransactionClientResponse> getTransactionReport(LocalDate startDate, LocalDate endDate, Long idClient) {
        validateDates(startDate,endDate);
        LocalDateTime startOfDay = startDate.atStartOfDay();
        LocalDateTime endOfDay = endDate.atTime(23, 59, 59);
        return transactionService.getAllTransactionByDates(startOfDay,endOfDay,idClient);
    }

    @Override
    public Mono<byte[]> getTransactionReportPdf(LocalDate startDate, LocalDate endDate, Long idClient) {
        validateDates(startDate,endDate);
        LocalDateTime startOfDay = startDate.atStartOfDay();
        LocalDateTime endOfDay = endDate.atTime(23, 59, 59);
        return transactionService.getAllTransactionByDates(startOfDay,endOfDay,idClient)
                .collectList()
                .flatMap(this::crearPDF);
    }

    private Mono<byte[]> crearPDF(List<TransactionClientResponse> transactions) {
        return Mono.fromCallable(() -> {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            PdfFont boldFont = PdfFontFactory.createFont(com.itextpdf.io.font.constants.StandardFonts.HELVETICA_BOLD);

            // Título centrado y en negrita
            Paragraph title = new Paragraph("Estado de Cuenta")
                    .setFont(boldFont)
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            Table table = new Table(UnitValue.createPercentArray(new float[]{10, 20, 15, 10, 10, 10, 10, 10}))
                    .useAllAvailableWidth();

            String[] headers = {"Fecha", "Cliente", "Número Cuenta", "Tipo", "Saldo Inicial", "Estado", "Movimiento", "Saldo Disponible"};
            for (String header : headers) {
                table.addHeaderCell(new Cell()
                        .add(new Paragraph(header).setFont(boldFont))
                        .setTextAlignment(TextAlignment.CENTER));
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (TransactionClientResponse transaction : transactions) {
                table.addCell(new Cell().add(new Paragraph(transaction.getDateTransaction().format(formatter)))
                        .setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(transaction.getName())).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(transaction.getNumber())).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(transaction.getTypeAccount().toString())).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(transaction.getInitialBalance()))).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(transaction.getStatus()))).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(transaction.getMount()))).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(transaction.getBalance()))).setTextAlignment(TextAlignment.CENTER));
            }
            document.add(table);
            document.close();
            return baos.toByteArray();
        });
    }

    private void validateDates(LocalDate startDate, LocalDate endDate){
        if(startDate.isAfter(endDate))
            throw new ExceptionResponse(HttpStatus.NOT_ACCEPTABLE, Message.ERROR_DATES);
    }

}
