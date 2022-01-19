// 회원 가입 모달 컴포넌트
<template>
  <div class="flex justify-center">
    <div class="w-full max-w-xs">
    <form class="bg-main-300 shadow-md rounded px-8 pt-6 pb-8 mb-4">
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
        <button class="bg-gradient-to-r from-main-100 to-sub-100 text-white font-bold py-1 px-24 rounded-full focus:outline-none focus:shadow-outline" type="button" >
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