package com.ssafy.andback.core.queryrepository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.andback.api.dto.response.QRecipeIngredientRequireCountDto;
import com.ssafy.andback.api.dto.response.RecipeIngredientRequireCountDto;
import com.ssafy.andback.core.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ssafy.andback.core.domain.QRecipe.*;
import static com.ssafy.andback.core.domain.QRefrigerator.*;
import static com.ssafy.andback.core.domain.QUserRefrigerator.*;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RefrigeratorQueryRepositoryImpl implements RefrigeratorQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Refrigerator> findAllRefrigeratorByUser(User user) {
        return query.select(refrigerator)
                .from(refrigerator)
                .rightJoin(userRefrigerator)
                .on(refrigerator.refrigeratorId.eq(userRefrigerator.refrigerator.refrigeratorId))
                .where(userRefrigerator.user.userId.eq(user.getUserId()))
                .fetch();
    }


    public Map<Long, Long> findAllRefrigeratorIngredientCount(User user) {

        List<RecipeIngredientRequireCountDto> list = query.select(new QRecipeIngredientRequireCountDto(
                        recipe.recipeId,
                        recipe.recipeId.count()
                )).from(QRecipeIngredient.recipeIngredient)
                .leftJoin(QRecipeIngredient.recipeIngredient.recipe, recipe)
                .groupBy(recipe.recipeId)
                .fetch();

        /**
         * SELECT ri.recipe_id, COUNT(ri.recipe_id) AS category
         * FROM recipe_ingredient ri
         * LEFT JOIN recipe
         * ON ri.recipe_id = recipe.recipe_id
         *
         *
         * WHERE ri.sub_category_id IN (
         *
         *
         * 	SELECT DISTINCT fi.sub_category_id
         * 	FROM food_ingredient fi
         * 	WHERE fi.refrigerator_id IN (
         * 		SELECT r.refrigereator_id
         * 		FROM user_refrigerator ur
         * 		LEFT JOIN refrigerator r
         * 		ON ur.refrigerator_id = r.refrigereator_id
         * 		WHERE ur.user_id = 2)
         * 	)
         * GROUP BY ri.recipe_id;
         */

        QFoodIngredient fi = new QFoodIngredient("fi");
        QRefrigerator r = new QRefrigerator("r");
        QUserRefrigerator ur = new QUserRefrigerator("ur");

        QRecipeIngredient ri = new QRecipeIngredient("ri");
        QRecipe recipe = new QRecipe("recipe");

        List<RecipeIngredientRequireCountDto> list2 = query.select(new QRecipeIngredientRequireCountDto(
                        recipe.recipeId,
                        recipe.recipeId.count()
                ))
                .from(ri)
                .leftJoin(ri.recipe, recipe)
                .where(ri.subCategory.in(
                        JPAExpressions.select(fi.subCategory).distinct()
                                .from(fi)
                                .where(fi.refrigerator.in(
                                        query.select(r)
                                                .from(ur)
                                                .leftJoin(ur.refrigerator, r)
                                                .where(ur.user.eq(user))
                                                .fetch()
                                ))
                ))
                .groupBy(recipe.recipeId)
                .fetch();


        Map<Long, Long> result = new HashMap<>();
        for (RecipeIngredientRequireCountDto temp : list) {
            result.put(temp.getRecipeId(), temp.getCount());
        }


        for (RecipeIngredientRequireCountDto temp : list2) {
            result.put(temp.getRecipeId(), result.get(temp.getRecipeId()) - temp.getCount());
        }

        return result;
    }


}
