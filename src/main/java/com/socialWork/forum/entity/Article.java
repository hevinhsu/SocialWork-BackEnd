package com.socialWork.forum.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article implements Serializable {

    public static final Integer HIDDEN = 0;
    public static final Integer PUBLISHED = 1;
    public static final Integer EDITED = 2;
    public static final Integer DELETED = 3;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
    @NotNull
    private String title;
    private String detail;
    @Column(name = "createTime", nullable = false)
    Timestamp createTime;
    @Column(name = "updateTime")
    Timestamp updateTime;
    @Column(name = "status", nullable = false)
    private Integer status;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "articleTypeId")
    private ArticleType articleType;
}
