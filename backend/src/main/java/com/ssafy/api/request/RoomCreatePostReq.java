package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 파티룸 생성 API ([POST] /api/v1/rooms) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("RoomCreatePostRequest")
public class RoomCreatePostReq {
	@ApiModelProperty(name="방 제목", example="방 제목")
	String title;
	@ApiModelProperty(name="방 설명", example="방 설명")
	String description;
	@ApiModelProperty(name="썸네일 경로", example="썸네일 경로")
	String thumbnail_url;
	@ApiModelProperty(name="최대 인원수", example="100")
	String capacity;
	@ApiModelProperty(name="비밀번호", example="비밀번호")
	String password;
	@ApiModelProperty(name="시작시간", example="2020-01-01")
	String start_time;
	@ApiModelProperty(name="활성 여부", example="is_active")
	Boolean is_active;
	@ApiModelProperty(name="잠금 여부", example="is_locked")
	Boolean is_locked;
}
