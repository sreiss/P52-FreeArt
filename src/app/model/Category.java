package app.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Simon on 09/01/2015.
 */
@Entity
@Table(
        name = "category",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id", "name", "displayName"})
)
public class Category {
    private int id;
    private String name;
    private String displayName;
    private Collection<Work> works;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
    @Column(name = "displayName")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != category.id) return false;
        if (name != null ? !name.equals(category.name) : category.name != null) return false;
        if (displayName != null ? !displayName.equals(category.displayName) : category.displayName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "category")
    public Collection<Work> getWorks() {
        return works;
    }

    public void setWorks(Collection<Work> works) {
        this.works = works;
    }
}
