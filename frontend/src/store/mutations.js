export function setPlatform (state, isDesktop) {
  state.isDesktopPlatform = isDesktop
}

export function setMenuActive (state, index) {
	console.log('setMenuActive', state,index)
	const keys = Object.keys(state.menus)
	state.activeMenu = keys[index]
}

export function setMenuActiveMenuName (state, menuName) {
	state.activeMenu = menuName
}

export function setLoginState (state, setLogin) {
	console.log(setLogin)
	state.loginState = setLogin
	console.log(state.loginState)
}

export function setRoomList (state, roomList) {
	console.log(roomList)
	state.roomList = roomList
}

export function setTimer (state, timerTime) {
	console.log(timerTime)
	state.timerTime = timerTime
}