package com.ssafy.andback.core.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "refrigerator")
@Getter
@Setter

/**
*
* Refrigerator
* 냉장고 Entity
*
* @author hoony
* @version 1.0.0
* 생성일 2022-04-25
* 마지막 수정일 2022-04-25
**/
public class Refrigerator {

    @Id @Column(name = "refrigereator_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refrigeratorId;

    @Column(name = "refrigerator_name")
    private String refrigeratorName;

    @OneToMany(mappedBy = "refrigerator")
    private List<FoodIngredient> foodIngredientList = new ArrayList<FoodIngredient>();
}
