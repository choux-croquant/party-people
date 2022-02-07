// 플랫폼 관련 정보 가져오기
export function getIsDesktopPlatform(state) {
	return state.isDesktopPlatform;
}

// 메뉴 객체 가져오기
export function getMenus(state) {
	return state.menus;
}

export function getRoomList(state) {
	return state.roomList;
}

export function getTimerTime(state) {
	return state.timerTime;
}

export function getRoomUserList(state) {
	return state.roomUserList;
}

export function getRouletteSignalData(state) {
	return state.rouletteSignalData;
}

export function getRoomId(state) {
	return state.roomId;
}
