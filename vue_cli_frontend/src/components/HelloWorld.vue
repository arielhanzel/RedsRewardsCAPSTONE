<template>
  <div>
    <div id="video-container">
      <video
        autoplay
        muted
        playsinline
        id="bg-video"
        ref="bgVideo"
        @ended="onVideoEnded"
      >
        <source :src="videos[currentVideo]" type="video/mp4" />
      </video>
    </div>
    <div class="hello">
      <h1 v-if="useStore.token && useStore.token != 'null'">
        Welcome back, {{ useStore.user }}!
      </h1>
      <h1 v-else>Welcome to Red's Rewards!</h1>
      <p>
        Earn points to redeem exciting rewards!<br />
        Learn more about how to earn points
        <router-link to="/about">here</router-link>.
      </p>

      <div
        v-if="useStore.token && useStore.token != 'null'"
        class="gamification"
      >
        <div class="points">
          <span>Points: {{ useStore.points }}</span>
        </div>
        <div class="progress-bar">
          <div
            class="progress"
            :style="{ width: useStore.progressPercentage + '%' }"
          ></div>
        </div>
        <div>
          <span>
            {{ useStore.pointsToGoal }} points until your next reward
            unlock!</span
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { onMounted } from "vue";
import { useUserStore } from "@/store";

const useStore = useUserStore();

onMounted(() => {
  useStore.initializeFromLocalStorage();
});
const bgVideo = ref(null);
const currentVideo = ref(0);
const videos = [
  require("@/assets/welcome-vid1.mp4"),
  require("@/assets/welcome-vid2.mp4"),
  require("@/assets/welcome-vid3.mp4"),
];

const onVideoEnded = () => {
  currentVideo.value = (currentVideo.value + 1) % videos.length;
  bgVideo.value.load();
};
</script>

<style scoped>
.hello {
  position: relative;
  z-index: 1;
  background-color: transparent;
}

h3 {
  margin: 40px 0 0;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: rgb(255, 0, 0);
}
.gamification {
  margin-top: 20px;
  text-align: center;
}

.points {
  font-size: 18px;
  font-weight: bold;
}

.progress-bar {
  width: 80%;
  background-color: #eee;
  margin: 10px auto;
  border-radius: 10px;
  height: 20px;
  overflow: hidden;
}

.progress {
  height: 100%;
  background-color: #4caf50;
  transition: width 0.3s ease-in-out;
}

#video-container {
  position: fixed;
  top: 0;
  left: 0;
  overflow: hidden;
  z-index: -1;
}

#bg-video {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>
