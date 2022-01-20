<template>
  <main-header />
   
  <div class="conference-list-wrap pl-0" style="overflow: auto"> 
    <infinite-scroll @infinite-scroll="infiniteHandler">
      <div :key="i" v-for="i in state.count" @click="clickConference(i)" class="conference-card m-5"> 
        <conference /> 
      </div> 
    </infinite-scroll>
  </div>

  <footer class="display: none">not showing</footer>
  
  <!-- <infinite-scroll @infinite-scroll="infiniteHandler">
    <div class="conference-list-wrap pl-0" style="overflow: auto"> 
      <div :key="i" v-for="i in state.count" @click="clickConference(i)" class="conference-card m-5"> 
        <conference /> 
      </div> 
    </div>
  </infinite-scroll>  -->

</template>
<style>
.conference-list-wrap {
  max-height: calc(100% - 35px);
}

@media (min-width: 701px) and (max-width: 1269px) {
  .conference-list-wrap {
    min-width: 700px;
  }
}

@media (min-width: 1270px) {
  .conference-list-wrap {
    min-width: 1021px;
  }
}

.conference-list-wrap .conference-card {
  min-width: 335px;
  max-width: 25%;
  display: inline-block;
  cursor: pointer;
}
</style>
<script>

import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import MainHeader from './components/main-header.vue'
import Conference from './components/conference.vue'
import InfiniteScroll from "infinite-loading-vue3";

export default {
  name: 'Home',

  components: {
    MainHeader,
    Conference,
    InfiniteScroll,
  },

  setup () {
    const router = useRouter()

    const state = reactive({
      count: 12
    })

    // 백엔드에 axios 요청 보내서 응답 받아올 부분
    const infiniteHandler = () => {
      console.log("request")
      state.count += 4

      // window.onscroll = () => {
      //   let bottomOfWindow = document.documentElement.scrollTop + window.innerHeight === document.documentElement.offsetHeight;
      //   if (bottomOfWindow) {
      //     console.log("request / response")
      //     state.count += 4
      //   }
      // }
    }

    const clickConference = function (id) {
      router.push({
        name: 'ConferenceDetail',
        params: {
          conferenceId: id
        }
      })
    }

    // function infiniteHandler() {
    //   window.addEventListener('scroll', () => {
    //     if (getScrollTop() < getDocumentHeight() - window.innerHeight) 
    //       return;
        
    //     console.log('request')
    //     state.count += 4
    //   });
    // }

    // // 현재 스크롤한 높이를 구하는 함수 
    // function getScrollTop() {
    //   return (window.pageYOffset !== undefined) ? window.pageYOffset : (document.documentElement || document.body.parentNode || document.body).scrollTop;
    // }

    // // 문서의 높이를 구하는 함수
    // function getDocumentHeight() {
    //   const body = document.body;
    //   const html = document.documentElement;
    
    //   return Math.max(
    //       body.scrollHeight, body.offsetHeight,
    //       html.clientHeight, html.scrollHeight, html.offsetHeight
    //   );
    // }

    return { state, infiniteHandler, clickConference }
  },

  // mounted() {
  //   // 백엔드에 axios 요청 보내서 응답 받아올 부분
  //   const infiniteHandler = () => {
  //     window.onscroll = () => {
  //       let bottomOfWindow = document.documentElement.scrollTop + window.innerHeight === document.documentElement.offsetHeight;
  //       if (bottomOfWindow) {
  //         console.log("request / response")
  //         this.state.count += 4
  //       }
  //     }
  //   }
  // }
}
</script>
