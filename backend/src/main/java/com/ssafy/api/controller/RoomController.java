package com.ssafy.api.controller;

import com.ssafy.api.request.RoomEntryPostReq;
import com.ssafy.api.request.RoomHostUpdateReq;
import com.ssafy.api.response.RoomUserListRes;
import com.ssafy.api.request.RoomCreatePostReq;
import com.ssafy.api.response.UserLoginPostRes;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.RoomService;
import com.ssafy.api.service.UserService;
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
    UserService userService;

    @Autowired
    RoomService roomService;

    @GetMapping("/users/{room_id}")
    @ApiOperation(value = "파티룸 접속 사용자 리스트 조회", notes = "사용자 닉네임을 포함하는 파티룸 내 사용자 객체 리스트를 반환한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공", response = RoomUserListRes.class),
            @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<? extends BaseResponseBody> getRoomUserList(@ApiIgnore Authentication authentication,
                                                                      @PathVariable(name = "room_id")  @ApiParam(value="접속한 방 id", required = true) String requestRoomId) {

        // 토큰이 없는 사용자가 파티룸 사용자 리스트를 요청한 경우 : 401(Unauthorized Error반환)
        if (authentication == null) return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Unauthorized"));

        Long roomId = Long.parseLong(requestRoomId);
        List<User> userList = roomService.getRoomUserListByRoomId(roomId);
        return ResponseEntity.status(201).body(RoomUserListRes.of(201, "Success", roomId, userList));
    }

	@PostMapping("/host/{room_id}")
	@ApiOperation(value = "파티룸 호스트 변경", notes = "파티룸의 호스트를 변경한다.")
	@ApiResponses({
			@ApiResponse(code = 201, message = "성공", response = UserLoginPostRes.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	public ResponseEntity<? extends BaseResponseBody> updateRoomHosts(@ApiIgnore Authentication authentication,
																	  @PathVariable(name = "room_id")  @ApiParam(value="접속한 방 id", required = true) String requestRoomId,
																	  @RequestBody @ApiParam(value="파티룸 내 사용자 호스트 등록 여부 정보", required = true) List<RoomHostUpdateReq> hostReq) {

		// 토큰이 없는 사용자가 파티룸 호스트 변경을 요청한 경우 : 401(Unauthorized Error반환)
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Unauthorized"));

		Long roomId = Long.parseLong(requestRoomId);
		List<User> hostList = roomService.updateRoomHostInfo(hostReq);
		return ResponseEntity.status(201).body(RoomUserListRes.of(201, "Success", roomId, hostList));
	}

	@PatchMapping("/exit/{room_id}")
	@ApiOperation(value = "파티룸 퇴장", notes = "파티룸 소켓 통신을 끊고 접속한 파티룸에서 퇴장한다.")
	@ApiResponses({
			@ApiResponse(code = 201, message = "성공", response = UserLoginPostRes.class),
			@ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
			@ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
	})
	public ResponseEntity<? extends BaseResponseBody> exitRoom(@ApiIgnore Authentication authentication,
																	  @PathVariable(name = "room_id")  @ApiParam(value="접속한 방 id", required = true) String requestRoomId) {

		// 토큰이 없는 사용자가 파티룸 퇴장을 요청한 경우 : 401(Unauthorized Error반환)
		if (authentication == null) return ResponseEntity.status(401).body(BaseResponseBody.of(401, "Unauthorized"));

		SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
		String userId = userDetails.getUsername();
		Long roomId = Long.parseLong(requestRoomId);

		roomService.updateSessionEndTime(userId, roomId);
		return ResponseEntity.status(201).body(BaseResponseBody.of(201, "Success"));
	}


    @PostMapping("/")
    @ApiOperation(value = "파티룸 생성", notes = "<strong>파티룸</strong>을 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
            @ApiResponse(code = 401, message = "인증 실패", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<UserLoginPostRes> createPartyRoom(@RequestBody @ApiParam(value = "파티룸 정보", required = true) RoomCreatePostReq roomCreatePostReq) {
        roomService.createRoom(roomCreatePostReq);

        return ResponseEntity.status(200).body(null);
    }

    @PostMapping("/{room_id}")
    @ApiOperation(value = "파티룸 입장 비밀번호 확인", notes = "<strong>파티룸</strong>입장시 비밀번호를 확인한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공", response = UserLoginPostRes.class),
            @ApiResponse(code = 403, message = "인증 실패", response = BaseResponseBody.class),
            @ApiResponse(code = 500, message = "서버 오류", response = BaseResponseBody.class)
    })
    public ResponseEntity<? extends BaseResponseBody> roomEntryPassword(
            // @ApiIgnore Authentication authentication,
            @PathVariable(name = "room_id") @ApiParam(value = "파티룸 번호", required = true)  Long roomId,
            @RequestBody @ApiParam(value = "파티룸 비밀번호", required = true) RoomEntryPostReq req) {


        if(roomService.roomEntryPassword(roomId, req.getPassword())) return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
        return ResponseEntity.status(200).body(BaseResponseBody.of(403, "Failed"));
    }
}
