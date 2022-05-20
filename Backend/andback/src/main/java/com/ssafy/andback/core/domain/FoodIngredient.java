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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "food_ingredient")
@Getter
@Setter
@NoArgsConstructor
public class FoodIngredient extends BaseEntity{

    // 식재료 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long foodIngredientId;

    // 식재료 이름
    @Column(name = "food_ingredient_name", nullable = false)
    private String foodIngredientName;

    // 유통기한
    @Column(name = "food_ingredient_exp", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate foodIngredientExp;

    // 식재료 수량
    @Column(name = "food_ingredient_count", nullable = false)
    private float foodIngredientCount;

    // 구입일
    @Column(name = "food_ingredient_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate foodIngredientDate;

    // 보관방법
    @Enumerated(EnumType.STRING)
    @Column(name = "food_ingredient_way")
    private WayStatus foodIngredientWay;

    // 냉장고 아이디(냉장고 1 : 식재료 N)
    @ManyToOne(targetEntity = Refrigerator.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "refrigerator_id")
    private Refrigerator refrigerator;

    // (카테고리 1 : 식재료 N)
    @ManyToOne(targetEntity = SubCategory.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "sub_category_id")
    private SubCategory subCategory;

    @Builder
    public FoodIngredient(String foodIngredientName, LocalDate foodIngredientExp, float foodIngredientCount, LocalDate foodIngredientDate, WayStatus foodIngredientWay, Refrigerator refrigerator, SubCategory subCategory) {
        this.foodIngredientName = foodIngredientName;
        this.foodIngredientExp = foodIngredientExp;
        this.foodIngredientCount = foodIngredientCount;
        this.foodIngredientDate = foodIngredientDate;
        this.foodIngredientWay = foodIngredientWay;
        this.refrigerator = refrigerator;
        this.subCategory = subCategory;
    }
}
