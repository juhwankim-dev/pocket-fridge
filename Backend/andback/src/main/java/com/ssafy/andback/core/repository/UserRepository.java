package com.ssafy.andback.core.repository;

import com.ssafy.andback.core.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 *
 * UserRepository
 * DB 에서 유저 정보에 접근하기 위한 Repository
 *
 * @author 김다은
 * @version 1.0.0
 * 생성일 2022-04-19
 * 마지막 수정일 2022-04-19
 **/
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserEmail(String userEmail);   // 유저 이메일로 검색
}
