// 파티룸 생성 시 정보를 입력하는 모달

<template>
  <base-modal ref='baseModal'>
    <div class="flex flex-col justify-between items-center bg-main-300 p-12 w-11/12 sm:w-5/6 lg:w-1/2 max-w-2xl mx-auto rounded-lg">
      <div class="flex flex-row h-40 justify-between justify-items-center w-full">
        <!-- 방 이름 / 인원 수 / 비밀번호 -->
        <div class="flex flex-col justify-between w-1/2">
          <div><input v-model="state.title" class="p-2 w-full text-tc-200 rounded-sm" type="text" placeholder="방 이름" required></div>
          <div><input v-model="state.capacity" class="p-2 w-full text-tc-200 rounded-sm" type="number" max="8" min="1" placeholder="인원 수" required></div>
          <div class="bg-tc-500 p-2 rounded-sm flex flex-row justify-start">
            <span>
              <svg height="21" viewBox="0 0 21 21" width="21" xmlns="http://www.w3.org/2000/svg"><g fill="none" fill-rule="evenodd" transform="translate(4 1)"><path d="m2.5 8.5-.00586729-1.99475098c-.00728549-4.00349935 1.32800361-6.00524902 4.00586729-6.00524902 2.19134421 0 3.6093703 1.31907891 4 4m-8 4h8.0225317c1.0543618 0 1.9181652.81587779 1.9945143 1.8507377l.0054778.1548972-.0169048 6c-.0031058 1.1023652-.8976224 1.9943651-1.999992 1.9943651h-8.005627c-1.1045695 0-2-.8954305-2-2v-6c0-1.1045695.8954305-2 2-2z" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"/><circle cx="6.5" cy="13.5" fill="currentColor" r="1.5"/></g></svg>
            </span>
            <input v-model="state.password" class="m-0 pl-2 w-4/5 text-tc-200" type="password" placeholder="비밀번호 설정">
          </div>
        </div>
        <!-- 썸네일 -->
        <div class="w-1/2 h-40 ml-5">
          <div class="flex items-center justify-center w-full h-full">
            <label class="flex flex-col justify-center items-center rounded-sm w-full h-full bg-tc-500 hover:bg-gray-300">
              <svg height="21" viewBox="0 0 21 21" width="21" xmlns="http://www.w3.org/2000/svg"><g fill="none" fill-rule="evenodd" transform="translate(3 3)"><g stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"><path d="m2.5.5h10c1.1045695 0 2 .8954305 2 2v10c0 1.1045695-.8954305 2-2 2h-10c-1.1045695 0-2-.8954305-2-2v-10c0-1.1045695.8954305-2 2-2z"/><path d="m14.5 10.5-3-3-3 2.985"/><path d="m12.5 14.5-9-9-3 3"/></g><circle cx="11" cy="4" fill="currentColor" r="1"/></g></svg>
              <p class="pt-1 text-sm tracking-wider text-tc-200 group-hover:text-gray-600">썸네일 등록</p>
              <input ref="state.thumbnailUrl" type="file" class="opacity-0 h-0" />
            </label>
          </div>
        </div>
      </div>
      <!-- 방 소개 -->
      <textarea
        type="text"
        name="partyroom-introduce"
        placeholder="방 소개"
        class="p-2 mt-5 bg-white text-tc-200 rounded-sm h-36 w-full"
        id=""
      ></textarea>
      <!-- 태그 -->
      <div class="w-full mt-5">
        <input type="text" placeholder="태그입력" class="text-tc-200 w-full p-2 rounded-sm">
      </div>
      <!-- 링크 공유 -->
      <div class="flex flex-row justify-between bg-white rounded-sm w-full mt-5 p-2">
        <span class="text-tc-200">링크공유</span>
        <svg @click="copyLink" class="text-tc-100" height="21" viewBox="0 0 21 21" width="21" xmlns="http://www.w3.org/2000/svg"><g fill="none" fill-rule="evenodd" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" transform="translate(2 2)"><path d="m16.5 10.5v-8c0-1.1045695-.8954305-2-2-2h-8c-1.1045695 0-2 .8954305-2 2v8c0 1.1045695.8954305 2 2 2h8c1.1045695 0 2-.8954305 2-2z"/><path d="m4.5 4.50345827h-2c-1.1045695 0-2 .8954305-2 2v7.99654173c0 1.1045695.8954305 2 2 2h.00345528l8.00000002-.0138241c1.1032187-.001906 1.9965447-.8967767 1.9965447-1.9999971v-1.9827205"/><path d="m10.5 3.5v6"/><path d="m10.5 3.5v6" transform="matrix(0 1 -1 0 17 -4)"/></g></svg>
      </div>
      <button type="submit" class="w-3/5 p-2 rounded-full bg-gradient-to-r from-main-200 to-sub-200 mt-7 text-tc-200">생성하기</button>
    </div>
  </base-modal>
</template>

<style>
input:focus {
  border: none;
  outline: none;
}
</style>

<script>
import { reactive, ref } from 'vue'
import BaseModal from './base-modal.vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'

export default {
  name: 'Home',
  components: {
    BaseModal
  },
  setup () {
    const router = useRouter()
    const store = useStore()
    const baseModal = ref(null)

    const state = reactive({
      capacity: '',
      description: '',
      password: '',
      title: ''
    })
    const thumbnailUrl = ref(null)

    const open = () => {
      console.log("rcopen")
      baseModal.value.openModal()
    }
    const close = () => {
      baseModal.value.closeModal()
    }
    const createRoom = () => {
      store.dispatch('root/createRoom', {
        capacity: state.capacity,
        description: state.form.description,
        password: state.password,
        title: state.title,
        thumbnailUrl: thumbnailUrl
      })
      .then(() => {
        router.push({ name: 'Home' })
      })
      .catch((err) => {
        console.log(err)
      })
    }
    return { state, open, close, createRoom }
  }
}
</script>
