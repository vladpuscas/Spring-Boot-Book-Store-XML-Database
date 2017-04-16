package application.JaxbController;

import application.DAO.Books;
import application.DAO.Users;
import org.springframework.stereotype.Controller;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Created by Vlad on 10-Apr-17.
 */
@Controller
public class MarshallController {

    private File file;

    public void setUsers(Users users) throws JAXBException {
        file = new File("src/main/resources/users.xml");
        if(file.exists() && !file.isDirectory()) {
            JAXBContext jaxbContext  = JAXBContext.newInstance(Users.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            marshaller.marshal(users,file);

        }
    }

    public void setBooks(Books books) throws JAXBException {
        file = new File("src/main/resources/book.xml");
        if(file.exists() && !file.isDirectory()) {
            JAXBContext jaxbContext  = JAXBContext.newInstance(Books.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
            marshaller.marshal(books,file);

        }
    }
}
