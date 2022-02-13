<template>
	<div class="justify-center h-screen">
    <div class="h-full pt-40 bg-gradient-to-r from-main-200 to-sub-200">
      <img class="party-people-logo block w-1/2 mx-auto" src="@/assets/images/partyPeopleLanding.svg">
      <button
			class="mt-20 w-80 h-16 text-3xl shadow-lg bg-gradient-to-r from-main-100 to-sub-100 text-white font-bold py-1 px-5 rounded-full focus:outline-none focus:shadow-outline"
			@click="start"
		  >
        START
		  </button>
    </div>
	</div>
</template>

<style></style>

<script>
import { useRouter } from 'vue-router';
import anime from 'animejs/lib/anime.es.js';
import { onMounted } from '@vue/runtime-core';

export default {
	name: 'LandingPage',
	setup() {
		const router = useRouter();
		const start = () => {
			router.push({ name: 'Home' });
		};

    onMounted(() => {
      anime({
        targets: '.party-people-logo',
        translateX: ['-2000px', '0px'],
        duration: 8000
      })
    })
		return { start };
	}
};
</script>
