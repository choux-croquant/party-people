<template>
  <div
    class="absolute flex top-0 h-screen z-20"
    :class="[right ? 'right-0 flex-row' : 'left-0 flex-row-reverse']"
  >
    <!-- chat-bar toggle button -->
    <button
      @click.prevent="toggle"
      class="w-6 h-48 p-0 my-auto rounded-l-full text-white bg-main-200 text-center focus:outline-none hover:bg-gray-500 transition-color duration-300"
    >
      <svg v-if="isSidebarOpen" class="p-0" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
        <rect width="40" height="40" fill="white" fill-opacity="0.01"/>
        <path d="M19 12L31 24L19 36" stroke="black" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
      <svg v-else class="p-0" viewBox="0 0 48 48" fill="none" xmlns="http://www.w3.org/2000/svg">
        <rect width="40" height="40" fill="white" fill-opacity="0.01"/>
        <path d="M31 36L19 24L31 12" stroke="black" stroke-width="4" stroke-linecap="round" stroke-linejoin="round"/>
      </svg>
    </button>

    <!-- chat-bar -->
    <div
      ref="content"
      class="transition-all pt-16 pb-12 w-80 duration-700 bg-main-300 overflow-hidden flex flex-col items-center justify-between"
      :class="[isSidebarOpen ? 'max-w-lg' : 'max-w-0']"
    >

      <!-- chatting list -->
      <ul class="border-2 h-4/5 w-5/6 border-main-100 p-4 overflow-y-auto">
        <li class="flex flex-col mb-2" v-for="i in 10" :key="i">
          <div class="flex flex-row items-center">
            <span class="text-main-100 text-md mr-2 font-bold">ssafy kim</span>
            <span class="text-tc-400 text-xs">20:25</span>
          </div>
          <div>
            <p class="text-tc-300 text-sm break-all text-left">Lorem Ipsum is simply dummy text of the printing and typesetting industry.</p>
          </div>
        </li>
      </ul>

      <div class="relative inline-flex border-3 rounded-lg border-main-100 w-1/2 m-0 h-7 p-0">
        <select class="px-4 py-0 text-sm form-select border-0 rounded-md text-gray-600 w-full h-5-5 border-transparent focus:border-transparent focus:ring-0 appearance-none">
          <option selected="selected">all</option>
          <option>C++</option>
          <option>Django</option>
          <option>Spring</option>
          <option>Vue.js</option>
        </select>
      </div>

      <!-- message -->
      <div class="w-4/5 p-0 flex flex-row justify-between items-center">
        <textarea v-model="message" @keydown.enter="sendMessage" class="message-box w-5/6 border-2 border-main-100 text-xs focus:border-main-100 focus:border-2" cols="" rows="2"></textarea>
        <!-- <input type="text" class="border-2 border-main-100 text-xs h-6" w-full> -->
        <svg @click="sendMessage" class="h-6 w-6 send-icon cursor-pointer" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px"
            viewBox="0 0 491.022 491.022" style="enable-background:new 0 0 491.022 491.022;" xml:space="preserve">
          <g>
            <g>
              <path d="M490.916,13.991c-0.213-1.173-0.64-2.347-1.28-3.307c-0.107-0.213-0.213-0.533-0.32-0.747
                c-0.107-0.213-0.32-0.32-0.533-0.533c-0.427-0.533-0.96-1.067-1.493-1.493c-0.427-0.32-0.853-0.64-1.28-0.96
                c-0.213-0.107-0.32-0.32-0.533-0.427c-0.32-0.107-0.747-0.32-1.173-0.427c-0.533-0.213-1.067-0.427-1.6-0.533
                c-0.64-0.107-1.28-0.213-1.92-0.213c-0.533,0-1.067,0-1.6,0c-0.747,0.107-1.493,0.32-2.133,0.533
                c-0.32,0.107-0.747,0.107-1.067,0.213L6.436,209.085c-5.44,2.347-7.893,8.64-5.547,14.08c1.067,2.347,2.88,4.373,5.227,5.44
                l175.36,82.453v163.947c0,5.867,4.8,10.667,10.667,10.667c3.733,0,7.147-1.92,9.067-5.12l74.133-120.533l114.56,60.373
                c5.227,2.773,11.627,0.747,14.4-4.48c0.427-0.853,0.747-1.813,0.96-2.667l85.547-394.987c0-0.213,0-0.427,0-0.64
                c0.107-0.64,0.107-1.173,0.213-1.707C491.022,15.271,491.022,14.631,490.916,13.991z M190.009,291.324L36.836,219.218
                L433.209,48.124L190.009,291.324z M202.809,437.138V321.831l53.653,28.267L202.809,437.138z M387.449,394.898l-100.8-53.013
                l-18.133-11.2l-0.747,1.28l-57.707-30.4L462.116,49.298L387.449,394.898z"/>
            </g>
          </g>
        </svg>
      </div>


    </div>
  </div>
</template>

<style>
.border-3 {
  border-width: 3px;
}

.h-5-5 {
  height: 1.375rem;
}

.send-icon {
  fill: #6667AB;
}

</style>

<script>
// const messageBox = document.querySelector('.message-box')
// import { reactive } from 'vue'

export default {

  data() {
    return {
      isSidebarOpen: true,
      right: true,
      message: "",
    };
  },

  methods: {
    toggle() {
      this.isSidebarOpen = !this.isSidebarOpen;
    },

    sendMessage(event) {
      // TODO: 메시지 전송 처리

      // enter키 누를 때 줄바꿈 방지
      event.preventDefault();
      // 메시지 창 초기화
      this.message = ""
    },
  }
};
</script>
