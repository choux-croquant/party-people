<template>
  <base-modal ref="baseModal">
    <div class="flex justify-center">
      <div class="w-full max-w-xs">
      <form class="bg-main-200 shadow-md rounded px-8 pt-6 pb-8">
        <div class="flex justify-between items-start rounded-t bg-main-200 mb-4">
            <button type="button" @click="close()" class="text-tc-500 bg-alert-200 hover:bg-gray-200 hover:text-gray-900 rounded-full text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-600 dark:hover:text-white">
                <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>  
            </button>
        </div>
        <!-- 투표 제목 -->
        <div class="">
          <input class="shadow appearance-none border rounded-full w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" id="username" type="text" placeholder="투표 제목을 입력해 주세요." v-model="voteTopic">
        </div>
      </form>
      <form class="bg-main-300 shadow-md rounded px-8 pt-4 pb-8 mb-4">
        <!-- 투표 항목 -->
        <ul>
          <li class="mt-4" v-for="i in itemNum" :key="i">
            <input class="shadow appearance-none border rounded-full w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" id="item" type="text" placeholder="내용을 입력하세요." v-model="items[i]">
          </li>
        </ul>
        <!-- 투표 항목 추가 삭제 버튼 -->
        <div class="flex items-center justify-center">
          <button type="button" class="mb-4 mt-2 mx-3" @click="plusItem">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-main-100" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v3m0 0v3m0-3h3m-3 0H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
          </button>
          <button type="button" class="mb-4 mt-2 mx-3" @click="minusItem">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-main-100" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12H9m12 0a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
          </button>
        </div>
        <!-- 투표 시작 버튼 -->
        <div class="flex items-center justify-center">
          <button class="bg-gradient-to-r from-main-100 to-sub-100 text-white font-bold h-10 py-1 px-24 rounded-full focus:outline-none focus:shadow-outline" type="button" @click="test">
            투표시작
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
import BaseModal from './base-modal.vue'
import { ref } from 'vue'

export default {
  name: 'voteCreateModal',
  components : {
    BaseModal
  },
  setup() {
    const baseModal = ref(null)
    const open = () => {
      console.log("voteopen")
      baseModal.value.openModal()
    }
    const close = () => {
      baseModal.value.closeModal()
    }
    return { baseModal, open, close}
  },
  data () {
    return {
      voteTopic: '',
      itemNum: 2,
      items: {}
    }
  },
  methods: {
    plusItem () {
      this.itemNum ++
      console.log(this.itemNum)
    },
    minusItem () {
      this.itemNum --
    },
    test () {
      console.log(this.items)
    }
  }



}
</script>