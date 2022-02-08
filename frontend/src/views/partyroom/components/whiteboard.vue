<template>
	<div class="flex flex-row justify-start">
		<div class="flex flex-col items-center p-2 shadow-lg rounded-2xl h-700">
			<!-- 두께 설정 -->
			<div class="p-4 flex flex-col items-center">
				<span class="mb-2 font-bold text-lg">Width</span>
				<input
					type="range"
					id="widthRange"
					min="0.1"
					max="20"
					value="2.5"
					step="0.1"
				/>
			</div>
			<div class="flex flex-col items-center justify-evenly py-3">
				<span class="mb-2 font-bold text-lg">Color</span>
				<!-- 색깔 설정 -->
				<div class="flex flex-col items-center" id="jsColors">
					<div
						class="flex w-10 h-10 cursor-pointer rounded-full shadow-lg mb-3 color-set selected"
						style="background-color: #2c2c2c"
					></div>
					<div
						class="flex w-10 h-10 cursor-pointer rounded-full shadow-lg mb-3 color-set"
						style="background-color: #ff3b30"
					></div>
					<div
						class="flex w-10 h-10 cursor-pointer rounded-full shadow-lg mb-3 color-set"
						style="background-color: #ff9500"
					></div>
					<div
						class="flex w-10 h-10 cursor-pointer rounded-full shadow-lg mb-3 color-set"
						style="background-color: #ffcc00"
					></div>
					<div
						class="flex w-10 h-10 cursor-pointer rounded-full shadow-lg mb-3 color-set"
						style="background-color: #4cd963"
					></div>
					<div
						class="flex w-10 h-10 cursor-pointer rounded-full shadow-lg mb-3 color-set"
						style="background-color: #5ac8fa"
					></div>
					<div
						class="flex w-10 h-10 cursor-pointer rounded-full shadow-lg mb-3 color-set"
						style="background-color: #0579ff"
					></div>
					<div
						class="flex w-10 h-10 cursor-pointer rounded-full shadow-lg mb-3 color-set"
						style="background-color: #5856d6"
					></div>
				</div>
				<!-- 지우개 -->
				<div class="w-10 h-10 p-0 cursor-pointer rounded-full bg-tc-500 flex items-center justify-center" id="eraser">
					<svg
						version="1.1"
						class="h-5/6 w-5/6 erasor-icon"
						xmlns="http://www.w3.org/2000/svg"
						viewBox="0 0 500 500"
					>
						<path
							fill="black"
							d="M317.468,0L72.048,245.418l14.41,14.41L0,346.287L165.713,512l86.459-86.46l14.411,14.411L512,194.532L317.468,0z
							M425.541,151.303L223.353,353.492l-64.844-64.844L360.697,86.459L425.541,151.303z M317.468,43.23l21.615,21.614L136.894,267.033
							l-21.615-21.616L317.468,43.23z M165.713,468.77L43.23,346.287l64.843-64.844l122.484,122.483L165.713,468.77z M266.582,396.721
							l-21.614-21.614l202.188-202.189l21.615,21.615L266.582,396.721z"
						/>
					</svg>
				</div>
				<div class="mt-10">
					<!-- 화이트보드 닫기 버튼 -->
					<button
						class="mr-1 bg-transparent hover:bg-main-200 text-main-200 text-md font-semibold hover:text-tc-500 py-2 px-4 border border-main-200 hover:border-transparent rounded"
						@click="closeWhiteboard"
					>
						Close
					</button>
					<!-- 초기화 버튼 -->
					<button
						class="bg-transparent hover:bg-sub-100 text-sub-100 text-md font-semibold hover:text-tc-500 py-2 px-4 border border-sub-100 hover:border-transparent rounded"
						@click="resetWhiteboard"
					>
						Reset
					</button>
				</div>
			</div>
		</div>

		<!-- 캔버스 (그림이 그려지는 곳) -->
		<canvas id="partypeopleCanvas" class="bg-tc-500 canvas h-700"></canvas>
	</div>
</template>

<style>
.h-700 {
	height: 700px;
}

.canvas {
	width: 700px;
	border-radius: 15px;
	box-shadow: 0 4px 6px rgba(50, 50, 93, 0.11), 0 1px 3px rgba(0, 0, 0, 0.08);
}

.erasor-icon {
	fill: black;
}

.selected {
	outline: 3px solid #f3799e;
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
			const eraser = document.querySelector('#eraser')

			const INITIAL_COLOR = '#2c2c2c';
			const CANVAS_SIZE = 700;

			state.canvas.width = CANVAS_SIZE;
			state.canvas.height = CANVAS_SIZE;

			state.ctx.fillStyle = 'white';
			state.ctx.fillRect(0, 0, CANVAS_SIZE, CANVAS_SIZE);
			state.ctx.strokeStyle = INITIAL_COLOR;
			state.ctx.lineWidth = 2.5;
			state.painting = false;

			const stopPainting = () => {
				// state.painting = false;
				sendPaintingState(false);
			}

			const startPainting = () => {
				// state.painting = true;
				sendPaintingState(true);
			}

			// 캔버스 내에서 마우스를 움직일 때마다 호출 (현재 좌표, 붓 색상/두께 정보를 담아 sendSignal 함수 호출)
			const onMouseMove = async event => {
				const x = event.offsetX;
				const y = event.offsetY;

				sendSignal(x, y, state.ctx.strokeStyle, state.ctx.lineWidth);
			};

			// 붓 색상 설정
			function handleColorClick(event) {
				const color = event.target.style.backgroundColor;
				state.ctx.strokeStyle = color;
				resetBtns();
				event.target.classList.add('selected')
				console.log(event.target)
			}

			// 붓 두께 설정
			const handleRangeChange = (event) => {
				const size = event.target.value;
				state.ctx.lineWidth = size;
			}

			const handleCM = (event) => {
				event.preventDefault();
			}

			const clickEraser = () => {
				resetBtns();
				eraser.classList.add('selected');
				state.ctx.strokeStyle = "#ffffff";
			}

			const resetBtns = () => {
				Array.from(state.colors).forEach(color =>
					color.classList.remove('selected')
				);
				eraser.classList.remove('selected');
			}

			if (state.canvas) {
				state.canvas.addEventListener('mousemove', onMouseMove);
				state.canvas.addEventListener('mousedown', startPainting);
				state.canvas.addEventListener('mouseup', stopPainting);
				state.canvas.addEventListener('mouseleave', stopPainting);
				state.canvas.addEventListener('contextmenu', handleCM);
			}

			if (eraser) {
				eraser.addEventListener('click', clickEraser)
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
		};

		// ctx 정보 보내기 step 4
		const addWhiteboardSignal = data => {
			let parsedData = JSON.parse(data);

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
		};

		// painting state 정보 보내기 step 4
		const addPaintingSignal = data => {
			let is_painting = JSON.parse(data);

			state.painting = is_painting;
		};

		// 화이트보드 창 닫기
		const closeWhiteboard = () => {
			emit('close-whiteboard');
		};

		// 화이트보드 초기화 (자기 화면에만 적용)
		const resetWhiteboard = () => {
			console.log('asdf')
			state.ctx.fillRect(0, 0, 700, 700);
		};

		return {
			state,
			sendSignal,
			addWhiteboardSignal,
			sendPaintingState,
			addPaintingSignal,
			closeWhiteboard,
			resetWhiteboard,
		};
	},
};
</script>
