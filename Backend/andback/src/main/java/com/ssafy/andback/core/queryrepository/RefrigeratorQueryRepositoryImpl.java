package com.ssafy.andback.core.queryrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.andback.core.domain.Refrigerator;
import com.ssafy.andback.core.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}
