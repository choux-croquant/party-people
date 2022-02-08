<template>
	<div class="flex flex-col items-center">
		<!-- 캔버스 (그림이 그려지는 곳) -->
		<canvas id="partypeopleCanvas" class="bg-tc-500 canvas"></canvas>
		<div class="flex flex-col items-center mt-6">
			<!-- 두께 설정 -->
			<div class="p-4 flex items-center">
				<span class="mr-3 font-bold">Width</span>
				<input
					type="range"
					id="widthRange"
					min="0.1"
					max="10"
					value="2.5"
					step="0.1"
				/>
			</div>
      <div class="flex justify-evenly p-4">
        <!-- 색깔 설정 -->
        <div class="flex mr-4" id="jsColors">
          <div
            class="flex w-12 h-12 cursor-pointer rounded-full shadow-lg color-set"
            style="background-color: #2c2c2c"
          ></div>
          <div
            class="flex w-12 h-12 cursor-pointer rounded-full shadow-lg color-set"
            style="background-color: white"
          ></div>
          <div
            class="flex w-12 h-12 cursor-pointer rounded-full shadow-lg color-set"
            style="background-color: #ff3b30"
          ></div>
          <div
            class="flex w-12 h-12 cursor-pointer rounded-full shadow-lg color-set"
            style="background-color: #ff9500"
          ></div>
          <div
            class="flex w-12 h-12 cursor-pointer rounded-full shadow-lg color-set"
            style="background-color: #ffcc00"
          ></div>
          <div
            class="flex w-12 h-12 cursor-pointer rounded-full shadow-lg color-set"
            style="background-color: #4cd963"
          ></div>
          <div
            class="flex w-12 h-12 cursor-pointer rounded-full shadow-lg color-set"
            style="background-color: #5ac8fa"
          ></div>
          <div
            class="flex w-12 h-12 cursor-pointer rounded-full shadow-lg color-set"
            style="background-color: #0579ff"
          ></div>
          <div
            class="flex w-12 h-12 cursor-pointer rounded-full shadow-lg color-set"
            style="background-color: #5856d6"
          ></div>
        </div>
        <!-- 화이트보드 닫기 버튼 -->
        <button 
          class="bg-transparent hover:bg-main-200 text-main-200 font-semibold hover:text-tc-500 py-2 px-4 border border-main-200 hover:border-transparent rounded"
          @click="closeWhiteboard"
        >
          Close
        </button>
      </div>
		</div>
	</div>
</template>

<style>
.canvas {
	width: 700px;
	height: 700px;
	border-radius: 15px;
	box-shadow: 0 4px 6px rgba(50, 50, 93, 0.11), 0 1px 3px rgba(0, 0, 0, 0.08);
}
</style>

<script>
import { reactive, onMounted } from 'vue';

export default {
	name: 'Whiteboard',

	setup(props, { emit }) {
		const state = reactive({
			canvas: null,
			ctx: null,
			colors: null,
			range: null,
			painting: false,
		});

		onMounted(() => {
			state.canvas = document.querySelector('#partypeopleCanvas');
			state.ctx = state.canvas.getContext('2d');
			state.colors = document.getElementsByClassName('color-set');
			state.range = document.querySelector('#widthRange');

			const INITIAL_COLOR = '#2c2c2c';
			const CANVAS_SIZE = 700;

			state.canvas.width = CANVAS_SIZE;
			state.canvas.height = CANVAS_SIZE;

			state.ctx.fillStyle = 'white';
			state.ctx.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);
			state.ctx.strokeStyle = INITIAL_COLOR;
			state.ctx.lineWidth = 2.5;
			state.painting = false;

			function stopPainting() {
				// state.painting = false;
				sendPaintingState(false);
				console.log('[P-0] ', false);
			}

			function startPainting() {
				// state.painting = true;
				sendPaintingState(true);
				console.log('[P-0] ', true);
			}

			// 캔버스 내에서 마우스를 움직일 때마다 호출 (현재 좌표, 붓 색상/두께 정보를 담아 sendSignal 함수 호출)
			const onMouseMove = async event => {
				const x = event.offsetX;
				const y = event.offsetY;

				sendSignal(x, y, state.ctx.strokeStyle, state.ctx.lineWidth);
				console.log('[S-0] ', x, y, state.ctx.strokeStyle, state.ctx.lineWidth);
			};

			// 붓 색상 설정
			function handleColorClick(event) {
				const color = event.target.style.backgroundColor;
				state.ctx.strokeStyle = color;
			}

			// 붓 두께 설정
			function handleRangeChange(event) {
				const size = event.target.value;
				state.ctx.lineWidth = size;
			}

			function handleCM(event) {
				event.preventDefault();
			}

			if (state.canvas) {
				state.canvas.addEventListener('mousemove', onMouseMove);
				state.canvas.addEventListener('mousedown', startPainting);
				state.canvas.addEventListener('mouseup', stopPainting);
				state.canvas.addEventListener('mouseleave', stopPainting);
				state.canvas.addEventListener('contextmenu', handleCM);
			}

			Array.from(state.colors).forEach(color =>
				color.addEventListener('click', handleColorClick),
			);

			if (state.range) {
				state.range.addEventListener('input', handleRangeChange);
			}
		});

		// ctx 정보 보내기 step 1
		const sendSignal = (x, y, color, width) => {
			emit('send-whiteboard-signal', x, y, color, width);
			console.log('[S-1] ', x, y, color, width);
		};

		// ctx 정보 보내기 step 4
		const addWhiteboardSignal = data => {
			let parsedData = JSON.parse(data);
			console.log('[S-4] ', parsedData);

			if (!state.painting) {
				state.ctx.beginPath();
				state.ctx.moveTo(parsedData.currentX, parsedData.currentY);
			} else {
				state.ctx.strokeStyle = parsedData.color;
				state.ctx.lineWidth = parsedData.width;
				state.ctx.lineTo(parsedData.currentX, parsedData.currentY);
				state.ctx.stroke();
			}
		};

		// painting state 정보 보내기 step 1
		const sendPaintingState = is_painting => {
			emit('send-painting-signal', is_painting);
			console.log('[P-1] ', is_painting);
		};

		// painting state 정보 보내기 step 4
		const addPaintingSignal = data => {
			let is_painting = JSON.parse(data);

			state.painting = is_painting;
			console.log('[P-4] ', is_painting);
		};

    // 화이트보드 창 닫기
    const closeWhiteboard = () => {
      emit('close-whiteboard');
    }

		return {
			state,
			sendSignal,
			addWhiteboardSignal,
			sendPaintingState,
			addPaintingSignal,
      closeWhiteboard,
		};
	},
};
</script>
