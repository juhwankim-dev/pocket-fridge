/**
* RecipeIngredient
* 레시피 재료 정보 Entity
*
* @author 문관필
* @version 1.0.0
* 생성일 2022/04/28
* 마지막 수정일 2022/04/28
**/
package com.ssafy.andback.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "recipe_ingredient")
@NoArgsConstructor
@Getter
public class RecipeIngredient {

    // 레시피 재료 아이디
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_ingredient_id", nullable = false)
    private Long recipeIngredientId;

    // 레시피 재료 이름
    @Column(name = "recipe_ingredient_name", nullable = false)
    private String recipeIngredientName;

    // 레시피 아이디 (레시피 1 : 레시피 재료 N)
    @ManyToOne(targetEntity = Recipe.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    // 서브 카테고리 아이디 (서브 카테고리 1 : 레시피 식재료 N)
    @ManyToOne(targetEntity = SubCategory.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;


}