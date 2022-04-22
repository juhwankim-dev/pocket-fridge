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
public class Refrigerator {

    @Id @Column(name = "refrigereator_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refrigeratorId;

    @Column(name = "refrigerator_name")
    private String refrigeratorName;

    @OneToMany(mappedBy = "refrigerator")
    private List<FoodIngredient> foodIngredientList = new ArrayList<FoodIngredient>();
}
