<template>
	<div class="bottom flex justify-center mb-5">
		<div>
			<svg
				@click="videoOnOff"
				xmlns="http://www.w3.org/2000/svg"
				class="cursor-pointer py-2 px-2 mx-5 h-10 w-10 text-red-600 bg-main-200 rounded-full"
				fill="none"
				viewBox="0 0 24 24"
				stroke="currentColor"
			>
				<path
					stroke-linecap="round"
					stroke-linejoin="round"
					stroke-width="1.5"
					d="M3 9a2 2 0 012-2h.93a2 2 0 001.664-.89l.812-1.22A2 2 0 0110.07 4h3.86a2 2 0 011.664.89l.812 1.22A2 2 0 0018.07 7H19a2 2 0 012 2v9a2 2 0 01-2 2H5a2 2 0 01-2-2V9z"
				/>
				<path
					stroke-linecap="round"
					stroke-linejoin="round"
					stroke-width="1.5"
					d="M15 13a3 3 0 11-6 0 3 3 0 016 0z"
				/>
			</svg>
		</div>
		<div>
			<svg
				@click="audioOnOff"
				xmlns="http://www.w3.org/2000/svg"
				class="cursor-pointer py-2 px-2 mx-5 h-10 w-10 text-red-600 bg-main-200 rounded-full"
				fill="none"
				viewBox="0 0 24 24"
				stroke="currentColor"
			>
				<path
					stroke-linecap="round"
					stroke-linejoin="round"
					stroke-width="1.5"
					d="M19 11a7 7 0 01-7 7m0 0a7 7 0 01-7-7m7 7v4m0 0H8m4 0h4m-4-8a3 3 0 01-3-3V5a3 3 0 116 0v6a3 3 0 01-3 3z"
				/>
			</svg>
		</div>
		<div>
			<svg
				v-show="state.filter"
				@click="filterOff"
				xmlns="http://www.w3.org/2000/svg"
				class="cursor-pointer py-2 px-2 mx-5 h-10 w-10 text-white-600 bg-main-200 rounded-full"
				fill="none"
				viewBox="0 0 24 24"
				stroke="currentColor"
			>
				<path
					stroke-linecap="round"
					stroke-linejoin="round"
					stroke-width="1.2"
					d="M17.75,3 C19.5449254,3 21,4.45507456 21,6.25 L21,13.1286797 C21,13.7254168 20.7629471,14.2977131 20.3409903,14.7196699 L14.7196699,20.3409903 C14.2977131,20.7629471 13.7254168,21 13.1286797,21 L6.25,21 C4.45507456,21 3,19.5449254 3,17.75 L3,6.25 C3,4.45507456 4.45507456,3 6.25,3 L17.75,3 Z M17.75,4.5 L6.25,4.5 C5.28350169,4.5 4.5,5.28350169 4.5,6.25 L4.5,17.75 C4.5,18.7164983 5.28350169,19.5 6.25,19.5 L13,19.5 L13.0009311,16.43647 C12.7797674,16.4695411 12.5550391,16.4914039 12.3268476,16.5020318 L12.0009196,16.5095694 C10.6524195,16.5095694 9.41985327,16.1277457 8.32332796,15.3693213 C7.98266181,15.1336957 7.89750966,14.6665189 8.13313532,14.3258527 C8.36876097,13.9851866 8.83593779,13.9000344 9.17660394,14.1356601 C10.0214496,14.7200073 10.9561858,15.0095694 12.0009196,15.0095694 C12.4632218,15.0095694 12.9039851,14.9528695 13.3249517,14.8390171 C13.8209477,13.810242 14.841241,13.0847218 16.0381726,13.0069334 L16.2511587,13 L19.5,13 L19.5,6.25 C19.5,5.28350169 18.7164983,4.5 17.75,4.5 Z M18.439,14.5 L16.2514009,14.5 C15.3331635,14.5002964 14.5800184,15.2074184 14.5065843,16.1066314 L14.5007131,16.2501337 L14.5,18.439 L18.439,14.5 Z M9.00044779,7.75115873 C9.69041108,7.75115873 10.2497368,8.3104845 10.2497368,9.00044779 C10.2497368,9.69041108 9.69041108,10.2497368 9.00044779,10.2497368 C8.3104845,10.2497368 7.75115873,9.69041108 7.75115873,9.00044779 C7.75115873,8.3104845 8.3104845,7.75115873 9.00044779,7.75115873 Z M15.0004478,7.75115873 C15.6904111,7.75115873 16.2497368,8.3104845 16.2497368,9.00044779 C16.2497368,9.69041108 15.6904111,10.2497368 15.0004478,10.2497368 C14.3104845,10.2497368 13.7511587,9.69041108 13.7511587,9.00044779 C13.7511587,8.3104845 14.3104845,7.75115873 15.0004478,7.75115873 Z"
				/>
			</svg>
		</div>
		<button
			@click="leaveSession"
			class="bg-alert-100 w-32 h-10 text-white rounded-full text-center mx-5"
		>
			<div class="mt-1.5 text-xl font-bold">OUT</div>
		</button>
	</div>
</template>

<style>
.bottom {
	position: fixed;
	bottom: 0;
	width: 100%;
	/* Height of the footer*/
}
</style>

<script>
import { reactive } from '@vue/reactivity';
export default {
	setup(props, { emit }) {
		const state = reactive({
			video: true,
			audio: true,
			filter: false,
		});

		const videoOnOff = () => {
			if (state.video) {
				state.video = false;
			} else {
				state.video = true;
			}

			emit('videoOnOff', {
				video: state.video,
			});
		};

		const audioOnOff = () => {
			if (state.audio) {
				state.audio = false;
			} else {
				state.audio = true;
			}
			console.log(state.audio);
			emit('audioOnOff', {
				audio: state.audio,
			});
		};

		const filterOff = () => {
			if (state.filter) {
				state.filter = false;
			} else {
				state.filter = true;
			}
			emit('filterOff');
		};

		const leaveSession = () => {
			emit('leaveSession');
		};
		return { state, videoOnOff, audioOnOff, filterOff, leaveSession };
	},
};
</script>
