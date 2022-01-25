// 파티 룸 카드 컴포넌트

<template>
  <div class="max-w-sm rounded-xl overflow-hidden shadow-lg conference-card">
    <img class="w-full" :src="thumbnailUrl" alt="PartyRoom Thumbnail">
    <div class="px-6 py-4">
      <div class="flex flex-row justify-between">
        <span class="font-bold text-xl mb-2">{{ room.title }}</span>
        <div class="inline">
          <span class="rounded-full main-bg-color ml-3 px-3 py-1 text-white h-8">{{currentUserCount}} / {{maxUserCount}}</span>
        </div>
      </div>
      <p class="text-gray-700 text-base line-clamp-3 card-description">{{ desc }}</p>
    </div>
    <div class="px-6 pt-4 pb-2 flex flex-row flex-wrap bg-gradient-to-r from-main-200 to-sub-200">
      <span class="inline-block partyroom-tag rounded-full px-3 py-1 text-sm font-semibold text-white mr-2 mb-2">#파티</span>
      <span class="inline-block partyroom-tag rounded-full px-3 py-1 text-sm font-semibold text-white mr-2 mb-2">#파티</span>
      <span class="inline-block partyroom-tag rounded-full px-3 py-1 text-sm font-semibold text-white mr-2 mb-2">#파티</span>
      <span class="inline-block partyroom-tag rounded-full px-3 py-1 text-sm font-semibold text-white mr-2 mb-2">#파티</span>
      <span class="inline-block partyroom-tag rounded-full px-3 py-1 text-sm font-semibold text-white mr-2 mb-2">#파티</span>
      <span class="inline-block partyroom-tag rounded-full px-3 py-1 text-sm font-semibold text-white mr-2 mb-2">#파티</span>
    </div>
  </div>
</template>

<style>
.main-bg-color {
  background-color: #6667AB;
}

.partyroom-tag {
  background-color: #FF95B5;
}

/* 3줄 넘어가면 말줄임 표시 */
.card-description {
  overflow: hidden;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;
}

/* 테블릿, 모바일은 2줄 넘어가면 말줄임 표시 */
@media (max-width: 1269px) {
  .card-description {
    -webkit-line-clamp: 2;
  }
}
</style>

<script>
export default {
  name: 'Home',

  props: {
    room: {
      type: Object
    },
    thumbnailUrl: {
      type: String,
      default: 'https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg',
    },
    title: {
      type: String,
      default: '파티룸 이름'
    },
    desc: {
      type: String,
      default: "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum."
    },
    currentUserCount: {
      type: Number,
      default: 1,
    },
    maxUserCount: {
      type: Number,
      default: 8,
    }
  },

  setup () {}
}
</script>
