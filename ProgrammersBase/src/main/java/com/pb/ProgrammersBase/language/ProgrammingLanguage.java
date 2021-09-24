/* Created by Adam Jost on 09/13/2021 */
package com.pb.ProgrammersBase.language;

import javax.persistence.*;

@Entity
@Table(name = "programming_languages")
public class ProgrammingLanguage {

    @Id
    @SequenceGenerator(
            name = "programming_language_sequence",
            sequenceName = "programming_language_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "programming_language_sequence"
    )
    @Column(name = "pl_id")
    Long id;

    @Column(name = "pl_name")
    String name;

    public ProgrammingLanguage() {}

    public ProgrammingLanguage(String name) {
        this.name = name;
    }

    public ProgrammingLanguage(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProgrammingLanguage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
