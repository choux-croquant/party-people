import { createRouter, createWebHistory } from 'vue-router'
// import LandingPage from '@views/landing/landing-page'
import Home from '@/views/home/home'
import LoginModal from '@/views/home/components/login-modal'
import ConferencesDetail from '@/views/partyroom/partyroom-detail'
import SignupModal from '@/views/home/components/signup-modal'


const routes = [
  // {
  //   path: '/',
  //   name: 'LandingPage',
  //   component: LandingPage
  // },
  {
    path: '/home',
    name: 'Home',
    component: Home
  },
  {
    path: '/conferences/:conferenceId',
    name: 'ConferenceDetail',
    component: ConferencesDetail
  },
  {
    path: '/login-modal',
    name: 'LoginModal',
    component: LoginModal
  },
  {
    path: '/signup-modal',
    name: 'SignupModal',
    component: SignupModal
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.afterEach((to) => {
  console.log(to)
})

export default router
