package com.ssafy.andback.core.repository;


import com.ssafy.andback.core.domain.Token;
import com.ssafy.andback.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * TokenRepository
 * 토큰 레포지토리
 *
 * @author hoony
 * @version 1.0.0
 * 생성일 2022-04-29
 * 마지막 수정일 2022-04-29
 **/

public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<List<Token>> findAllByUser(User user);



}
