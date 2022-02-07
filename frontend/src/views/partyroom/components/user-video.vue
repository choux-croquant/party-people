<template>
	<div v-if="streamManager" class="userVideoContainer">
		<ov-video class="rounded-xl" :stream-manager="streamManager" />
		<div class="userOverlay text-tc-500">
			<p>{{ clientData }}</p>
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

export default {
	name: 'UserVideo',

	components: {
		OvVideo,
	},

	props: {
		streamManager: Object,
	},

	computed: {
		clientData() {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
	},

	methods: {
		getConnectionData() {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
	},
};
</script>
