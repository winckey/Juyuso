package com.juyuso.db.repository;

import com.juyuso.db.entity.UserImg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserImgRepository {

    private final EntityManager em;

    public void save(UserImg userImg) {
        em.persist(userImg);
    }

    public int deleteByUserId(Long userId) {
        return em.createQuery("delete from UserImg img where img.user.id = :id")
                .setParameter("id", userId)
                .executeUpdate();
    }
}
