<template>
  <base-modal ref="baseModal">
    <div class="flex justify-center">
      <div class="w-full max-w-xs">
        <form class="bg-main-200 shadow-md rounded px-8 pt-6 pb-8">
          <div class="flex justify-between items-start rounded-t bg-main-200 mb-4">
            <button type="button" @click="close()"
                    class="text-tc-500 bg-alert-200 hover:bg-gray-200 hover:text-gray-900 rounded-full text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-600 dark:hover:text-white">
              <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
                <path fill-rule="evenodd"
                      d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                      clip-rule="evenodd"></path>
              </svg>
            </button>
          </div>
          <!-- 룰렛 제목 -->
          <div class="">
            <input
                class="shadow appearance-none border rounded-full w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                id="username" type="text" placeholder="룰렛 제목을 입력해 주세요." v-model="voteTopic">
          </div>
        </form>
        <form class="bg-main-300 shadow-md rounded px-8 pt-4 pb-8 mb-4">
          <!-- 룰렛 시작 버튼 -->
          <div class="flex items-center justify-center">
            <button
                class="bg-gradient-to-r from-main-100 to-sub-100 text-white font-bold h-10 py-1 px-24 rounded-full focus:outline-none focus:shadow-outline"
                type="button" @click="rouletteStart">
              룰렛시작
            </button>
          </div>
        </form>
        <p class="text-center text-gray-500 text-xs">
          &copy;2022 PartyPeople Corp. All rights reserved.
        </p>
      </div>
    </div>
  </base-modal>
</template>

<style>

</style>

<script>
import BaseModal from './base-modal.vue';
import {ref} from 'vue';
import {useStore} from "vuex";

export default {
  name: 'rouletteCreateModal',
  components: {
    BaseModal
  },
  // Todo : props 추가하는 법, https://v3.ko.vuejs.org/ko-kr/guide/composition-api-setup.html
  setup() {
    const store = useStore()
    const baseModal = ref(null)
    const open = () => {
      console.log("roulette open")
      const sessionId = store.getters['root/getRoomId']

      store.dispatch('root/requestRoomUserList', {roomId: sessionId})
          .then((res) => {
            console.log('요청은 성공')

            const roomUserList = res.data.contents.reduce((acc, cur) => {
              acc.push({value : `${cur.userid}/${cur.nickname}`})
              return acc
            }, [])

            store.commit('root/setRoomUserList', roomUserList)
          })
          .catch((err) => {
            console.log('실패')
            console.log(err)
          })

      baseModal.value.openModal()
    }
    const close = () => {
      baseModal.value.closeModal()
    }
    return {baseModal, open, close, store}
  },
  data() {
    return {
      voteTopic: '',
      itemNum: 0,
      items: {},
    }
  },
  methods: {
    plusItem() {
      this.itemNum++
      console.log(this.itemNum)
    },
    minusItem() {
      this.itemNum--
    },
    rouletteStart() {
      console.log("roulette start button clicked")

      const signalParticipants = this.store.getters['root/getRoomUserList']
      const signalWinner = Math.floor(Math.random() * signalParticipants.length)
      const signalData = {
        participants : signalParticipants,
        winner : signalWinner
      }

      this.store.commit('root/setRouletteSignalData', signalData)
      this.$emit('sendRouletteSignal')
      this.close()
    }
  }

}
</script>
