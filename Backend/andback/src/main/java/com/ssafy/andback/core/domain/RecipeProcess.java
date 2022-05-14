package com.ssafy.andback.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "recipe_process")
@NoArgsConstructor
@Getter
public class RecipeProcess extends BaseEntity {

    // 레시피 과정정보 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_process_id", nullable = false)
    private Long recipeProcessId;

    // 레시피 순서
    @Column(name = "recipe_process_sequence", nullable = false)
    private int recipeProcessSequence;

    // 레시피 설명
    @Column(name = "recipe_process_description", nullable = false)
    private String recipeProcessDescription;

    // 이미지 URL
    @Column(name = "recipe_process_image")
    private String recipeProcessImage;

    // 레시피 아이디
    @ManyToOne(targetEntity = Recipe.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

}
