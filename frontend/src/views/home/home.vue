<template>
  <main-header />
   
  <div class="conference-list-wrap pl-0" style="overflow: auto"> 
    <infinite-scroll @infinite-scroll="infiniteHandler">
      <conference :key="room.id" v-for="room in state.roomList" class="conference-card m-5" @click="handleClick(room.id)" :room="room" /> 
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

import { reactive, ref, computed, onBeforeMount } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import MainHeader from './components/main-header.vue'
import Conference from './components/conference.vue'
import InfiniteScroll from "infinite-loading-vue3";
import PasswordConfirm from '@/teleport/password-confirm.vue'

export default {
  name: 'Home',

  components: {
    MainHeader,
    Conference,
    InfiniteScroll,
    PasswordConfirm,
  },

  setup () {
    const router = useRouter()
    const store = useStore()
    const passwordConfirmModal = ref(null)
    const state = reactive({
      count: 12,
      roomList: computed(() => store.getters['root/getRoomList'])
    })

    onBeforeMount(() => {
      store.dispatch('root/requestRoomList')
      .then((res) => {
        store.commit('root/setRoomList', res.data.contents.content)
      })
      .catch((err) => {
        console.log(err)
      })
    })

    // 백엔드에 axios 요청 보내서 응답 받아올 부분
    const infiniteHandler = () => {
      console.log("request")
      state.count += 4

      // window.onscroll = () => {
      //   let bottomOfWindow = document.documentElement.scrollTop + window.innerHeight === document.documentElement.offsetHeight;
      //   if (bottomOfWindow) {
      //     console.log("request / response")
      //     state.count += 4
      //   }
      // }
    }

    const clickConference = function (id) {
      router.push({
        name: 'ConferenceDetail',
        params: {
          conferenceId: id
        }
      })
    }

    const handleClick = (id) => {
      store.dispatch('root/roomLinkEntry', id)
      .then((res) => {
        console.log(res.data)
        if (res.data.locked) {
          passwordConfirmModal.value.open(id)
        }
        else {
          router.push({
            name: 'ConferenceDetail',
            params: {
              conferenceId: id,
              userName: store.getters['auth/getUserName']
            }
          })
        }
      })
      .catch((err) => {
        console.log(err)
      })
    }
    return { state, store, infiniteHandler, clickConference, handleClick, passwordConfirmModal }
  },
}
  // mounted() {
  //   // 백엔드에 axios 요청 보내서 응답 받아올 부분
  //   const infiniteHandler = () => {
  //     window.onscroll = () => {
  //       let bottomOfWindow = document.documentElement.scrollTop + window.innerHeight === document.documentElement.offsetHeight;
  //       if (bottomOfWindow) {
  //         console.log("request / response")
  //         this.state.count += 4
  //       }
  //     }
  //   }
  // }
</script>
