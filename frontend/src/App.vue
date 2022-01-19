<template>
  <router-view></router-view>
</template>

<script>
import Main from './views/main/main.vue'

export default {
  name: 'App',

  components: {
    Main,
  },

  data: () => ({
    //
  }),
}
</script>
