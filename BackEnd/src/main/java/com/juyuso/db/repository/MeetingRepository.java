package com.juyuso.db.repository;

import com.juyuso.db.entity.Meeting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    @Query(value = "SELECT * FROM meeting m inner join hash_tag h " +
            "on m.meeting_id = h.meeting_id WHERE h.tag LIKE %:tag%", nativeQuery = true)
    Page<Meeting> findListByHashTag(@Param("tag")String tags, Pageable pageable);

    @Query("SELECT m FROM Meeting m WHERE m.active = true and m.title LIKE CONCAT('%',:title, '%')")
    Page<Meeting> findAllByTitleContaining(@Param("title") String title, Pageable pageable);

    @Query(value = "SELECT * FROM meeting m LEFT JOIN user_img ui ON m.owner_id = ui.user_id WHERE m.active = TRUE ", nativeQuery = true)
    Page<Meeting> findAll(Pageable pageable);


    @Query(value = "SELECT * FROM meeting m inner join hash_tag h " +
            "on m.meeting_id = h.meeting_id WHERE h.tag LIKE %:tag% AND m.common = :common ", nativeQuery = true)
    Page<Meeting> findAllByTagAndCommon(@Param("tag")String tags, Pageable pageable, @Param("common") Boolean common);

    @Query("SELECT m FROM Meeting m WHERE m.active = true and m.title LIKE CONCAT('%',:title, '%') AND m.common = :common ")
    Page<Meeting> findAllByTitleAndCommon(@Param("title") String title, Pageable pageable, @Param("common")Boolean common);


    @Query(value = "SELECT * FROM meeting m LEFT JOIN user_img ui ON m.owner_id = ui.user_id WHERE m.active = TRUE AND m.common = :common ", nativeQuery = true)
    Page<Meeting> findAllByCommon(Pageable pageable, @Param("common") Boolean common);




}
