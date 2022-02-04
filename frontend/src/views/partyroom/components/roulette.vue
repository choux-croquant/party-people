<template>
    <div>
        <div class="roulette-outer">
            <div class="roulette-pin"></div>
            <div class="roulette" v-bind:style="this.state.rouletteStyle">
                <div class="item-wrapper">
                    <div class="item" :key="item.value" v-for="(item, index) in this.state.items"
                        v-bind:style="this.state.itemStyles[index]">{{ item.value }}</div>
                </div>
                <div class="line-wrapper">
                    <div class="line" :key="item.value" v-for="(item, index) in this.state.items"
                        v-bind:style="this.state.lineStyles[index]"></div>
                </div>
            </div>
        </div>
        <button class="play-btn" @click="playRoulette()" v-bind:disabled="this.state.buttonDisabled">Play</button>
        <ul>
            <li :key="curHistory" v-for="(curHistory) in this.state.history">{{curHistory}}</li>
        </ul>
    </div>
</template>

<script>
import {reactive, onBeforeMount, computed} from 'vue';
import {useStore} from "vuex";

export default {
    name: 'roulette',
    setup() {
        const store = useStore()
        const state = reactive({
            items: [
                {value : "하윤주"},
                {value : "선민기"},
                {value : "이정환"},
                {value : "최현규"},
                {value : "서민기"},
                {value : "박건우"},
            ],
            currentPin: 0,
            count: 0,
            history: [],
            buttonDisabled: false,
            itemStyles: [],
            lineStyles: [],
            segment: computed(() => 360 / state.items.length),
            offset: computed(() => state.segment / 2),
            angle: computed(() => -((state.currentPin * state.segment) + (state.count * 360 * 5))),
            rouletteStyle: computed(() => ({"transform" : "rotate(" + state.angle + "deg)"})),
            currentItem: computed(() => state.items[state.currentPin])
        })
        onBeforeMount(() => {
            state.items.forEach((el, idx) => {
                state.itemStyles.push({
                    "transform" : "rotate(" + (state.segment * idx) + "deg)",
                });
                state.lineStyles.push({
                    "transform" : "rotate(" + (state.segment * idx + state.offset) + "deg)",
                });
            });
        })

        const playRoulette = (info) => {
            state.buttonDisabled = true;
            state.count++;
            state.currentPin = Math.floor(Math.random() * state.items.length);
            // state.currentPin = info.winner
            // state.items = info.participants

            setTimeout(() => {
                state.buttonDisabled = false;
                state.history.push(state.currentItem.value);
            }, 5000);
        }

        return {store, state, playRoulette}
    }
}
</script>

<style>
.roulette-outer {
    position: relative;
    width: 500px;
    height: 500px;
    margin-left: auto;
    margin-right: auto;
    background: cadetblue;
}

.roulette-outer > .roulette-pin {
    position: absolute;
    top: 3%;
    left: 50%;
    width: 0;
    height: 0;
    border-style: solid;
    border-width: 25px 5px 0 5px;
    border-color: chartreuse transparent transparent transparent;
    margin-left: -5px;
    z-index: 1;
}

.roulette-outer > .roulette {
    position: absolute;
    top: 5%;
    bottom: 5%;
    left: 5%;
    right: 5%;
    border-radius: 50%;
    border: 2px solid black;
    transition: transform 5s ease-in-out;
}

.roulette-outer > .roulette > .item-wrapper > .item {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 0;
    right: 0;
    padding-top: 10%;
    text-align: center;
    display: flex;
    justify-content: center;
}

.roulette-outer > .roulette > .line-wrapper > .line {
    position: absolute;
    top: 0;
    bottom: 50%;
    left: 50%;
    width: 2px;
    margin-left: -1px;
    background: black;
    transform-origin: bottom;
}
</style>
