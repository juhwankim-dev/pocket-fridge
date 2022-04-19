/**
* FoodIngredient
* 식재료 정보 Entity
*
* @author 문관필
* @version 1.0.0
* 생성일 2022/04/19
* 마지막 수정일 2022/04/19
**/
package com.ssafy.andback.core.domain;

import com.ssafy.andback.api.constant.WayStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter @Setter
public class FoodIngredient {

    // 식재료 아이디
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long foodIngredientId;

    // 식재료 이름
    @Column(name = "food_ingredient_name", nullable = false)
    private String foodIngredientName;

    // 유통기한
    @Column(name = "food_ingredient_exp", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate foodIngredientExp;

    // 식재로 카테고리
    @Column(name = "food_ingredient_category", nullable = false)
    private String foodIngredientCategory;

    // 식재료 수량
    @Column(name = "food_ingredient_count", nullable = false)
    private int foodIngredientCount;

    // 구입일
    @Column(name = "food_ingredient_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate foodIngredientDate;

    // 보관방법
    @Enumerated(EnumType.STRING)
    @Column(name = "food_ingredient_way")
    private WayStatus foodIngredientWay;

    // 냉장고 아이디(냉장고 1 : 식재료 N)
//    @ManyToOne(targetEntity = Refrigerator.class, fetch = FetchType.LAZY)
//    private Refrigerator refrigeratorId;
}
