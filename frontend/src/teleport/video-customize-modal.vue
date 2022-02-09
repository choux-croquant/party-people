// 비디오 커스터마이징 모달
<template>
	<base-modal ref="baseModal">
		<div class="w-full max-w-2xl bg-main-300 shadow-md rounded px-6 pt-6 pb-4">
			<!-- close button -->
			<div class="flex justify-between items-start rounded-t bg-main-300">
				<button
					@click="close()"
					type="button"
					class="text-tc-500 bg-alert-200 hover:bg-gray-200 hover:text-gray-900 rounded-full text-sm p-1.5 ml-auto inline-flex items-center dark:hover:bg-gray-600 dark:hover:text-white"
				>
					<svg
						class="w-5 h-5"
						fill="currentColor"
						viewBox="0 0 20 20"
						xmlns="http://www.w3.org/2000/svg"
					>
						<path
							fill-rule="evenodd"
							d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
							clip-rule="evenodd"
						></path>
					</svg>
				</button>
			</div>
			<div class="flex flex-col justify-between items-center">
				<!-- 커스터마이징 적용 미리보기 창 -->
				<div
					class="w-80 border-main-100 border-2 h-52 mt-2 mb-4 bg-main-100 shadow-lg"
				>
					<img
						class="w-full h-full"
						src="https://fuss10.elemecdn.com/e/5d/4a731a90594a4af544c0c25941171jpeg.jpeg"
						alt="customizing display"
					/>
				</div>

				<!-- 카테고리와 버튼 -->
				<div class="flex flex-row justify-between w-full mb-3">
					<div
						class="relative inline-flex border-3 rounded-lg border-main-100 w-1/4 m-0 h-9 p-0"
					>
						<select
							v-model="state.selectedCategory"
							class="cursor-pointer font-bold px-4 py-0 text-sm form-select border-0 rounded-md text-gray-600 w-full h-7.5 border-transparent focus:border-transparent focus:ring-0 appearance-none"
						>
							<option
								v-for="category in state.categories"
								:value="category.name"
								:key="category.id"
							>
								{{ category.name }}
							</option>
						</select>
					</div>
					<div class="flex flew-row items-center">
						<button
							@click="applyVideoCustom()"
							class="rounded-full w-24 h-8 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100"
							type="button"
						>
							Apply
						</button>
						<button
							@click="cancelVideoCustom()"
							class="rounded-full w-24 h-8 font-bold shadow-lg bg-main-200 text-tc-500 hover:bg-main-100 ml-3"
							type="button"
						>
							Cancel
						</button>
					</div>
				</div>

				<!-- 커스터마이징 아이템 -->
				<div
					class="w-full h-32 border-3 rounded-lg border-main-100 shadow-lg flex flex-col px-3 py-2"
				>
					<!-- 스티커 선택 -->
					<div
						v-if="state.selectedCategory === '스티커'"
						id="scrolling-content"
						class="flex overflow-x-auto h-full"
					>
						<div
							v-for="custom in state.stickerList"
							:key="custom.id"
							class="w-1/4 cursor-pointer h-full px-4 flex-shrink-0"
							v-show="showCategory(custom.category)"
							@click="clickVideoCustom(custom)"
						>
							<img
								:src="custom.url"
								:class="[
									custom.id === state.selectedCustomId
										? 'border-4 border-sub-200'
										: '',
								]"
							/>
						</div>
					</div>
					<!-- 비주얼 필터 선택 -->
					<div
						v-if="state.selectedCategory === '필터'"
						id="scrolling-content"
						class="flex overflow-x-auto h-full"
					>
						<div
							v-for="custom in state.visualFilterList"
							:key="custom.id"
							class="w-1/4 cursor-pointer h-full px-4 flex-shrink-0"
							v-show="showCategory(custom.category)"
							@click="clickVideoCustom(custom)"
						>
							<img
								:src="custom.url"
								:class="[
									custom.id === state.selectedCustomId
										? 'border-4 border-sub-200'
										: '',
								]"
							/>
						</div>
					</div>
					<!-- 문구 선택 -->
					<div
						v-if="state.selectedCategory === '문구'"
						id="scrolling-content"
						class="flex-col overflow-x-auto h-full"
					>
						<div>
							<select
								v-model="state.textList.font"
								class="m-1 border-main-100 border-2 cursor-pointer font-bold py-0 text-sm form-select border-0 rounded-md text-gray-600 h-7.5 border-transparent focus:border-transparent focus:ring-0 appearance-none"
							>
								<option value="" disabled selected>글꼴 선택</option>
								<option
									v-for="font in state.fonts"
									:value="font.name"
									:key="font.id"
								>
									{{ font.name }}
								</option>
							</select>
							<select
								v-model="state.textList.location"
								class="m-1 border-main-100 border-2 cursor-pointer font-bold py-0 text-sm form-select rounded-md text-gray-600 h-7.5 border-transparent focus:border-transparent focus:ring-0 appearance-none"
							>
								<option value="" disabled selected>위치 선택</option>
								<option
									v-for="location in state.locations"
									:value="location.name"
									:key="location.id"
								>
									{{ location.name }}
								</option>
							</select>
							<input
								type="number"
								placeholder="글자 크기"
								v-model="state.textList.fontSize"
								class="m-1 border-main-100 border-2 cursor-pointer font-bold py-0 text-sm form-select rounded-md text-gray-600 h-7.5 border-transparent focus:border-transparent focus:ring-0 appearance-none"
							/>
						</div>
						<input
							type="text"
							placeholder="입력할 문구"
							v-model="state.textList.inputText"
							class="overflow-x-auto w-full h-14 border-main-100 border-2 font-bold text-sm rounded-md text-gray-600 border-transparent focus:border-transparent focus:ring-0 appearance-none"
						/>
					</div>
				</div>
			</div>
		</div>
	</base-modal>
