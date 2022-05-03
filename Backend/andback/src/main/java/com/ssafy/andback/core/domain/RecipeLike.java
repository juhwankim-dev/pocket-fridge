package com.ssafy.andback.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "recipe_like")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class RecipeLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeLikeId;

    // 레시피 아이디 (레시피 1 : 좋아요 N)
    @ManyToOne(targetEntity = Recipe.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    // 유저 아이디(유저 1 : 좋아요 N)
    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public RecipeLike(Recipe recipe, User user) {
        this.recipe = recipe;
        this.user = user;
    }
}
