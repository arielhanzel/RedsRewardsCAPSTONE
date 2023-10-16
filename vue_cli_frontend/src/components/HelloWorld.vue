<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <p>
      Earn points to redeem exciting rewards!<br />
      Learn more about how to earn points
      <router-link to="/about">here</router-link>.
    </p>

    <div class="gamification">
      <div class="points">
        <span>Points: {{ userPoints }}</span>
      </div>
      <div class="progress-bar">
        <div
          class="progress"
          :style="{ width: progressPercentage + '%' }"
        ></div>
      </div>
      <div>
        <span> {{ pointsToGoal }} points until your next milestone!</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "HelloWorld",
  props: {
    msg: String,
  },
  data() {
    return {
      userPoints: 220,
      totalPoints: 1000, // Set your desired total points for progress completion
    };
  },
  computed: {
    progressPercentage() {
      return (this.userPoints / this.totalPoints) * 100;
    },
    pointsToGoal() {
      return this.totalPoints - this.userPoints;
    },
  },
};
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
  border-radius: 5px;
  height: 20px;
  overflow: hidden;
}

.progress {
  height: 100%;
  background-color: #4caf50;
  transition: width 0.3s ease-in-out;
}
</style>
