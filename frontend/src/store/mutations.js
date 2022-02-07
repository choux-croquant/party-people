export function setPlatform (state, isDesktop) {
  state.isDesktopPlatform = isDesktop
}

export function setRoomList (state, roomList) {
	console.log(roomList)
	state.roomList = roomList
}

export function setTimer (state, timerTime) {
	console.log(timerTime)
	state.timerTime = timerTime
}

export function setVote (state, voteInfo) {
	console.log(voteInfo)
	state.voteInfo = voteInfo
}

export function setVoteResult (state, voteResult) {
	console.log(voteResult)
	state.voteResult = voteResult
}

export function setRoomUserList (state, roomUserList) {
	console.log(roomUserList)
	state.roomUserList = roomUserList
}
