// 파티룸 생성 시 정보를 입력하는 모달

<template>
  <div class="flex flex-col justify-between items-center bg-main-300 p-12 w-11/12 sm:w-5/6 lg:w-1/2 max-w-2xl mx-auto rounded-lg">
    <div class="flex flex-row h-40 justify-between justify-items-center w-full">
      <!-- 방 이름 / 인원 수 / 비밀번호 -->
      <div class="flex flex-col justify-between w-1/2">
        <div><input class="p-2 w-full text-tc-200 rounded-sm" type="text" placeholder="방 이름" required></div>
        <div><input class="p-2 w-full text-tc-200 rounded-sm" type="number" max="8" min="1" placeholder="인원 수" required></div>
        <div class="bg-tc-500 p-2 rounded-sm">
          <input class="m-0 w-full text-tc-200" type="password" placeholder="비밀번호 설정">
        </div>
      </div>
      <!-- 썸네일 -->
      <div class="w-1/2 h-40 ml-5">
        <div class="flex items-center justify-center w-full h-full">
          <label class="flex flex-col justify-center rounded-sm w-full h-full bg-tc-500 hover:bg-gray-300">
            <p class="align-middle pt-1 text-sm tracking-wider text-tc-200 group-hover:text-gray-600">썸네일 등록</p>
            <input type="file" class="opacity-0 h-0" />
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
      <span>공유icon</span>
    </div>
    <button type="submit" class="w-3/5 p-2 rounded-full bg-gradient-to-r from-main-200 to-sub-200 mt-7 text-tc-200">생성하기</button>
  </div>
</template>

<style>

</style>

<script>
// import { ref } from 'vue'

export default {
  name: 'Home',

  props: {
  },

  setup () {
  }
}
</script>
