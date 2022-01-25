package com.juyuso.api.dto.response;

import com.juyuso.db.entity.MeetingHistory;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ApiModel("MeetingHistory List Response")
public class MeetingHistoryListResDto {
    @ApiModelProperty(name = "유저 닉네임", example = "냠냠")
    private String nickName;
    @ApiModelProperty(name = "입장, 생성, 퇴장 시간", example = "20220101")
    private String insertedTime;
    @ApiModelProperty(name = "기록타입", example = "입장, 퇴장, 생성")
    private String action;
    @ApiModelProperty(name = "미팅 방 번호", example = "2")
    private Long meetingId;
    @ApiModelProperty(name = "미팅 방 이름", example = "술한잔해요")
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
