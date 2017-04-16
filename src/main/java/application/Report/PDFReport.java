package application.Report;

import application.Entity.Book;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by Vlad on 14-Apr-17.
 */
public class PDFReport implements Report {
    private static final String file = "src/main/resources/reports/Report.pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    @Override
    public void generateReport(List<Book> books) {
        try{
            Document document = new Document();
            PdfWriter.getInstance(document,new FileOutputStream(file));
            document.open();
            document.addTitle("Books out of stock");
            addBooks(document,books);
            document.close();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void addBooks(Document document, List<Book> books) throws DocumentException {
        Paragraph p = new Paragraph();
        p.add("BOOKS OUT OF STOCK:");
        document.add(p);
        document.add(Chunk.NEWLINE);
        for(Book b:books) {
            Paragraph paragraph = new Paragraph();
            paragraph.add(b.getTitle());
            paragraph.add(",");
            paragraph.add(b.getAuthor());
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
        }
    }
}
