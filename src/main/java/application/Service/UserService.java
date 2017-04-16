package application.Service;

import application.DAO.Users;
import application.Entity.User;
import application.JaxbController.UnmarshalController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.util.List;

/**
 * Created by Vlad on 10-Apr-17.
 */
@Service
public class UserService implements UserDetailsService {
    @Autowired
    private Users users;
    @Autowired
    public UserService() {}

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        try {
            UnmarshalController controller = new UnmarshalController();
            users = controller.getUsers();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        List<User> usersList = users.getUsers();
        User user = null;
        for (User aux:usersList) {
            if(aux.getUsername().equals(s))
                user = aux;
        }

        user.isEnabled();
        return new User(user.getUsername(),user.getPassword(),user.getRole(), user.getOrder());
    }
}
