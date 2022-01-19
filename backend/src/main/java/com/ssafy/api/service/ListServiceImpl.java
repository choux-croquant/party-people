package com.ssafy.api.service;

import com.ssafy.db.repository.ListRepository;
import com.ssafy.db.repository.ListRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *	미팅룸 리스트, 태그 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("listService")
public class ListServiceImpl implements ListService {
	@Autowired
	ListRepository listRepository;
	
	@Autowired
	ListRepositorySupport listRepositorySupport;
	
	@Autowired
	PasswordEncoder passwordEncoder;
}
