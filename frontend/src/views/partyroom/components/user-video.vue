<template>
	<div v-if="streamManager" class="userVideoContainer">
		<ov-video class="rounded-xl" :stream-manager="streamManager" />
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
		};
	},

	props: {
		streamManager: Object,
	},

	computed: {
		clientData() {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
		userNickname() {
			const userList = this.store.getters['root/getRoomUserList'].map(user => {
				return user.value.split('/');
			});
			const nickname = userList.map(user => {
				if (user[0] === this.clientData) {
					return user[1];
				}
			});
			return nickname[0];
		},
	},

	methods: {
		getConnectionData() {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
	},

	mounted() {
		this.userNickname = this.store.getters['root/getRoomUserList'];
	},
};
</script>
