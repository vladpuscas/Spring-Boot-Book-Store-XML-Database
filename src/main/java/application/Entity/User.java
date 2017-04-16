package application.Entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Vlad on 10-Apr-17.
 */
@XmlRootElement
public class User implements UserDetails {
    private String username;
    private String password;
    private String role;
    private List<Book> order = new ArrayList<Book>();
    public User(String username, String password, String role, List<Book> order) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.order = order;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @XmlElement
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(this.getRole());
    }

    public String getPassword() {
        return password;
    }
    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }
    @XmlElement
    public void setRole(String role) {
        this.role = role;
    }

    public List<Book> getOrder() {
        return order;
    }
    @XmlElement
    public void setOrder(List<Book> order) {
        this.order = order;
    }

    public void addBookToOrder(Book book) {
        order.add(book);
    }

    public void removeBookFromOrder(Book book) {
        for(Book b:order){
            if(b.getTitle().equalsIgnoreCase(book.getTitle())){
                order.remove(b);
                break;
            }
        }
    }
}
