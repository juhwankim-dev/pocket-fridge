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
    private Long recipeId;

    // 요리 이름
    @Column(name = "recipe_food_name")
    private String recipeFoodName;

    // 요리 요약
    @Column(name = "recipe_food_summary")
    private String recipeFoodSummary;

    // 내용
    @Column(name = "recipe_content")
    private String recipeContent;

    // 이미지
    @Column(name = "recipe_image")
    private String recipeImage;

    // 음식 종류
    @Enumerated(EnumType.STRING)
    @Column(name = "recipe_type")
    private RecipeType recipeType;

    // 레시피 재료
    @OneToMany(mappedBy = "recipe")
    private List<RecipeIngredient> recipeIngredientList = new ArrayList<RecipeIngredient>();
}
