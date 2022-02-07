<template>
	<nav class="bg-white border-gray-200 px-4 py-5">
		<div class="flex flex-wrap justify-between items-center w-full my-0">
			<a href="#" class="flex-none ml-2 mr-6">
				<img class="w-10 h-10" src="@/assets/logo.png" />
			</a>
			<div class="flex-auto">
				<div class="relative mr-3">
					<div
						class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none"
					>
						<svg
							class="w-5 h-5 text-tc-400 fill-current"
							viewBox="0 0 20 20"
							xmlns="http://www.w3.org/2000/svg"
						>
							<path
								fill-rule="evenodd"
								d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z"
								clip-rule="evenodd"
							></path>
						</svg>
					</div>
					<input
						v-model="state.searchValue"
						@keyup.enter="roomSearch()"
						type="text"
						id="party-room-search"
						class="block border-0 appearance-none rounded-full shadow-md h-10 p-2 pl-10 w-full text-tc-200 bg-main-300 sm:text-sm focus:outline-none focus:border-main-100 focus:ring-2 focus:ring-main-100"
						placeholder="Search for party room"
					/>
				</div>
			</div>

			<div class="dropdown inline-block flex-none relative">
				<button class="flex-none rounded-full shadow-lg w-10 h-10 bg-main-200">
					<svg
						class="fill-current text-tc-500 ml-3 h-4 w-4"
						xmlns="http://www.w3.org/2000/svg"
						viewBox="0 0 20 20"
					>
						<path
							d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"
						/>
					</svg>
				</button>
				<ul class="dropdown-menu absolute hidden right-0 w-40 text-gray-700">
					<button
						@click="changeOption('title')"
						class="rounded-full w-32 h-10 mt-2 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100"
						type="button"
					>
						제목
					</button>
					<button
						@click="changeOption('des')"
						class="rounded-full w-32 h-10 mt-2 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100"
						type="button"
					>
						내용
					</button>
				</ul>
			</div>

			<div class="flex-none hidden md:block w-1/6"></div>
			<div v-if="!state.loginState" class="flex-none hidden md:block">
				<button
					@click="clickLogin()"
					class="rounded-full w-32 h-10 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100"
					type="button"
				>
					Login
				</button>
				<button
					@click="clickSignup()"
					class="rounded-full w-32 h-10 ml-4 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100"
					type="button"
				>
					Sign-Up
				</button>
			</div>
			<div v-else class="flex-none hidden md:block">
				<button
					@click="createRoom()"
					class="rounded-full w-32 h-10 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100"
					type="button"
				>
					Add+
				</button>
				<button
					@click="clickLogout()"
					class="rounded-full w-32 h-10 ml-4 font-bold shadow-lg bg-alert-200 text-tc-500 hover:bg-alert-100"
					type="button"
				>
					Log-Out
				</button>
			</div>
		</div>
	</nav>

	<!-- Login modal -->
	<login-modal ref="loginModal" />
	<!-- Signup modal -->
	<signup-modal ref="signupModal" />
	<!-- Room create modal -->
	<conference-create-modal ref="conferenceCreateModal" />
</template>
<script>
import { computed, reactive, ref } from 'vue';
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import SignupModal from '@/teleport/signup-modal.vue';
import LoginModal from '@/teleport/login-modal.vue';
import ConferenceCreateModal from '@/teleport/conference-create-modal.vue';

export default {
	name: 'main-header',

	components: {
		LoginModal,
		SignupModal,
		ConferenceCreateModal,
	},
	setup() {
		const store = useStore();
		const router = useRouter();
		const signupModal = ref(null);
		const loginModal = ref(null);
		const conferenceCreateModal = ref(null);
		const state = reactive({
			loginState: computed(() => store.getters['auth/getLoginState']),
			searchValue: null,
			searchOption: 'title',
		});

		const clickLogin = () => {
			console.log('clicklogin');
			console.log(loginModal.value);
			loginModal.value.open();
		};

		const clickLogout = () => {
			localStorage.removeItem('access_token');
			store.commit('auth/setLoginState', false);
			router.push({ name: 'Home' });
		};

		const clickSignup = () => {
			console.log('clicksignup');
			console.log(signupModal.value);
			signupModal.value.open();
		};

		const createRoom = () => {
			console.log('createRoom');
			console.log(conferenceCreateModal.value);
			conferenceCreateModal.value.open();
		};

		const changeOption = option => {
			console.log(option);
			state.searchOption = option;
			console.log(state.searchOption);
		};

		const roomSearch = () => {
			console.log(state.searchValue);
			store
				.dispatch('root/roomSearch', {
					include: state.searchOption,
					word: state.searchValue,
				})
				.then(res => {
					store.commit('root/setRoomList', res.data.contents.content);
				})
				.catch(err => {
					console.log(err);
				});
			state.searchValue = null;
		};

		return {
			state,
			loginModal,
			signupModal,
			conferenceCreateModal,
			clickLogin,
			clickSignup,
			createRoom,
			clickLogout,
			changeOption,
			roomSearch,
		};
	},
};
</script>
<style>
.dropdown:hover .dropdown-menu {
	display: block;
}
</style>
