package application.JaxbController;

import application.DAO.Books;
import application.DAO.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Vlad on 10-Apr-17.
 */
@Controller
public class UnmarshalController {

    @Autowired
    private Users users;

    @Autowired
    private Books books;

    private File file;

    public Users getUsers() throws JAXBException {
        file = new File("src/main/resources/users.xml");
        if(file.exists() && !file.isDirectory()) {
            JAXBContext jaxbContext  = JAXBContext.newInstance(Users.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            users = (Users)unmarshaller.unmarshal(file);
            return users;
        }
        else
            return null;
    }

    public Books getBooks() throws JAXBException {
        file = new File("src/main/resources/book.xml");
        if(file.exists() && !file.isDirectory()) {
            JAXBContext jaxbContext  = JAXBContext.newInstance(Books.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            books = (Books)unmarshaller.unmarshal(file);
            return books;
        }
        else
            return null;
    }
}
