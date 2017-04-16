package application.WebController;

import application.DAO.Books;
import application.DAO.Users;
import application.Entity.Book;
import application.Entity.User;
import application.JaxbController.MarshallController;
import application.JaxbController.UnmarshalController;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 10-Apr-17.
 */
@Controller
@RequestMapping("index")
public class SearchController {

    private Books books;

    private Users users;

    @Autowired
    private UnmarshalController unmarshalController;

    @Autowired
    private MarshallController marshallController;

    @RequestMapping(method = RequestMethod.GET)
    public String showForm() {
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView search(HttpServletRequest request) {
        String param = request.getParameter("search");
        List<Book> result = new ArrayList<Book>();
        try {
            books = unmarshalController.getBooks();
            for(Book b:books.getBooks()) {
                if(b.getAuthor().toLowerCase().contains(param.toLowerCase())
                        || b.getGenre().toLowerCase().contains(param.toLowerCase())
                        || b.getTitle().toLowerCase().contains(param.toLowerCase())) {
                    result.add(b);
                }
            }
        }catch (JAXBException e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView("search/results");
        modelAndView.addObject("result",result);
        return modelAndView;
    }

    @RequestMapping(value = "/order", method = RequestMethod.POST)
    public String addBookToOrder(HttpServletRequest request) {
        String username = request.getRemoteUser();
        String title = request.getParameter("title");
        try {
            users = unmarshalController.getUsers();
            List<User> userList = users.getUsers();
            for(User u: userList) {
                if(u.getUsername().equals(username)) {
                    books = unmarshalController.getBooks();
                    Book book = books.getBookByTitle(title);
                    u.addBookToOrder(book);
                    break;
                }
            }
            users.setUsers(userList);
            marshallController.setUsers(users);
        }catch(JAXBException e) {
            e.printStackTrace();
        }
        return "redirect:/index";
    }


}
