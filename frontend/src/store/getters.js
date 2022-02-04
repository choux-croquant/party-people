// 플랫폼 관련 정보 가져오기
export function getIsDesktopPlatform (state) {
  return state.isDesktopPlatform
}
// 메뉴 객체 가져오기
export function getMenus (state) {
	return state.menus
}
// Active된 메뉴 인덱스 가져오기
export function getActiveMenuIndex (state) {
	const keys = Object.keys(state.menus)
	return keys.findIndex(item => item === state.activeMenu)
}

export function getRoomList (state) {
	return state.roomList
}


export function getTimerTime (state) {
	return state.timerTime
}

export function getRoomUserList (state) {
	return state.roomUserList
}

export function getRouletteSignalData (state) {
	return state.rouletteSignalData
}

export function getRouletteStartSignal (state) {
	return state.rouletteStartSignal
}

export function getRoomId (state) {
	return state.roomId
}
