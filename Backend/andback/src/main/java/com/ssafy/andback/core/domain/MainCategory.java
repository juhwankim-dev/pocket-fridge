package com.ssafy.andback.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
*
* MainCategory
* 대분류 카테고리 Entity
*
* @author hoony
* @version 1.0.0
* 생성일 2022-04-25
* 마지막 수정일 2022-04-25
**/

@Entity
@Table(name = "main_category")
@NoArgsConstructor
@Getter
public class MainCategory {

    @Id
    @Column(name = "main_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mainCategory;


    @Column(name = "main_category_name")
    private String mainCategoryName;


}
