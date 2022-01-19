<template>
  <main-header />
</template>
<style>
/* .infinite-list {
  padding-left: 0;
  max-height: calc(100% - 35px);
}

@media (min-width: 701px) and (max-width: 1269px) {
  .infinite-list {
    min-width: 700px;
  }
}

@media (min-width: 1270px) {
  .infinite-list {
    min-width: 1021px;
  }
}

.infinite-list .infinite-list-item {
  min-width: 335px;
  max-width: 25%;
  display: inline-block;
  cursor: pointer;
} */
</style>
<script>

import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import MainHeader from './components/main-header.vue'

export default {
  name: 'Home',

  components: {
    MainHeader
    
  },

  setup () {
    const router = useRouter()

    const state = reactive({
      count: 12
    })

    const load = function () {
      state.count += 4
    }

    const clickConference = function (id) {
      router.push({
        name: 'ConferenceDetail',
        params: {
          conferenceId: id
        }
      })
    }

    return { state, load, clickConference }
  }
}
</script>
