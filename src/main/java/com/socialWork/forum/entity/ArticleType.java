package com.socialWork.forum.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleType implements Serializable {

    public static final Integer DISABLE = 0;
    public static final Integer ENABLE = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "articleTypeId")
    private Long articleId;
    @NotNull
    private String typeName;
    @NotNull
    private Integer status;
}
