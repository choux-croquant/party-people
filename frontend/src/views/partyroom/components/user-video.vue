<template>
<div v-if="streamManager" class="userVideoContainer">
	<ov-video :stream-manager="streamManager"/>
	<div><p>{{ clientData }}</p></div>
</div>
</template>
<style>
.userVideoContainer {
	width: 480px;
	height: 270px;
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
		clientData () {
			const { clientData } = this.getConnectionData();
			return clientData;
		},
	},

	methods: {
		getConnectionData () {
			const { connection } = this.streamManager.stream;
			return JSON.parse(connection.data);
		},
	},
};
</script>
