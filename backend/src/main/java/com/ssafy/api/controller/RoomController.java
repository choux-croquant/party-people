package com.ssafy.api.controller;

import com.ssafy.api.request.RoomEntryPostReq;
import com.ssafy.api.request.RoomHostUpdateReq;
import com.ssafy.api.response.RoomEntryLinkRes;
import com.ssafy.api.response.RoomUserListRes;
import com.ssafy.api.request.RoomCreatePostReq;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.service.RoomService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.User;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 파티룸 내부 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "파티룸 내부 API", tags = {"Room"})
@RestController
@RequestMapping("/api/v1/rooms")
public class RoomController {

    @Autowired
    RoomService roomService;

	@GetMapping("/users/{room_id}")
	@ApiOperation(value = "파티룸 접속 사용자 리스트 조회", notes = "사용자 닉네임을 포함하는 파티룸 내 사용자 객체 리스트를 반환한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = RoomUserListRes.class),
			@ApiResponse(code = 401, message = "인증 토큰 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 403, message = "조회 권한 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 404, message = "세션 데이터 없음", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	public ResponseEntity<? extends BaseResponseBody> getRoomUserList(
			@ApiIgnore Authentication authentication,
			@PathVariable(name = "room_id")  @ApiParam(value="접속한 방 id", required = true) Long roomId) {

		// 토큰이 없는 사용자가 요청한 경우 : 401(인증 토큰 없음)
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseBody.of(401, "인증 토큰 없음"));

		SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
		Long userId = userDetails.getUser().getId();

		// 해당 방에 접속하지 않은 사용자가 요청한 경우 : 403(조회 권한 없음)
		if(roomService.isUserNotInCurrentSession(roomId, userId)) return ResponseEntity.status(403).body(BaseResponseBody.of(403, "조회 권한 없음"));

		// 존재하지 않는 세션을 요청한 경우 : 404(세션 데이터 없음)
		if (roomService.isNotSessionExist(roomId)) return ResponseEntity.status(404).body(BaseResponseBody.of(404, "세션 데이터 없음"));

		List<User> userList = roomService.getRoomUserListByRoomId(roomId);
		return ResponseEntity.status(200).body(RoomUserListRes.of(200, "Success", roomId, userList));
	}

	@PatchMapping("/host/{room_id}")
	@ApiOperation(value = "파티룸 호스트 변경", notes = "파티룸의 호스트를 변경한다.")
	@ApiResponses({
			@ApiResponse(code = 201, message = "성공", response = UserLoginPostRes.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	public ResponseEntity<? extends BaseResponseBody> updateRoomHosts(
			@ApiIgnore Authentication authentication,
			@PathVariable(name = "room_id")  @ApiParam(value="접속한 방 id", required = true) Long roomId,
			@RequestBody @ApiParam(value="파티룸 내 사용자 호스트 등록 여부 정보", required = true) List<RoomHostUpdateReq> req) {

		// 토큰이 없는 사용자가 파티룸 호스트 변경을 요청한 경우 : 401(Unauthorized Error반환)
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Unauthorized"));

		roomService.updateRoomHostInfo(roomId, req);
		return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
	}

	@PatchMapping("/exit/{room_id}")
	@ApiOperation(value = "파티룸 퇴장", notes = "파티룸 소켓 통신을 끊고 접속한 파티룸에서 퇴장한다.")
	@ApiResponses({
			@ApiResponse(code = 201, message = "성공", response = UserLoginPostRes.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	public ResponseEntity<? extends BaseResponseBody> exitRoom(
			@ApiIgnore Authentication authentication,
			@PathVariable(name = "room_id")  @ApiParam(value="접속한 방 id", required = true) Long roomId) {

		// 토큰이 없는 사용자가 파티룸 퇴장을 요청한 경우 : 401(Unauthorized Error반환)
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Unauthorized"));

		SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
		Long userId = userDetails.getUser().getId();

		roomService.updateSessionEndTime(roomId, userId);
        if (!roomService.checkRoomUserExist(roomId)) roomService.deleteRoom(roomId);
		return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
	}

	@PostMapping("/")
	@ApiOperation(value = "파티룸 생성", notes = "<strong>파티룸</strong>을 생성한다.")
	@ApiResponses({
			@ApiResponse(code = 201, message = "성공", response = UserLoginPostRes.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	public ResponseEntity<? extends BaseResponseBody> createRoom(
			@RequestBody @ApiParam(value = "파티룸 정보", required = true) RoomCreatePostReq req,
			@ApiIgnore Authentication authentication) {

		// 토큰이 없는 사용자가 파티룸 생성을 요청한 경우 : 401(Unauthorized Error반환)
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Unauthorized"));

		SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
		Long userId = userDetails.getUser().getId();

		// TODO : 파티룸 생성 후 입장 방법 정하기, 프론트에서 POST 입장 한번 더 보내줄지, 여기서 처리할 지
		// TODO: 응답 값, 메소드 응답 값 수정
		if (roomService.createRoom(req, userId) == null)
			return ResponseEntity.status(403).body(null);

		return ResponseEntity.status(200).body(null);
	}

	@PatchMapping("/del/{room_id}")
	@ApiOperation(value = "파티룸 삭제", notes = "<strong>파티룸</strong>을 삭제한다.")
	@ApiResponses({
			@ApiResponse(code = 201, message = "성공", response = UserLoginPostRes.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	public ResponseEntity<? extends BaseResponseBody> deleteRoom(
			@ApiIgnore Authentication authentication,
			@PathVariable(name = "room_id") @ApiParam(value = "파티룸 번호", required = true) long roomId) {

		// TODO: 호스트인지 확인하는 코드 추가

		// 토큰이 없는 사용자가 파티룸 삭제를 요청한 경우 : 401(Unauthorized Error반환)
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Unauthorized"));

		roomService.deleteRoom(roomId);
		// TODO: 강제 삭제하면, 세션 안에 있는 사람도 다 endtime 찍어내기

		// TODO: 응답 값, 메소드 응답 값 수정
		return ResponseEntity.status(200).body(null);
	}

	@GetMapping("/{room_id}")
	@ApiOperation(value = "파티룸 링크 입장", notes = "<strong>파티룸</strong> 링크 입장시 비밀번호 여부값을 응답한다.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
			@ApiResponse(code = 403, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	public ResponseEntity<? extends BaseResponseBody> roomLinkEntry(
			@ApiIgnore Authentication authentication,
			@PathVariable(name = "room_id") @ApiParam(value = "파티룸 번호", required = true)  Long roomId) {

		// 토큰이 없는 사용자가 파티룸 입장(링크 접속)을 요청한 경우 : 401(Unauthorized Error반환)
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Unauthorized"));

		Room room = roomService.findByRoomId(roomId);
		return ResponseEntity.status(200).body(RoomEntryLinkRes.of(200, "Success", room));
	}

    @PostMapping("/{room_id}")
    @ApiOperation(value = "파티룸 입장", notes = "<strong>파티룸</strong>입장시 비밀번호를 확인하고 입장한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
            @ApiResponse(code = 403, message = "인증 실패", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<? extends BaseResponseBody> roomEntry(
            @ApiIgnore Authentication authentication,
            @PathVariable(name = "room_id") @ApiParam(value = "파티룸 번호", required = true)  Long roomId,
            @RequestBody @ApiParam(value = "파티룸 비밀번호", required = true) RoomEntryPostReq req) {

		// TODO: 호스트인지 확인하는 코드 추가

		// 토큰이 없는 사용자가 파티룸 입장(비밀번호 입력 접속) 경우 : 401(Unauthorized Error반환)
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Unauthorized"));

		SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
		User user = userDetails.getUser();

        if(roomService.roomEntry(user, roomId, req.getPassword())) {
			return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
		}
        return ResponseEntity.status(403).body(BaseResponseBody.of(403, "Failed"));
    }
}
