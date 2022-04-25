package com.ssafy.andback.core.queryrepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
import com.ssafy.andback.core.domain.QUserRefrigerator;
>>>>>>> S06P31D206-164 [be] feat: :sparkles: 유저 냉장고 리스트 조회 구현
=======
>>>>>>> S06P31D206-164 [be] feat: :sparkles: 냉장고 등록 기능 추가
=======
>>>>>>> f8ba250e0e51ac6821d19fc723e14b5216871bfd
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
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> f8ba250e0e51ac6821d19fc723e14b5216871bfd
                .on(refrigerator.refrigeratorId.eq(userRefrigerator.refrigerator.refrigeratorId))
                .where(userRefrigerator.user.userId.eq(user.getUserId()))
                .fetch();
    }

<<<<<<< HEAD
=======
                .on(refrigerator.eq(userRefrigerator.refrigerator))
                .where(userRefrigerator.userRefrigeratorId.eq(user.getUserId()))
                .fetch();
    }
>>>>>>> S06P31D206-164 [be] feat: :sparkles: 유저 냉장고 리스트 조회 구현
=======
                .on(refrigerator.refrigeratorId.eq(userRefrigerator.refrigerator.refrigeratorId))
                .where(userRefrigerator.user.userId.eq(user.getUserId()))
                .fetch();
    }

>>>>>>> S06P31D206-164 [be] feat: :sparkles: 냉장고 등록 기능 추가
=======
>>>>>>> f8ba250e0e51ac6821d19fc723e14b5216871bfd
}
