package com.ssafy.andback.core.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Refrigerator
 * 냉장고 Entity
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-04-25
 * 마지막 수정일 2022-04-25
 **/

@Entity
@Table(name = "refrigerator")
@NoArgsConstructor
@Getter
@Setter
public class Refrigerator {

    @Id
    @Column(name = "refrigereator_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long refrigeratorId;

    @Column(name = "refrigerator_name")
    private String refrigeratorName;

    @OneToMany(mappedBy = "refrigerator")
    private List<FoodIngredient> foodIngredientList = new ArrayList<FoodIngredient>();

    @Builder
    public Refrigerator(String refrigeratorName) {
        this.refrigeratorName = refrigeratorName;
    }


    public void updateName(String refrigeratorName) {
        this.refrigeratorName = refrigeratorName;
    }
}
