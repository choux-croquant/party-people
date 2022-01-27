<template>
  <div v-if="count" class="content-center">
    <button type="button" class="bg-main-200" :disabled="counting" @click="startCountdown">
      <vue-countdown v-if="counting" :time="state.min*60*1000+(state.sec*1000)" @end="onCountdownEnd" v-slot="{ minutes, seconds }"> {{ minutes }} : {{ seconds }} </vue-countdown>
      <span v-else>Fetch Verification Code</span>
    </button>
    <button @click="stopCountdown">stop</button>
  </div>
</template>

<style>

</style>

<script>
import { computed, reactive, watch} from 'vue';
import { useStore } from 'vuex';
export default {
  name : "timer",

  setup() {
    const store = useStore()
    const state = reactive ({
      time: computed(() => store.getters['root/getTimerTime']),
      min: 0,
      sec: 0
    })
    watch(state.time, () => {
      // console.log('watch')
      state.min = state.time.min
      state.sec = state.time.sec
      // console.log(state.min)
    })
    return { state }
  },

  data() {
    return {
      count: true,
      counting: false,
    };
  },
  methods: {
    startCountdown: function () {
      this.counting = true;
    },
    onCountdownEnd: function () {
      this.counting = false;
    },
    stopCountdown: function () {
      this.counting = false
    }
  },
};
</script>