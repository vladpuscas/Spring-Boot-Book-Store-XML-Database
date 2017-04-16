package application.DAO;

import application.Entity.User;
import org.springframework.stereotype.Repository;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import application.Entity.Book;

/**
 * Created by Vlad on 10-Apr-17.
 */
@Repository
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Users {
    @XmlElement(name = "user")
    private List<User> users = new ArrayList<User>();

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public boolean add(User user) {
        return users.add(user);
    }

    public boolean remove(User user) {
        return users.remove(user);
    }

    public boolean contains(String username) {
        for(User u:users) {
            if(u.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public User getUserByUsername(String username) {
        for(User u:users) {
            if(u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public void clearOrder(User user) {
        for(User u: users) {
            if(u.getUsername().equals(user.getUsername())) {
                u.setOrder(new ArrayList<Book>());
                break;
            }
        }
    }

    public void removeBookFromOUserOrder(String username,Book book) {
        for(User u:users) {
            if(u.getUsername().equalsIgnoreCase(username)) {
                u.removeBookFromOrder(book);
                break;
            }
        }
    }
}