</template>
<style></style>
<script>
import { ref, reactive } from 'vue';
import BaseModal from './base-modal.vue';
import stickerListJson from '@/assets/json-assets/stickerList.json';

export default {
	name: 'VideoCustomizeModal',

	components: {
		BaseModal,
	},

	setup(props, { emit }) {
		const baseModal = ref(null);

		const state = reactive({
			selectedCategory: '스티커',
			selectedCustom: null,

			categories: [
				{ id: 1, name: '스티커' },
				{ id: 2, name: '필터' },
				{ id: 3, name: '문구' },
			],

			fonts: [
				{ id: 1, name: 'Cantarell' },
				{ id: 2, name: 'Cantarell' },
			],

			locations: [
				{ id: 1, name: 'Top|Right' },
				{ id: 2, name: 'Top|Center' },
				{ id: 3, name: 'Top|Left' },
				{ id: 4, name: 'Center|Right' },
				{ id: 5, name: 'Center|Center' },
				{ id: 6, name: 'Center|Left' },
				{ id: 7, name: 'Bottom|Right' },
				{ id: 8, name: 'Bottom|Center' },
				{ id: 9, name: 'Bottom|Left' },
			],

			// 각 항목을 json 파일로 따로 저장하여 json파일 불러옴
			stickerList: stickerListJson,

			visualFilterList: [
				{
					id: 1,
					url: 'https://cdn.crowdpic.net/list-thumb/thumb_l_02F4A9A335F63872A1C75E9FAFE16241.png',
					command: 'videoflip method=vertical-flip',
				},
				{
					id: 2,
					url: 'https://cdn.crowdpic.net/list-thumb/thumb_l_02F4A9A335F63872A1C75E9FAFE16241.png',
					command: 'videoflip method=vertical-flip',
				},
			],

			textList: {
				inputText: '',
				location: '',
				font: '',
				fontSize: 25,
			},
		});

		const open = () => {
			baseModal.value.openModal();
		};
		const close = () => {
			baseModal.value.closeModal();
		};

		// 옵션 카테고리가 기본값인 '스티커'이거나 함수의 인자로 들어온 category와 같은 경우에만 true 리턴
		const showCategory = category => {
			return (
				state.selectedCategory == '스티커' || state.selectedCategory == category
			);
		};

		const clickVideoCustom = customObject => {
			console.log('선택된 항목 : ' + customObject);
			state.selectedCustom === customObject
				? (state.selectedCustom = null)
				: (state.selectedCustom = customObject);
		};

		// 카테고리에 따라 파티룸 내부 함수 호출(emit)
		const applyVideoCustom = () => {
			var selected = state.selectedCategory;
			console.log('apply 클릭 : ' + selected);
			if (selected === '스티커') {
				emit('stickerOverlay', state.selectedCustom);
			} else if (selected === '필터') {
				emit('visualFilter', state.selectedCustom);
			} else if (selected === '문구') {
				emit('textOverlay', {
					inputText: state.textList.inputText,
					valignment: state.textList.location
						? state.textList.location.split('|')[0].toLowerCase()
						: '',
					halignment: state.textList.location
						? state.textList.location.split('|')[1].toLowerCase()
						: '',
					font: state.textList.font,
					fontSize: state.textList.fontSize,
				});
			} else {
				cancelVideoCustom();
			}
		};

		// TODO: 비디오 커스텀 적용 취소 - bottombar의 filterOff 버튼과 연동
		const cancelVideoCustom = () => {
			close();
		};

		return {
			state,
			baseModal,
			open,
			close,
			showCategory,
			clickVideoCustom,
			applyVideoCustom,
			cancelVideoCustom,
		};
	},
};
</script>
