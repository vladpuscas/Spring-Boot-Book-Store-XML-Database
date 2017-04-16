package application.WebController;

import application.DAO.Books;
import application.DAO.Users;
import application.Entity.Book;
import application.Entity.User;
import application.JaxbController.MarshallController;
import application.JaxbController.UnmarshalController;
import application.Report.Report;
import application.Report.ReportFactory;
import application.Validator.BookValidator;
import application.Validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 10-Apr-17.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UnmarshalController unmarshalController;

    @Autowired
    private MarshallController marshallController;

    private Books books;

    private Users users;

    @RequestMapping(value = "addBook",method = RequestMethod.GET)
    public String showForm(ModelAndView modelAndView) {
        return "admin/addBook";
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public String registerUser(HttpServletRequest request) {
        Books books;
        Book book = new Book();
        book.setTitle(request.getParameter("title"));
        book.setPrice(Double.parseDouble(request.getParameter("price")));
        book.setStock(Integer.parseInt(request.getParameter("stock")));
        book.setGenre(request.getParameter("genre"));
        book.setAuthor(request.getParameter("author"));

        BookValidator validator = new BookValidator();
        try {
            books = unmarshalController.getBooks();
            if(!books.contains(book) && validator.validate(book)) {
                books.addBook(book);
                marshallController.setBooks(books);
            }
            else
                return "redirect:/admin/addBook?error=true";

        }catch(JAXBException e) {
            e.printStackTrace();
            return "redirect:/admin/addBook?error=true";
        }
        return "/index";
    }

    @RequestMapping(value = "search",method = RequestMethod.GET)
    public String showForm() {
        return "admin/search";
    }

    @RequestMapping(value = "search",method = RequestMethod.POST)
    public ModelAndView search(HttpServletRequest request) {
        String param = request.getParameter("search");
        List<Book> result = new ArrayList<Book>();
        try {
            books = unmarshalController.getBooks();
            for(Book b:books.getBooks()) {
                if(b.getAuthor().contains(param) || b.getGenre().contains(param) || b.getTitle().contains(param)) {
                    result.add(b);
                }
            }
        }catch (JAXBException e) {
            e.printStackTrace();
        }
        ModelAndView modelAndView = new ModelAndView("admin/results");
        modelAndView.addObject("result",result);
        return modelAndView;
    }

    @RequestMapping(value = "/{title}/edit",method = RequestMethod.GET)
    public String edit(@PathVariable String title, Model model) {
        try{
            books = unmarshalController.getBooks();
            Book book = books.getBookByTitle(title);
            model.addAttribute("book",book);
        }catch (JAXBException e) {
            e.printStackTrace();
            return "redirect:/admin/edit?error=true";
        }

        return "admin/edit";
    }

    @RequestMapping(value = "update",method = RequestMethod.POST)
    public String update(HttpServletRequest request) {
        String title = request.getParameter("title");
        try {
            books = unmarshalController.getBooks();
            List<Book> bookList = books.getBooks();
            BookValidator validator = new BookValidator();
            Integer stock = Integer.parseInt(request.getParameter("stock"));
            Double price = Double.parseDouble(request.getParameter("price"));
            Book book = books.getBookByTitle(title);
            book.setStock(stock);
            book.setPrice(price);
            if(validator.validate(book)) {
                for(Book b:bookList) {
                    if(b.getTitle().equals(title)) {
                        b.setStock(stock);
                        b.setPrice(price);
                        break;
                    }
                }
                books.setBooks(bookList);
                marshallController.setBooks(books);
            }

        } catch (JAXBException e) {
            e.printStackTrace();
            return "redirect:/admin/edit?error=true";
        }

        return "redirect:/admin";
    }

    @RequestMapping(value = "/{title}/delete",method = RequestMethod.GET)
    public String delete(@PathVariable String title) {
        try {
            books = unmarshalController.getBooks();
            Book book = books.getBookByTitle(title);
            books.deleteBook(book);
            marshallController.setBooks(books);
        } catch (JAXBException e) {
            e.printStackTrace();
            return "redirect:/admin?error=true";
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "addUser",method = RequestMethod.GET)
    public ModelAndView showFrom() {
        return new ModelAndView("admin/addUser");
    }

    @RequestMapping(value = "addUser",method = RequestMethod.POST)
    public String addUser(HttpServletRequest request) {
        try {
            String username = request.getParameter("username");
            users = unmarshalController.getUsers();
            UserValidator validator = new UserValidator();
            if(!users.contains(username)) {
                User user = new User();
                user.setUsername(username);
                user.setPassword(request.getParameter("password"));
                user.setRole(request.getParameter("role"));
                if(validator.validate(user)) {
                    users.add(user);
                    marshallController.setUsers(users);
                }
                else
                    return "redirect:/admin/addUser?error=true";

            }
            else
                return "redirect:/admin/addUser?error=true";
        } catch (JAXBException e) {
            e.printStackTrace();
            return "redirect:/admin/addUser?error=true";
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "searchUser",method = RequestMethod.GET)
    public String showSearch() {
        return "admin/searchUser";
    }

    @RequestMapping(value = "searchUser",method = RequestMethod.POST)
    public ModelAndView getResults(HttpServletRequest request) {
        String param = request.getParameter("search");
        List<User> result = new ArrayList<User>();
        try{
            users = unmarshalController.getUsers();
            for(User u:users.getUsers()) {
                if(u.getUsername().toLowerCase().contains(param.toLowerCase()))
                    result.add(u);
            }
        }catch (JAXBException e) {
            e.printStackTrace();

        }
        ModelAndView modelAndView = new ModelAndView("admin/userResults");
        modelAndView.addObject("result",result);
        return modelAndView;
    }

    @RequestMapping(value = "users/{username}/edit",method = RequestMethod.GET)
    public String editUser(@PathVariable String username, Model model) {
        try{
            users = unmarshalController.getUsers();
            User user = users.getUserByUsername(username);
            model.addAttribute("user",user);
        }catch (JAXBException e) {
            e.printStackTrace();
            return "redirect:/admin/users/edit?error=true";
        }
        return "admin/users/edit";
    }

    @RequestMapping(value = "updateUser",method = RequestMethod.POST)
    public String updateUser(HttpServletRequest request) {
        String username = request.getParameter("username");
        try{
            users = unmarshalController.getUsers();
            List<User> newUser = users.getUsers();
            for(User u:newUser) {
                if(u.getUsername().equals(username)) {
                    u.setRole(request.getParameter("role"));
                    u.setPassword(request.getParameter("password"));
                    break;
                }
            }
            users.setUsers(newUser);
            marshallController.setUsers(users);
        }catch (JAXBException e) {
            e.printStackTrace();
            return "redirect:/admin/users/edit?error=true";
        }
        return "redirect:/admin";
    }
    @RequestMapping(value = "users/{username}/delete",method = RequestMethod.GET)
    public String deleteUser(@PathVariable String username) {
        try {
            users = unmarshalController.getUsers();
            User user = users.getUserByUsername(username);
            users.remove(user);
            marshallController.setUsers(users);
        }catch (JAXBException e) {
            e.printStackTrace();
            return "redirect:/admin/users/delete?error=true";
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "report",method = RequestMethod.POST)
    public String generateReport(HttpServletRequest request) {
        String type = request.getParameter("format");
        ReportFactory factory = new ReportFactory();
        Report report = factory.getReport(type);
        try {
            books = unmarshalController.getBooks();
            report.generateReport(books.outofStock());

        }catch (JAXBException e) {
            e.printStackTrace();
            return "redirect:/admin/report?error=true";
        }
        return "redirect:/admin";
    }
}
