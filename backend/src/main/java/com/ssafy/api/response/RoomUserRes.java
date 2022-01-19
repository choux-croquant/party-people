package com.ssafy.api.response;

import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 파티룸 내 사용자 정보(사용자 아이디, 사용자 닉네임)를 표현하는 엔티티
 */
@Getter
@Setter
@ApiModel("UserResponse")
public class RoomUserRes {
    String userid;
    String nickname;

    public static RoomUserRes of(User user) {
        RoomUserRes res = new RoomUserRes();
        res.setUserid(user.getUserid());
        res.setNickname(user.getNickname());
        return res;
    }
}
