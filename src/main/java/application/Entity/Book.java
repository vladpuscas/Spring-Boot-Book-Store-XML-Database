package application.Entity;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by Vlad on 10-Apr-17.
 */
@XmlRootElement
public class Book {
    //@XmlAttribute
    //@XmlJavaTypeAdapter(IdAdapter.class)
    //private Integer id;
    private String title;
    private String author;
    private String genre;
    private int stock;
    private Double price;


    public String getTitle() {
        return title;
    }
    @XmlElement
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }
    @XmlElement
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }
    @XmlElement
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getStock() {
        return stock;
    }
    @XmlElement
    public void setStock(int stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }
    @XmlElement
    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                " title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", genre='" + genre + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                '}';
    }
}
