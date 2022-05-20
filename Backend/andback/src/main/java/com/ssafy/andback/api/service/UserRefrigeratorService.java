package com.ssafy.andback.api.service;

import com.ssafy.andback.core.domain.User;
import com.ssafy.andback.core.domain.UserRefrigerator;

import java.util.List;

public interface UserRefrigeratorService {

    List<UserRefrigerator> findUserRefrigeratorsByUser(User user);
}
