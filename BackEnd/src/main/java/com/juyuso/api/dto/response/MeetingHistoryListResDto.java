package com.juyuso.api.dto.response;

import com.juyuso.db.entity.MeetingHistory;
import lombok.Getter;
import lombok.Setter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MeetingHistoryListResDto {
    private String nickName;
    private String insertedTime;
    private String action;
    private Long meetingId;
    private String meetingTitle;

    public MeetingHistoryListResDto() {

    }

    public MeetingHistoryListResDto(String nickName, String insertedTime, String action) {
        this.nickName = nickName;
        this.insertedTime = insertedTime;
        this.action = action;
    }

    public static List<MeetingHistoryListResDto> of (List<MeetingHistory> histories) {
        List<MeetingHistoryListResDto> list = new ArrayList<>();

        for(MeetingHistory meetingHistory: histories) {
            MeetingHistoryListResDto res = new MeetingHistoryListResDto();
            res.setNickName(meetingHistory.getUser().getNickname());
            res.setAction(meetingHistory.getAction());
            res.setInsertedTime(meetingHistory.getInsertedTime().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            res.setMeetingId(meetingHistory.getMeeting().getId());
            res.setMeetingTitle(meetingHistory.getMeeting().getTitle());
            list.add(res);
        }
        return list;
    }


}
