package com.juyuso.api.dto.response.friend;

import com.juyuso.api.dto.UserInfoDto;
import com.juyuso.common.model.response.BaseResponseBody;
import com.juyuso.db.entity.user.User;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel("BanResponse")
public class FriendBanResDto extends BaseResponseBody {

    private List<UserInfoDto> bans;

    public static FriendBanResDto of(Integer statusCode, String message , List<User> bans) {
        FriendBanResDto res = new FriendBanResDto();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setBans(bans);
        return res;
    }

    public void setBans(List<User> user) {
        bans = new ArrayList<>();
        for (int i =0 ; i < user.size() ; i ++){
            bans.add(UserInfoDto.of(user.get(i)));
        }
    }
}
