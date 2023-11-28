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
      <h1 v-if="userStore.loggedIn">Welcome back, {{ storedUsername }}!</h1>
      <h1 v-else>Welcome to Red's Rewards!</h1>
      <div v-if="userStore.role === 'ADMIN'" class="admin-button">
        <router-link to="/admin" class="admin-link"
          >You are logged in as an admin</router-link
        >
      </div>
      <p>
        Earn points to redeem exciting rewards!<br />
        Learn more about how to earn points
        <router-link to="/about">here</router-link>.
      </p>

      <div
        v-if="userStore.loggedIn && userPoints.length > 0"
        class="user-points-section"
      >
        <h2>Your Reward Points History</h2>
        <div class="table-container">
          <table>
            <thead>
              <tr>
                <th>Date</th>
                <th>Points Earned</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="point in userPoints" :key="point.pointId">
                <td>{{ new Date(point.timestamp).toLocaleDateString() }}</td>
                <td>{{ point.pointBalance }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useUserStore } from "@/store";
import axios from "axios";

const userStore = useUserStore();

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

const storedUsername = localStorage.getItem("username");

const userPoints = ref([]);

onMounted(async () => {
  if (userStore.loggedIn) {
    await fetchUserPoints();
  }
});

const fetchUserPoints = async () => {
  try {
    const username = userStore.user;
    const token = userStore.token;
    console.log("Token:", token);
    const url = `http://localhost:8000/user/rewardpoints/by-username/${username}`;
    const response = await axios.get(url, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    userPoints.value = response.data.sort((a, b) => {
      return new Date(b.timestamp) - new Date(a.timestamp);
    });
  } catch (error) {
    console.error("Failed to fetch user points:", error);
    alert("Failed to fetch points");
  }
};
</script>

<style scoped>
.hello {
  position: relative;
  z-index: 1;
  background-color: transparent;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-end;
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

.admin-link {
  display: inline-block;
  padding: 10px 20px;
  border-color: #880000;
  font-weight: bold;
  font-size: larger;
  height: 30px;
  color: white;
  background-color: #df0000;
  margin-top: 10px;
  border-radius: 15px;
  cursor: pointer;
  text-decoration: none;
  transition: 0.3s;
}

.admin-link:hover {
  color: #df0000;
  background-color: white;
}

.admin-link:active {
  transform: scale(1.1);
}

.user-points-section {
  position: relative;
  border-radius: 12px;
  padding: 40px;
  margin-top: 20px;
  margin-bottom: 40px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: all 0.3s;
  max-width: 400px;
  width: 100%;
  background: linear-gradient(135deg, #f5f7fa, #eef2f7);
  border: 1px solid #e1e8ed;
}

.user-points-section:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.12);
}

.user-points-section h2 {
  color: #880000;
  font-size: 28px;
  margin-bottom: 24px;
}

.table-container {
  max-height: 185px;
  overflow-y: auto;
  margin-bottom: 20px;
}

table {
  width: 100%;
  border-collapse: collapse;
}

table,
th,
td {
  border: 1px solid #ddd;
}

th,
td {
  text-align: left;
  padding: 8px;
}

th {
  background-color: #880000;
  color: white;
}

td {
  color: black;
}

thead {
  position: sticky;
  top: 0;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}
</style>
