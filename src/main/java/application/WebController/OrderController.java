package application.WebController;

import application.DAO.Books;
import application.DAO.Users;
import application.Entity.Book;
import application.Entity.User;
import application.JaxbController.MarshallController;
import application.JaxbController.UnmarshalController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.util.List;

/**
 * Created by Vlad on 12-Apr-17.
 */
@Controller
@RequestMapping("order")
public class OrderController {

    private Users users;

    private Books books;

    @Autowired
    private UnmarshalController unmarshalController;

    @Autowired
    private MarshallController marshallController;

    @RequestMapping(value = "showOrder",method = RequestMethod.GET)
    public Model showOrder(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        try{
            users = unmarshalController.getUsers();
            User user = users.getUserByUsername(username);
            List<Book> order = user.getOrder();
            model.addAttribute("order",order);

        }catch (JAXBException e) {
            e.printStackTrace();

        }
        return model;
    }

    @RequestMapping(value = "confirmOrder",method = RequestMethod.POST)
    public String confirmOrder(HttpServletRequest request) {
        try{
            users = unmarshalController.getUsers();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            User user = users.getUserByUsername(username);
            List<Book> order = user.getOrder();
            books = unmarshalController.getBooks();
            List<Book> bookList = books.getBooks();

            for(Book b:order) {
                books.updateStock(b);
            }
            marshallController.setBooks(books);
            users.clearOrder(user);
            marshallController.setUsers(users);


        }catch (JAXBException e) {
            e.printStackTrace();
            return "order/showOrder?error=true";
        }
        return "redirect:/index";
    }

    @RequestMapping(value = "/{title}/remove",method = RequestMethod.GET)
    public String removeBook(@PathVariable String title) {
        try{
            books = unmarshalController.getBooks();
            users = unmarshalController.getUsers();
            Book book = books.getBookByTitle(title);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            users.removeBookFromOUserOrder(username,book);
            marshallController.setUsers(users);
            marshallController.setBooks(books);

        }catch (JAXBException e) {
            e.printStackTrace();
            return "order/showOrder?error=true";
        }
        return "redirect:/order/showOrder";
    }

    @RequestMapping(value = "/clearOrder",method = RequestMethod.GET)
    public String clearOrder() {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            users = unmarshalController.getUsers();
            User user = users.getUserByUsername(username);
            users.clearOrder(user);
            marshallController.setUsers(users);
        }catch (JAXBException e) {
            e.printStackTrace();
            return "order/showOrder?error=true";
        }
        return "redirect:/order/showOrder";
    }
}
