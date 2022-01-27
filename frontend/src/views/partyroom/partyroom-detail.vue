<template>
  <div class="h-screen w-screen flex bg-tc-500">
    <div class="fixed inset-0 flex z-40">
      <room-sidebar></room-sidebar>
      <room-chat></room-chat>
      <room-bottombar></room-bottombar>
      <timer></timer>
    </div>
  </div>
</template>
<style>
</style>
<script>
  
import { reactive, onMounted, onUnmounted } from 'vue'
import { useStore } from 'vuex'
import { useRoute } from 'vue-router'
import roomSidebar from './components/room-sidebar.vue'
import RoomChat from './components/room-chat.vue'
import roomBottombar from './components/room-bottombar.vue'
import timer from './components/timer.vue'

export default {
  components: { roomSidebar, RoomChat, roomBottombar, timer },
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
