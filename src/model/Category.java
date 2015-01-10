package model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Simon on 09/01/2015.
 */
@Entity
@NamedQueries({
        @NamedQuery(
                name = "Category.findAll",
                query = "SELECT c FROM Category c"
        )
})
public class Category {
    private int id;
    private String name;
    private int categoryId;
    private Collection<Work> works;
    private Collection<Work> worksById;

    @Id
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
    @Column(name = "categoryId")
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (categoryId != category.categoryId) return false;
        if (id != category.id) return false;
        if (name != null ? !name.equals(category.name) : category.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + categoryId;
        return result;
    }

    @OneToMany(mappedBy = "category")
    public Collection<Work> getWorks() {
        return works;
    }

    public void setWorks(Collection<Work> works) {
        this.works = works;
    }

    @OneToMany(mappedBy = "category")
    public Collection<Work> getWorksById() {
        return worksById;
    }

    public void setWorksById(Collection<Work> worksById) {
        this.worksById = worksById;
    }
}
