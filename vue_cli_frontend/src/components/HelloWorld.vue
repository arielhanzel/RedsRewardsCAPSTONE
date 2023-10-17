<template>
  <div class="hello">
    <h1>Welcome to Red's Rewards!</h1>
    <p>
      Earn points to redeem exciting rewards!<br />
      Learn more about how to earn points
      <router-link to="/about">here</router-link>.
    </p>

    <div class="gamification">
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
          {{ useStore.pointsToGoal }} points until your next milestone!</span
        >
      </div>
    </div>
  </div>
</template>

<script setup>
import { useUserStore } from "@/store";
const useStore = useUserStore();
</script>

<style scoped>
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
</style>
