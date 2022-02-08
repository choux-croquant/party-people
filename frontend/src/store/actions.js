// API
import $axios from 'axios';

const backAxios = $axios.create({
	baseURL: '/api/v1',
});
const ovAxios = $axios.create({
	baseURL: '/openvidu/api',
	headers: {
		Authorization: 'Basic a106ssafy0183',
	},
});

export function requestRoomList({ state }, payload) {
	console.log('requestRoomList', state, payload);
	const url = `/list/room`;
	return backAxios.get(url);
}

export function requestRoomUserList({ state }, payload) {
	console.log('requestRoomList', state, payload);
	const url = `/rooms/users/${payload.roomId}`;
	let token = localStorage.getItem('access_token');
	console.log(token);

	return backAxios({
		method: 'GET',
		url: url,
		headers: { Authorization: 'Bearer ' + token },
	});
}

export function roomSearch({ state }, payload) {
	console.log('roomSearch', state, payload);
	const url = `/list/roomSearch?include=${payload.include}&word=${payload.word}`;

	return backAxios.get(url);
}

export function createRoom({ state }, payload) {
	console.log('createRoom', state, payload);
	const url = '/rooms';
	let token = localStorage.getItem('access_token');
	console.log(token);
	return backAxios({
		method: 'POST',
		url: url,
		headers: {
			Authorization: 'Bearer ' + token,
			'Content-Type': 'multipart/form-data',
		},
		data: payload,
	});
}

export function roomLinkEntry({ state }, roomId) {
	console.log('roomLinkEntry', state);
	const url = `/rooms/${roomId}`;
	let token = localStorage.getItem('access_token');
	return backAxios({
		method: 'GET',
		url: url,
		headers: {
			Authorization: 'Bearer ' + token,
		},
	});
}

export function passwordConfirm({ state }, payload) {
	const url = `/rooms/${payload.roomId}`;
	console.log(payload.password);
	let token = localStorage.getItem('access_token');
	return backAxios({
		method: 'POST',
		url: url,
		headers: {
			Authorization: 'Bearer ' + token,
		},
		data: { password: payload.password },
	});
}

export function leaveSession({ state }, roomId) {
	console.log('leaveSession', state, roomId);
	const url = `/rooms/exit/${roomId}`;
	let token = localStorage.getItem('access_token');
	return backAxios({
		method: 'PATCH',
		url: url,
		headers: {
			Authorization: 'Bearer ' + token,
		},
	});
}