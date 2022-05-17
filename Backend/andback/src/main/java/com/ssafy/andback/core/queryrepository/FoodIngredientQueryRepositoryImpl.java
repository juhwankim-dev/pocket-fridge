package com.ssafy.andback.core.queryrepository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.andback.core.domain.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.querydsl.jpa.JPAExpressions.*;


@RequiredArgsConstructor
public class FoodIngredientQueryRepositoryImpl implements FoodIngredientQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<FoodIngredient> findAllIngredientList(User user) {

        return jpaQueryFactory
                .selectFrom(QFoodIngredient.foodIngredient)
                .where(QFoodIngredient.foodIngredient.refrigerator.in(
                        select(QRefrigerator.refrigerator)
                                .from(QUserRefrigerator.userRefrigerator)
                                .leftJoin(QUserRefrigerator.userRefrigerator.refrigerator, QRefrigerator.refrigerator)
                                .where(QUserRefrigerator.userRefrigerator.user.eq(user))
                ))
                .fetch();
    }
}
