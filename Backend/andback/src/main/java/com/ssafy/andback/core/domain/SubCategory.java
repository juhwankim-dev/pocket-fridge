package com.ssafy.andback.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * SubCategory
 * 소분류 카테고리 Entity
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-04-25
 * 마지막 수정일 2022-04-25
 **/

@Entity
@Table(name = "sub_category")
@NoArgsConstructor
@Getter
public class SubCategory extends BaseEntity {

    @Id
    @Column(name = "sub_category_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subCategory;

    @Column(name = "sub_category_name")
    private String subCategoryName;

    @ManyToOne(targetEntity = MainCategory.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "main_category_id")
    private MainCategory mainCategory;

}
