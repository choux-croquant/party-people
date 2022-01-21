<template>
  <div>
    <button class="bg-gradient-to-r from-main-100 to-sub-100 text-white font-bold py-1 px-5 rounded-full focus:outline-none focus:shadow-outline" @click="start">START</button>
  </div>
</template>

<style>

</style>

<script>
import { useRouter } from 'vue-router'
export default {
  name:"LandingPage",
  setup() {
    const router = useRouter()
    const start = () => {
      router.push({ name: 'Home' })
    }
    return { start }
  },
}
</script>