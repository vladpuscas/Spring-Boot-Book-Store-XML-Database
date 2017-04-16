package application;


import application.DAO.Users;
import application.Entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

/**
 * Created by Vlad on 10-Apr-17.
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        SpringApplication.run(Application.class,args);
    }
}
