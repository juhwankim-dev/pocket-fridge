package com.ssafy.andback.core.repository;

import com.ssafy.andback.core.domain.Refrigerator;
import com.ssafy.andback.core.domain.User;
import com.ssafy.andback.core.domain.UserRefrigerator;
import com.ssafy.andback.core.queryrepository.UserRefrigeratorQueryRepository;
import com.ssafy.andback.core.queryrepository.UserRefrigeratorQueryRepositoryImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * UserRefrigeratorRepository
 * 유저 냉장고 삭제 함수 구현
 *
 * @author 김다은
 * @version 1.0.0
 * 생성일 2022-05-03
 * 마지막 수정일 2022-05-03
 **/
public interface UserRefrigeratorRepository extends JpaRepository<UserRefrigerator, Long>, UserRefrigeratorQueryRepository {

    List<UserRefrigerator> findUserRefrigeratorByUser(User user);

    void deleteUserRefrigeratorByRefrigerator(Refrigerator refrigeratorId);

    Optional<UserRefrigerator> findByRefrigeratorAndUserAndRefrigeratorOwner(Refrigerator refrigerator, User user, Boolean owner);

    Optional<UserRefrigerator> findByRefrigeratorAndUser(Refrigerator refrigerator, User user);

    List<UserRefrigerator> findAllByRefrigerator(Refrigerator refrigerator);

    Boolean existsByRefrigeratorAndUser(Refrigerator refrigerator, User user);

    Boolean existsByRefrigeratorAndUserAndRefrigeratorOwner(Refrigerator refrigerator, User user, Boolean owner);
}
