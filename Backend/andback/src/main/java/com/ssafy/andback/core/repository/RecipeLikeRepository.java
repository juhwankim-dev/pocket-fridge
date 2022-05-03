/**
 * RecipeLikeRepository
 * DB 에서 레시피 좋아요 정보에 접근하기 위한 Repository
 *
 * @author 문관필, 김다은
 * @version 1.0.0
 * 생성일 2022-05-02
 * 마지막 수정일 2022-05-03
 **/
package com.ssafy.andback.core.repository;

import com.ssafy.andback.core.domain.Recipe;
import com.ssafy.andback.core.domain.RecipeLike;
import com.ssafy.andback.core.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RecipeLikeRepository extends JpaRepository<RecipeLike, Long> {

    Optional<RecipeLike> findByUserAndRecipe(User user, Recipe recipe);
    void deleteRecipeLikeByUser(User user);
}
