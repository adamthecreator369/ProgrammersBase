/* Created by Adam Jost on 09/13/2021 */
package com.pb.ProgrammersBase.category;

import javax.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "resource_category_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_sequence"
    )
    @Column(name = "category_id")
    Long id;

    @Column(name = "programming_language")
    String programmingLanguage;

    @Column(name = "category_name")
    String name;

    public Category(){}

    public Category(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public Category(String programmingLanguage, String name) {

        this.programmingLanguage = programmingLanguage;
        this.name = name;
    }

    public Category(Long id, String programmingLanguage, String name) {

        this.id = id;
        this.programmingLanguage = programmingLanguage;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProgrammingLanguage() {
        return programmingLanguage;
    }

    public void setProgrammingLanguage(String programmingLanguage) {
        this.programmingLanguage = programmingLanguage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {

        return "Category{" +
                "id=" + id +
                ", programmingLanguage='" + programmingLanguage + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
