package application.Validator;

import application.Entity.Book;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Vlad on 14-Apr-17.
 */
public class BookValidator {

    public boolean validate(Book book) {
        return validateStrings(book.getAuthor())
                && validateStrings(book.getGenre())
                && validateStrings(book.getTitle())
                && validateStock(book.getStock())
                && validatePrice(book.getPrice());
    }
    private boolean validateStrings(String s) {
        String regx = "^[\\p{L} .'-]+$";
        return s.matches(regx);
    }

    private boolean validateStock(Integer stock) {
        return stock>0;
    }

    private boolean validatePrice(Double price) {
        return price>0.0;
    }
}
