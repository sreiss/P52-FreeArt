package model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Simon on 09/01/2015.
 */
@Entity
@Table(name = "author", uniqueConstraints = @UniqueConstraint(columnNames = {"id", "login"}))
public class Author {
    private String login;
    private String email;
    private String name;
    private String firstName;
    private int id;
    private Collection<Work> works;

    @Basic
    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;

        if (id != author.id) return false;
        if (email != null ? !email.equals(author.email) : author.email != null) return false;
        if (firstName != null ? !firstName.equals(author.firstName) : author.firstName != null) return false;
        if (login != null ? !login.equals(author.login) : author.login != null) return false;
        if (name != null ? !name.equals(author.name) : author.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + id;
        return result;
    }

    @OneToMany(mappedBy = "author")
    public Collection<Work> getWorks() {
        return works;
    }

    public void setWorks(Collection<Work> works) {
        this.works = works;
    }
}
