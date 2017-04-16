package application.Report;

import application.Entity.Book;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Vlad on 14-Apr-17.
 */
public class CSVReport implements Report {
    private static final String file = "src/main/resources/reports/Report.csv";
    @Override
    public void generateReport(List<Book> books) {
        try{
            FileWriter writer = new FileWriter(file);
            CSVUtils.writeLine(writer, Arrays.asList("Title","Author"));

            for(Book b:books) {
                List<String> list = new ArrayList<String>();
                list.add(b.getTitle());
                list.add(b.getAuthor());
                CSVUtils.writeLine(writer,list);
            }

            writer.flush();
            writer.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
