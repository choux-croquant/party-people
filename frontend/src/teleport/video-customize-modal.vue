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
					<div id="scrolling-content" class="flex overflow-x-auto h-full">
						<div
							v-for="custom in state.videoCustoms"
							:key="custom.id"
							class="w-1/4 cursor-pointer h-full px-4 flex-shrink-0"
							v-show="showCategory(custom.category)"
							@click="clickVideoCustom(custom.id)"
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
				</div>
			</div>
		</div>
	</base-modal>
</template>
<style></style>
<script>
import { ref, reactive } from 'vue';
import BaseModal from './base-modal.vue';

export default {
	name: 'VideoCustomizeModal',

	components: {
		BaseModal,
	},

	setup() {
		const baseModal = ref(null);

		const state = reactive({
			selectedCategory: 'All',
			selectedCustomId: null,

			categories: [
				{ id: 1, name: 'All' },
				{ id: 2, name: 'Birthday' },
				{ id: 3, name: 'Christmas' },
				{ id: 4, name: 'Halloween' },
			],

			videoCustoms: [
				{
					id: 1,
					category: 'Halloween',
					url: 'https://images.unsplash.com/photo-1508361001413-7a9dca21d08a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8aGFsbG93ZWVufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=200&q=20',
				},
				{
					id: 2,
					category: 'Halloween',
					url: 'https://images.unsplash.com/photo-1508361001413-7a9dca21d08a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8aGFsbG93ZWVufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=200&q=20',
				},
				{
					id: 3,
					category: 'Halloween',
					url: 'https://images.unsplash.com/photo-1508361001413-7a9dca21d08a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8aGFsbG93ZWVufGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=200&q=20',
				},
				{
					id: 4,
					category: 'Christmas',
					url: 'https://images.unsplash.com/photo-1451772741724-d20990422508?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
				},
				{
					id: 5,
					category: 'Christmas',
					url: 'https://images.unsplash.com/photo-1451772741724-d20990422508?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
				},
				{
					id: 6,
					category: 'Christmas',
					url: 'https://images.unsplash.com/photo-1451772741724-d20990422508?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80',
				},
				{
					id: 7,
					category: 'Birthday',
					url: 'https://images.unsplash.com/photo-1530103862676-de8c9debad1d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2370&q=80',
				},
				{
					id: 8,
					category: 'Birthday',
					url: 'https://images.unsplash.com/photo-1530103862676-de8c9debad1d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2370&q=80',
				},
				{
					id: 9,
					category: 'Birthday',
					url: 'https://images.unsplash.com/photo-1530103862676-de8c9debad1d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2370&q=80',
				},
			],
		});

		const open = () => {
			baseModal.value.openModal();
		};
		const close = () => {
			baseModal.value.closeModal();
		};

		// 옵션 카테고리가 'All'이거나 함수의 인자로 들어온 category와 같은 경우에만 true 리턴
		const showCategory = category => {
			return (
				state.selectedCategory == 'All' || state.selectedCategory == category
			);
		};

		const clickVideoCustom = id => {
			state.selectedCustomId === id
				? (state.selectedCustomId = null)
				: (state.selectedCustomId = id);
		};

		// TODO: 비디오 커스텀 적용
		const applyVideoCustom = () => {};
		// TODO: 비디오 커스텀 적용 취소
		const cancelVideoCustom = () => {};

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
