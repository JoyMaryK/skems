package com.mj.ToPDF;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mj.skems.inventoryRecords.InventoryRecords;

@Component
public class Pdf {
    private List<InventoryRecords> listIssued;
     
    public Pdf(List<InventoryRecords> listIssued) {
        this.listIssued = listIssued;
    }
 
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(CMYKColor.BLUE);
        cell.setPadding(4);
         
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(CMYKColor.WHITE);
         
        cell.setPhrase(new Phrase("Registration Number", font)); 
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Student Name", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Item", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Date Booked", font));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Issued by", font));
        table.addCell(cell);
         
            
    }
     
    private void writeTableData(PdfPTable table) {
        for (InventoryRecords inventoryRecords : listIssued) {
            table.addCell(inventoryRecords.getRegNo());
            table.addCell(inventoryRecords.getFirstName());
            table.addCell(inventoryRecords.getItem());
            table.addCell(inventoryRecords.getDateBooked());
            table.addCell(inventoryRecords.getStaffIssued());

            
        }
    }
     
    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
         
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(CMYKColor.BLACK);
         
        Paragraph head = new Paragraph("EGERTON UNIVERSITY", font);
        head.setAlignment(Paragraph.ALIGN_CENTER);
        Paragraph head2 = new Paragraph("SPORTS AND GAMES DEPARTMENT", font);
        head2.setAlignment(Paragraph.ALIGN_CENTER);
        Paragraph p = new Paragraph("List of Defaulters", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
         
       document.add(head); 
       document.add(head2);
        document.add(p);
         
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 1.5f});
        table.setSpacingBefore(10);
         
        writeTableHeader(table);
        writeTableData(table);
         
        document.add(table);
         
        document.close();
         
    }
}
