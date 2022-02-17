<template>
	<div>
		<div class="navigation-wrapper">
			<div ref="container" class="keen-slider h-screen">
				<div
					class="bg-gradient-to-r from-main-200 to-sub-200 keen-slider__slide number-slide1"
				>
					<img
						class="party-people-logo w-3/5 mx-auto"
						src="@/assets/images/partyPeopleLanding.svg"
					/>
					<button
						class="absolute border-4 border-sub-300 bottom-20 w-60 h-16 text-3xl shadow-lg bg-main-100 text-white font-bold py-1 px-5 rounded-full focus:outline-none focus:shadow-outline hover:bg-main-200"
						@click="start"
					>
						START
					</button>
				</div>
				<div
					class="bg-gradient-to-r from-main-200 to-sub-200 keen-slider__slide number-slide2"
				>
					<img
						class="party-people-logo w-1/2 mb-16 rounded-xl mx-auto"
						src="@/assets/images/main-page.png"
					/>
					<div class="absolute landing-desc-large bottom-40 text-tc-500">
						메인 페이지에 대한 설명이 들어갈 공간
					</div>
					<button
						class="absolute border-4 border-sub-300 bottom-20 w-60 h-16 text-3xl shadow-lg bg-main-100 text-white font-bold py-1 px-5 rounded-full focus:outline-none focus:shadow-outline hover:bg-main-200"
						@click="start"
					>
						START
					</button>
				</div>
			</div>
			<svg
				@click="slider.prev()"
				:class="{
					arrow: true,
					'arrow--left': true,
					'arrow--disabled': current === 0,
				}"
				xmlns="http://www.w3.org/2000/svg"
				viewBox="0 0 24 24"
			>
				<path
					d="M16.67 0l2.83 2.829-9.339 9.175 9.339 9.167-2.83 2.829-12.17-11.996z"
				></path>
			</svg>
			<svg
				v-if="slider"
				@click="slider.next()"
				:class="{
					arrow: true,
					'arrow--right': true,
					'arrow--disabled': current === slider.track.details.slides.length - 1,
				}"
				xmlns="http://www.w3.org/2000/svg"
				viewBox="0 0 24 24"
			>
				<path d="M5 3l3.057-3 11.943 12-11.943 12-3.057-3 9-9z"></path>
			</svg>
		</div>
	</div>
</template>

<script>
import { computed, onMounted, ref } from 'vue';
import anime from 'animejs/lib/anime.es.js';
import { useKeenSlider } from 'keen-slider/vue.es';
import 'keen-slider/keen-slider.min.css';
import { useRouter } from 'vue-router';
export default {
	setup() {
		const router = useRouter();
		const current = ref(0);
		const [container, slider] = useKeenSlider({
			initial: current.value,
			slideChanged: s => {
				current.value = s.track.details.rel;
			},
		});

		const dotHelper = computed(() =>
			slider.value
				? [...Array(slider.value.track.details.slides.length).keys()]
				: [],
		);

		const start = () => {
			router.push({
				name: 'Home',
			});
		};
		onMounted(() => {
			anime({
				targets: '.party-people-logo',
				translateX: ['-2000px', '0px'],
				duration: 6000,
			});
		});
		return { container, start, current, dotHelper, slider };
	},
};
</script>

<style>
[class^='number-slide'],
[class*=' number-slide'] {
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 50px;
	font-weight: 500;
	height: 200px;
}
.landing-desc-large {
	font-family: Pretendard;
	font-weight: 700;
}
.navigation-wrapper {
	position: relative;
}

.arrow {
	width: 30px;
	height: 30px;
	position: absolute;
	top: 50%;
	transform: translateY(-50%);
	-webkit-transform: translateY(-50%);
	fill: #fff;
	cursor: pointer;
}
.arrow--left {
	left: 5px;
}
.arrow--right {
	left: auto;
	right: 5px;
}
.arrow--disabled {
	fill: rgba(255, 255, 255, 0.5);
}
</style>
