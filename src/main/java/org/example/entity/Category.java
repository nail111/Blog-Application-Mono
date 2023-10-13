package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category extends CommonEntity implements Serializable {

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "description", unique = false, nullable = false)
    private String description;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<Post> posts;
}