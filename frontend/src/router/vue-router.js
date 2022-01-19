import { createRouter, createWebHistory } from 'vue-router'
// import LandingPage from '@views/landing/landing-page'
import Home from '@/views/home/home'
import ConferencesDetail from '@/views/conferences/conference-detail'


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
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.afterEach((to) => {
  console.log(to)
})

export default router
