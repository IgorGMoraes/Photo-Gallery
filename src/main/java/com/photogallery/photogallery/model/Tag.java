package com.photogallery.photogallery.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Tag {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Photo> photos = new HashSet<>();

    @ManyToMany(mappedBy = "tags")
    private Set<Album> albums = new HashSet<>();

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
