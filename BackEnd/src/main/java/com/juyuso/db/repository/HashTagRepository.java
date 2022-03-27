package com.juyuso.db.repository;

import com.juyuso.db.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    void deleteByMeetingId(Long meetingId);
}
