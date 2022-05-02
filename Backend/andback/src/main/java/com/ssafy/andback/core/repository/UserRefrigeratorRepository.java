package com.ssafy.andback.core.repository;

import com.ssafy.andback.core.domain.User;
import com.ssafy.andback.core.domain.UserRefrigerator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRefrigeratorRepository extends JpaRepository<UserRefrigerator, Long> {

    List<UserRefrigerator> findUserRefrigeratorByUser(User user);
    void deleteUserRefrigeratorByUser(User user);
}
