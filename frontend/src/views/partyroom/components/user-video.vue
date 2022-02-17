<template>
	<div v-if="streamManager" class="userVideoContainer">
		<ov-video
			class="rounded-xl"
			:stream-manager="streamManager"
			:class="{ userSpeaking: isSpeaking }"
		/>
		<div class="userOverlay rounded-b-xl text-tc-500">
			<p>{{ clientData }} / {{ userNickname }}</p>
		</div>
	</div>
</template>
<style>
.userVideoContainer {
	position: relative;
}
.userOverlay {
	display: none;
}
.userVideoContainer:hover .userOverlay {
	display: block;
	width: 100%;
	background-color: rgba(0, 0, 0, 0.5);
	position: absolute;
	bottom: 0px;
}
.userSpeaking {
	outline: 4px solid #6667ab;
}
</style>
<script>
import OvVideo from './ov-video';
import { useStore } from 'vuex';
export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	data() {
		return {
			store: useStore(),
			isSpeaking: false,
		};
	},

	props: {
		streamManager: Object,
	},
	// 유저 아이디 정보와 유저 닉네임 정보를 computed 속성으로 불러온 후 사용
	computed: {
		clientData() {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
		userNickname() {
			const userNickname = this.store.getters['auth/getUserNickname'];
			return userNickname;
		},
	},
	// 함수를 통해 css속성을 추가/제거 하는 것으로 speech detection 시 하이라이트 효과 구현
	methods: {
		getConnectionData() {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
		highlightOn(name) {
			const { clientData } = this.getConnectionData();

			if (clientData === name) {
				this.isSpeaking = true;
				return true;
			}

			return false;
		},
		highlightOff(name) {
			const { clientData } = this.getConnectionData();

			if (clientData === name) {
				this.isSpeaking = false;
				return true;
			}

			return false;
		},
	},
};
</script>
