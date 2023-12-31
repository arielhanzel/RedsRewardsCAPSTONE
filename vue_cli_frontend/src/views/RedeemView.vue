<template>
  <div class="rewards-page">
    <div class="rewards-header">
      <h1>
        You Have <span class="point-value">{{ userStore.points }}</span> points!
      </h1>
    </div>
    <!-- Add points for testing -->
    <button v-if="userStore.role === 'ADMIN'" @click="userStore.addPoints(100)">
      Add Points
    </button>

    <div class="rewards-list">
      <div class="reward-item" v-for="(reward, index) in rewards" :key="index">
        <div class="reward-content">
          <img :src="reward.image" alt="Reward Image" class="reward-image" />
          <div class="reward-description">
            <h2>{{ reward.name }}</h2>
            <p>{{ reward.description }}</p>
            <p>
              <strong>{{ reward.points }}</strong> points
            </p>
            <p>Redeemed: {{ reward.count }}</p>
            <button
              @click="userStore.redeemReward(reward)"
              :class="{ 'disabled-button': userStore.points < reward.points }"
              :disabled="userStore.points < reward.points"
            >
              Redeem
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useUserStore } from "@/store";
import { ref, onMounted } from "vue";

export default {
  setup() {
    const userStore = useUserStore();

    const rewards = ref([
      {
        name: "Red's T-shirt",
        points: 200,
        description: "Get a free T-shirt!",
        count: 0,
        image:
          "https://since99vintage.com/cdn/shop/products/image_258328be-b4fa-4fe8-9a90-93edf98cb604_530x@2x.jpg?v=1626282065",
      },
      {
        name: "Free Drink/Snack",
        points: 400,
        description: "Get a free drink or snack of your choice!",
        count: 0,
        image:
          "https://i.pinimg.com/736x/d2/55/de/d255dee2aff589cf202838f91c4cdb07.jpg",
      },
      {
        name: "Free Personal Training",
        points: 750,
        description: "Get a free 30-minute personal training session!",
        count: 0,
        image:
          "https://img.freepik.com/premium-vector/personal-training-fitness-male-coach-bodybuilder-cartoon-training-workout-vector-concept_53562-15548.jpg",
      },
      {
        name: "Membership Fee Waived for One Month",
        points: 1000,
        description: "Enjoy paying no membership fees for one month!",
        count: 0,
        image:
          "https://thumbs.dreamstime.com/b/vector-illstration-no-cash-icon-flat-design-vector-illstration-no-cash-icon-flat-design-isolated-170514654.jpg",
      },
      // Add more reward items here
    ]);

    // Define the initialization logic
    const initializeData = async () => {
      await userStore.viewRedeemCount();
      userStore.viewTotalPoints();
      rewards.value[0].count = userStore.redeemed1;
      rewards.value[1].count = userStore.redeemed2;
      rewards.value[2].count = userStore.redeemed3;
      rewards.value[3].count = userStore.redeemed4;
    };

    // Handle mounting logic using onMounted
    onMounted(() => {
      initializeData();
      document.title = "Red's Rewards - Redeem";
    });

    // Expose variables and methods to the template
    return { userStore, rewards, initializeData };
  },
};
</script>

<style scoped>
.rewards-page {
  padding: 20px;
}

.rewards-header {
  margin-top: -60px;
  font-size: 1.5em;
  display: flex;
  justify-content: center;
  text-align: center;
  margin-bottom: 20px;
}

.point-value {
  color: #df0000;
  margin-top: -10px;
  font-family: sans-serif;
  font-size: 2em;
}

.rewards-list {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding: 10px;
}

.reward-image {
  width: 40%;
  max-width: 150px;
  max-height: 150px;
  margin-left: 5%;
  margin-right: 5%;
  border-radius: 20px;
  border-color: #880000;
  border-style: inset;
  transition: 0.3s;
}

.reward-content {
  display: flex;
  align-items: center;
}

.reward-item {
  background: #f5f7fa;
  border: 1px solid #e1e8ed;
  border-radius: 12px;
  padding: 20px;
  width: 95%;
  text-align: center;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: 0.3s;
  margin-top: 20px;
  max-width: 500px;
}

.reward-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.12);
}

.reward-item:hover .reward-image {
  transform: scale(1.1);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.12);
}

.reward-item h2 {
  color: #880000;
  font-size: 24px;
  margin: 10px 0;
}

.reward-item p {
  font-size: 20px;
  line-height: 1.6;
  margin-bottom: 15px;
  color: #7f8c8d;
}

.reward-description {
  flex: 1;
}

button {
  border-color: #880000;
  font-weight: bold;
  font-size: larger;
  height: 30px;
  color: white;
  background-color: #df0000;
  margin-top: 10px;
  border-radius: 15px;
  cursor: pointer;
  transition: 0.3s;
}

button:disabled:hover {
  color: #666;
  background-color: #ccc;
}

button:hover {
  color: #df0000;
  background-color: white;
}

button:active {
  transform: scale(1.1);
}

.disabled-button {
  background-color: #ccc;
  color: #666;
  cursor: not-allowed;
}
</style>
