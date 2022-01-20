// 회원 가입 모달 컴포넌트
<template>
  <div class="flex justify-center">
    <div class="w-full max-w-xs">
    <form class="bg-main-300 shadow-md rounded px-8 pt-6 pb-8 mb-4">
      <div class="flex justify-between items-start rounded-t border-b bg-main-300">
          <button type="button" class="text-tc-500 bg-alert-200 hover:bg-gray-200 hover:text-gray-900 rounded-full text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-toggle="SignupModal">
              <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>  
          </button>
      </div>
      <img class="w-40 h-24 mb-4 rounded mx-auto" alt="Vue logo" src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg">
      <div class="mb-4">
        <input class="shadow appearance-none border rounded-full w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" id="username" type="text" placeholder="유저아이디">
      </div>
      <div class="mb-4">
        <input class="shadow appearance-none border rounded-full w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" id="username" type="text" placeholder="닉네임">
      </div>
      <div class="mb-4">
        <input class="shadow appearance-none border rounded-full w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" id="password" type="password" placeholder="비밀번호">
      </div>
      <div class="mb-4">
        <input class="shadow appearance-none border rounded-full w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" id="password" type="password" placeholder="비밀번호 확인">
      </div>
      <div class="mb-4">
        <input class="shadow appearance-none border rounded-full w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" id="username" type="text" placeholder="이메일">
      </div>
      <div class="mb-6">
        <input class="shadow appearance-none border rounded-full w-full py-2 px-3 mb-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" id="username" type="text" placeholder="전화번호">
      </div>
      <div class="flex items-center justify-center">
        <button data-modal-toggle="SignupModal" class="bg-gradient-to-r from-main-100 to-sub-100 text-white font-bold h-10 py-1 px-24 rounded-full focus:outline-none focus:shadow-outline" type="button" >
          가입하기
        </button>
      </div>
    </form>
    <p class="text-center text-gray-500 text-xs">
      &copy;2022 PartyPeople Corp. All rights reserved.
    </p>
    </div>
  </div>
</template>
<style>

</style>
<script>

export default {
  name: 'SingupModal',
  setup() {
    
  },
}
</script>