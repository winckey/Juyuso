package com.juyuso.db.repository.meeting;

import com.juyuso.db.entity.meeting.Meeting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {


    @Query("SELECT m FROM Meeting m join m.hashtags h WHERE h.tag LIKE %:tag% AND m.active = TRUE")
    Page<Meeting> findAllByTags(@Param("tag")String tags, Pageable pageable);

    @Query("SELECT m FROM Meeting m WHERE m.active = TRUE AND m.title LIKE %:title%")
    Page<Meeting> findAllByTitleContaining(@Param("title") String title, Pageable pageable);

    @Query(value = "SELECT m FROM Meeting m LEFT OUTER JOIN m.owner i WHERE m.active = TRUE ")
    Page<Meeting> findAll(Pageable pageable);

    @Query("SELECT m FROM Meeting m join m.hashtags h WHERE h.tag LIKE %:tag% AND m.common = :common AND m.active = TRUE")
    Page<Meeting> findAllByTagContainingAndCommon(@Param("tag")String tags, Pageable pageable, @Param("common") Boolean common);

    @Query("SELECT m FROM Meeting m WHERE m.active = true AND m.title LIKE %:title% AND m.common = :common")
    Page<Meeting> findAllByTitleContainingAndCommon(@Param("title") String title, Pageable pageable, @Param("common")Boolean common);

    @Query("SELECT m FROM Meeting m WHERE m.active = TRUE AND m.common = :common ")
    Page<Meeting> findAllByCommon(Pageable pageable, @Param("common") Boolean common);

    @Query(value = "UPDATE Meeting m SET m.active = false",nativeQuery = true)
    void changeActiveMeeting();




}
