package com.ssafy.api.controller;

import com.ssafy.api.request.UserSignUpPostReq;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.ListService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 파티룸 리스트, 태그 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "파티룸 리스트, 태그 API", tags = {"List"})
@RestController
@RequestMapping("/api/v1/list")
public class ListController {
	
	@Autowired
	ListService ListService;

}
