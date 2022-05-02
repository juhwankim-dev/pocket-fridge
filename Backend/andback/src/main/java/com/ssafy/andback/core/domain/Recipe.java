/**
* RecipeBasic
* 레시비 기본정보 Entity
*
* @author 문관필
* @version 1.0.0
* 생성일 2022/04/27
* 마지막 수정일 2022/04/27
**/
package com.ssafy.andback.core.domain;

import com.ssafy.andback.api.constant.RecipeType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipe")
@NoArgsConstructor
@Getter
public class Recipe {

    // 레시피 아이디
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id", nullable = false)
    private Long recipeId;

    // 요리 이름
    @Column(name = "recipe_food_name", nullable = false)
    private String recipeFoodName;

    // 요리 요약
    @Column(name = "recipe_food_summary", nullable = false)
    private String recipeFoodSummary;

    // 내용
    @Column(name = "recipe_content", nullable = false)
    private String recipeContent;

    // 이미지
    @Column(name = "recipe_image")
    private String recipeImage;

    // 음식 종류
    @Enumerated(EnumType.STRING)
    @Column(name = "recipe_type", nullable = false)
    private RecipeType recipeType;

    // 레시피 재료
    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private List<RecipeIngredient> recipeIngredientList = new ArrayList<RecipeIngredient>();

    // 레시피 과정정보
    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private List<RecipeProcess> recipeProcessList = new ArrayList<RecipeProcess>();

    // 레시피 좋아요
    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    private List<RecipeLike> recipeLikeList = new ArrayList<RecipeLike>();
}