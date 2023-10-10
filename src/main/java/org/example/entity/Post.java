package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "posts", uniqueConstraints = @UniqueConstraint(columnNames = {"title"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post extends CommonEntity {

    @Column(name = "title", unique = true, nullable = false)
    private String title;

    @Column(name = "description", unique = false, nullable = false)
    private String description;

    @Column(name = "content", unique = false, nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
}