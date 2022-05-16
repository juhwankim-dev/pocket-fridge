package com.ssafy.andback.core.queryrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.andback.api.dto.response.QRefrigeratorShareUserResponseDto;
import com.ssafy.andback.api.dto.response.RefrigeratorShareUserResponseDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.ssafy.andback.core.domain.QUser.*;
import static com.ssafy.andback.core.domain.QUserRefrigerator.*;

@RequiredArgsConstructor
public class UserRefrigeratorQueryRepositoryImpl implements UserRefrigeratorQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RefrigeratorShareUserResponseDto> shareUserList(Long refrigeratorId) {

        return jpaQueryFactory
                .select(new QRefrigeratorShareUserResponseDto(
                        user.userNickname, user.userEmail, userRefrigerator.refrigeratorOwner, user.userPicture))
                .from(userRefrigerator)
                .leftJoin(userRefrigerator.user, user)
                .where(userRefrigerator.refrigerator.refrigeratorId.eq(refrigeratorId))
                .fetch();
    }

}
