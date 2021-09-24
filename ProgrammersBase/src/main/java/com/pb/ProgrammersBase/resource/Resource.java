/* Created by Adam Jost on 09/13/2021 */
package com.pb.ProgrammersBase.resource;

import javax.persistence.*;

import java.util.Arrays;

@Entity
@Table(name = "resources")
public class Resource {

    @Id
    @SequenceGenerator(
            name = "resource_sequence",
            sequenceName = "resource_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "resource_sequence"
    )
    @Column(name = "resource_id")
    Long id;
    @Column(name = "resource_title")
    String title;
    @Column(name = "resource_desc")
    String desc;
    @Column(name = "resource_body")
    String body;
    @Column(name = "resource_category_id")
    Long categoryId;
    @Column(name = "resource_link")
    String link;
    @Column(name = "resource_image")
    byte[] image;

    public Resource() {}

    public Resource(Long id, String title, String desc, String body, Long categoryId, String link, byte[] image) {

        this.id = id;
        this.title = title;
        this.desc = desc;
        this.body = body;
        this.categoryId = categoryId;
        this.link = link;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", body='" + body + '\'' +
                ", categoryCode=" + categoryId +
                ", link='" + link + '\'' +
                ", image=" + Arrays.toString(image) +
                '}';
    }
}