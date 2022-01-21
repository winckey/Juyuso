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

    Page<Meeting> findAllByTitleContaining(String title, Pageable pageable);

    Page<Meeting> findAll(Pageable pageable);

}
