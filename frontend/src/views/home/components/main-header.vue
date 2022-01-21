<template>
  <nav class="bg-white border-gray-200 px-4 py-5">
    <div class="flex flex-wrap justify-between items-center w-full my-0">
      <a href="#" class="flex-none ml-2 mr-6">
        <img class="w-10 h-10" src="@/assets/logo.png"/>
      </a>
      <div class="flex-auto">
        <div class="relative mr-3">
          <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
            <svg class="w-5 h-5 text-tc-400 fill-current" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"></path></svg>
          </div>
          <input type="text" id="party-room-search" class="block border-0 appearance-none rounded-full shadow-md h-10 p-2 pl-10 w-full text-tc-200 bg-main-300 sm:text-sm focus:outline-none focus:border-main-100 focus:ring-2 focus:ring-main-100" placeholder="Search for party room">
        </div>
      </div>
      
      <div class="dropdown inline-block flex-none relative">
        <button class="flex-none rounded-full shadow-lg w-10 h-10 bg-main-200">
          <svg class="fill-current text-tc-500 ml-3 h-4 w-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20"><path d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"/> </svg>
        </button>
        <ul class="dropdown-menu absolute hidden right-0 w-40 text-gray-700">
          <button class="rounded-full w-32 h-10 mt-2 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100" type="button">제목</button>
          <button class="rounded-full w-32 h-10 mt-2 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100" type="button">내용</button>
          <button class="rounded-full w-32 h-10 mt-2 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100" type="button">호스트</button>
        </ul>
      </div>

      <div class="flex-none hidden md:block w-1/6"></div>
      <div v-if="!state.loginState" class="flex-none hidden md:block">
        <button data-modal-toggle="LoginModal" class="rounded-full w-32 h-10 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100" type="button">Login</button>
        <button data-modal-toggle="SignupModal" class="rounded-full w-32 h-10 ml-4 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100" type="button">Sign-Up</button>  
      </div>
      <div v-else class="flex-none hidden md:block">
        <button class="rounded-full w-32 h-10 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100" type="button">Add+</button>
        <button @click="logOut()" class="rounded-full w-32 h-10 ml-4 font-bold shadow-lg bg-alert-200 text-tc-500 hover:bg-alert-100" type="button">Log-Out</button>  
      </div>
    </div>
  </nav>


  <!-- Login modal -->
  <div id="LoginModal" aria-hidden="true" class="hidden overflow-y-auto overflow-x-hidden fixed right-0 left-0 top-4 z-50 justify-center items-center h-modal md:h-full md:inset-0">
    <login-modal />
  </div>
  <!-- Signup modal -->
  <div id="SignupModal" aria-hidden="true" class="hidden overflow-y-auto overflow-x-hidden fixed right-0 left-0 top-4 z-50 justify-center items-center h-modal md:h-full md:inset-0">
    <signup-modal />
  </div>
  
  <!-- <el-row
    class="main-header"
    :gutter="10"
    :style="{ 'height': height }">
    <div class="hide-on-small">
      <div class="logo-wrapper" @click="clickLogo"><div class="ic ic-logo"/></div>
      <div class="tool-wrapper">
        <div class="search-field">
          <el-input
            placeholder="검색"
            prefix-icon="el-icon-search"
            v-model="state.searchValue">
          </el-input>
        </div>
        <div class="button-wrapper">
          <el-button round type="primary">회원가입</el-button>
          <el-button type="primary" @click="clickLogin">로그인</el-button>
        </div>
      </div>

    </div>
    <div class="hide-on-big">
      <div class="menu-icon-wrapper" @click="changeCollapse"><i class="el-icon-menu"></i></div>
      <div class="logo-wrapper" @click="clickLogo"><div class="ic ic-logo"/></div>
      <div class="menu-icon-wrapper"><i class="el-icon-search"></i></div>
      <div class="mobile-sidebar-wrapper" v-if="!state.isCollapse">
        <div class="mobile-sidebar">
          <div class="mobile-sidebar-tool-wrapper">
            <div class="logo-wrapper"><div class="ic ic-logo"/></div>
            <el-button type="primary" class="mobile-sidebar-btn login-btn" @click="clickLogin">로그인</el-button>
            <el-button class="mobile-sidebar-btn register-btn">회원가입</el-button>
          </div>
          <el-menu
            :default-active="String(state.activeIndex)"
            active-text-color="#ffd04b"
            class="el-menu-vertical-demo"
            @select="menuSelect">
            <el-menu-item v-for="(item, index) in state.menuItems" :key="index" :index="index.toString()">
              <i v-if="item.icon" :class="['ic', item.icon]"/>
              <span>{{ item.title }}</span>
            </el-menu-item>
          </el-menu>
        </div>
        <div class="mobile-sidebar-backdrop" @click="changeCollapse"></div>
      </div>
    </div>
  </el-row> -->
</template>
<script>
import { computed, reactive } from 'vue'
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import LoginModal from './login-modal.vue'
import SignupModal from './signup-modal.vue'
export default {
  name: 'main-header',

  components: {
    LoginModal,
    SignupModal
  },
  props: {
    
  },

  setup() {
    const store = useStore()
    const router = useRouter()

    const state = reactive({
      loginState : computed(() => store.getters['root/getLoginState']),
      searchValue: null,
    })

    const logOut = () => {
      localStorage.removeItem('access_token')
      store.commit('root/setLoginState', false)
      router.push({ name: 'Home' })
    }
    // if (state.activeIndex === -1) {
    //   state.activeIndex = 0
    //   store.commit('root/setMenuActive', 0)
    // }

    // const menuSelect = function (param) {
    //   store.commit('root/setMenuActive', param)
    //   const MenuItems = store.getters['root/getMenus']
    //   let keys = Object.keys(MenuItems)
    //   router.push({
    //     name: keys[param]
    
    //   })
    // }


    // const clickLogin = () => {
    //   emit('openLoginDialog')
    // }

    // const changeCollapse = () => {
    //   state.isCollapse = !state.isCollapse
    // }

    return { state, logOut }
  }
}
</script>
<style>
.dropdown:hover .dropdown-menu {
  display: block;
}
</style>
