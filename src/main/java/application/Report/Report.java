package application.Report;

import application.Entity.Book;

import java.util.List;

/**
 * Created by Vlad on 14-Apr-17.
 */
public interface Report {
    void generateReport(List<Book> books);
}
