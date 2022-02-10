<template>
	<main-header />

	<div class="conference-list-wrap pl-0" style="overflow: auto">
		<infinite-scroll
			@infinite-scroll="infiniteHandler"
			:noResult="state.noResult"
		>
			<conference
				v-for="room in state.roomList"
				:key="room.id"
				class="conference-card m-5"
				@click="handleClick(room.id)"
				:room="room"
			/>
		</infinite-scroll>
	</div>

	<footer class="display: none">not showing</footer>
	<password-confirm ref="passwordConfirmModal" />
</template>
<style>
.conference-list-wrap {
	max-height: calc(100% - 35px);
}

@media (min-width: 701px) and (max-width: 1269px) {
	.conference-list-wrap {
		min-width: 700px;
	}
}

@media (min-width: 1270px) {
	.conference-list-wrap {
		min-width: 1021px;
	}
}

.conference-list-wrap .conference-card {
	min-width: 335px;
	max-width: 25%;
	display: inline-block;
	cursor: pointer;
}
</style>
<script>
import { reactive, ref, computed, onBeforeMount } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';
import MainHeader from './components/main-header.vue';
import Conference from './components/conference.vue';
import InfiniteScroll from 'infinite-loading-vue3';
import PasswordConfirm from '@/teleport/password-confirm.vue';

export default {
	name: 'Home',

	components: {
		MainHeader,
		Conference,
		InfiniteScroll,
		PasswordConfirm,
	},

  setup() {
    const router = useRouter();
    const store = useStore();
    const passwordConfirmModal = ref(null);
    const state = reactive({
      roomList: computed(() => store.getters["root/getRoomList"]),
			noResult: false,
			message: '',
			listLoading: false,
			lastPage: false,
		});

    onBeforeMount(() => {
			store.commit("root/setSearchValue", "")
			store.commit("root/setSearchOption", "title")
			store.commit("root/setPage", 1)
      store
        .dispatch("root/requestRoomList")
        .then((res) => {
          store.commit("root/setRoomList", res.data.contents.content);
        })
        .catch((err) => {
          console.log(err);
        });
    });

    // 백엔드에 axios 요청 보내서 응답 받아올 부분
    const infiniteHandler = () => {
			if(state.listLoading) return
			state.listLoading = true
			var page = store.getters["root/getPage"]
			store.commit("root/setPage", page + 1)
      store
        .dispatch("root/requestRoomList")
        .then((res) => {
					if(!res.data.contents.empty) {
						console.log('data : ', res.data.contents)
						store.commit("root/pushRoomList", res.data.contents.content);
						console.log('roomlist : ', state.roomList)
						state.noResult = false
					}
					else {
						state.noResult = true
						state.message = "No result found"
						store.commit("root/setPage", page)
					}
					state.listLoading = false
        })
        .catch((err) => {
          console.log(err);
					state.listLoading = false
        });
    };

    const clickConference = function (id) {
      router.push({
        name: "ConferenceDetail",
        params: {
          conferenceId: id,
        },
      });
    };

		const clickConference = function (id) {
			router.push({
				name: 'ConferenceDetail',
				params: {
					conferenceId: id,
				},
			});
		};

		const handleClick = id => {
			store
				.dispatch('root/roomLinkEntry', id)
				.then(res => {
					console.log(res.data);
					if (res.data.locked) {
						passwordConfirmModal.value.open(id);
					} else {
						store
							.dispatch('root/passwordConfirm', { roomId: id, password: '' })
							.then(res => {
								console.log(res);
								router.push({
									name: 'ConferenceDetail',
									params: {
										conferenceId: id,
										userName: store.getters['auth/getUserName'],
									},
								});
							})
							.catch(err => {
								console.log(err);
							});
					}
				})
				.catch(err => {
					console.log(err);
				});
		};
		return {
			state,
			store,
			infiniteHandler,
			clickConference,
			handleClick,
			passwordConfirmModal,
		};
	},
};
</script>
