/**
* RecipeLikeService
* 레시피 좋아요 서비스
*
* @author 문관필
* @version 1.0.0
* 생성일 2022/05/02
* 마지막 수정일 2022/05/02
**/
package com.ssafy.andback.api.service;

import com.ssafy.andback.core.domain.User;

public interface RecipeLikeService {

    public boolean addLike(User user, Long recipeId);
    public boolean removeLike(User user, Long recipeId);
}
