package application.DAO;

import application.Entity.Book;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 10-Apr-17.
 */
@Repository
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Books {
    @XmlElement(name = "book")
    private List<Book> books = new ArrayList<Book>();

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public boolean contains(Book book) {
        for(Book b:books) {
            if(b.getTitle().toLowerCase().equals(book.getTitle().toLowerCase()) && b.getAuthor().toLowerCase().equals(book.getAuthor().toLowerCase()))
                return true;
        }
        return false;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void deleteBook(Book book) {
        books.remove(book);
    }

    public Book getBookByTitle(String title) {
        for(Book b:books) {
            if(b.getTitle().equals(title))
                return b;
        }
        return null;
    }
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(Book b:books) {
            s.append(b.toString() + "\n");
        }
        String rez = s.toString();
        return rez;
    }

    public void updateStock(Book book) {
        for (Book b: books) {
            if(book.getTitle().equals(b.getTitle())) {
                b.setStock(b.getStock()-1);
                break;
            }
        }
    }

    public List<Book> outofStock() {
        List<Book> list = new ArrayList<Book>();
        for(Book b: books) {
            if(b.getStock() == 0) {
                list.add(b);
            }
        }
        return list;
    }
}
