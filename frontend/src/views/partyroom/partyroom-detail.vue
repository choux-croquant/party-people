<template>
  <!-- {{ $route.params.conferenceId + '번 방 상세 보기 페이지' }} -->
  <div class="h-screen w-screen flex bg-tc-500">
    <room-sidebar></room-sidebar>
  </div>
</template>
<style>
</style>
<script>
import { reactive, onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'
import roomSidebar from './components/room-sidebar.vue'


export default {
  components: { roomSidebar },
  name: 'conference-detail',

  setup () {
    const route = useRoute()
    const store = useStore()
    const state = reactive({
      conferenceId: ''
    })

    // 페이지 진입시 불리는 훅
    onMounted(() => {
      state.conferenceId = route.params.conferenceId
      store.commit('root/setMenuActiveMenuName', 'home')
    })

    // 페이지 이탈시 불리는 훅
    onUnmounted(() => {
      state.conferenceId = ''
    })

    return { state }
  }
}
</script>
