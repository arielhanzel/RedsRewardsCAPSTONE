<template>
  <div class="user-sections">
    <!-- User Overview Section -->
    <div class="section">
      <h1>User Overview</h1>
      <table class="styled-table">
        <tbody>
          <tr>
            <th>User ID</th>
            <td>{{ userId }}</td>
          </tr>
          <tr>
            <th>Username</th>
            <td>{{ username }}</td>
          </tr>
          <tr>
            <th>Email</th>
            <td>{{ email }}</td>
          </tr>
          <tr>
            <th>Role</th>
            <td v-for="role in roles" :key="role.roleId">
              {{ role.authority }}
            </td>
          </tr>
          <tr>
            <th>Points</th>
            <td>{{ points }}</td>
          </tr>
          <tr>
            <th>Classes Registered</th>
            <td>
              <ul>
                <li v-for="classType in classTypes" :key="classType">
                  {{ classType }}
                </li>
              </ul>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Register/Unregister for Class Section -->
    <div class="section">
      <h1>Available Fitness Classes</h1>
      <div class="table-container">
        <table class="styled-table">
          <thead>
            <tr>
              <th>Class ID</th>
              <th>Type</th>
              <th>Register</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="fitnessClass in fitnessClasses"
              :key="fitnessClass.classId"
            >
              <td>{{ fitnessClass.classId }}</td>
              <td>{{ fitnessClass.type }}</td>
              <td>
                <form @submit.prevent="registerForClass(fitnessClass.type)">
                  <button
                    type="submit"
                    style="background-color: green; color: white"
                  >
                    Register
                  </button>
                </form>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Referrer Section -->
    <div class="section">
      <h1>My Referrer</h1>
      <div class="table-container">
        <table v-if="referrer" class="styled-table">
          <thead>
            <tr>
              <th>Referral ID</th>
              <th>Referrer Username</th>
              <th>Referee Username</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td>{{ referrer.referralId }}</td>
              <td>{{ referrer.referrerUsername }}</td>
              <td>{{ referrer.refereeUsername }}</td>
            </tr>
          </tbody>
        </table>
        <p v-else>No referrer information available.</p>
      </div>
    </div>

    <!-- Referees Section -->
    <div class="section">
      <h1>My Referees</h1>
      <div class="table-container">
        <table v-if="referees.length" class="styled-table">
          <thead>
            <tr>
              <th>Referral ID</th>
              <th>Referrer Username</th>
              <th>Referee Username</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="referee in referees" :key="referee.referralId">
              <td>{{ referee.referralId }}</td>
              <td>{{ referee.referrerUsername }}</td>
              <td>{{ referee.refereeUsername }}</td>
            </tr>
          </tbody>
        </table>
        <p v-else>No referee information available.</p>
      </div>
    </div>

    <div class="section">
      <h1>Unapproved Points</h1>
      <div class="table-container">
        <table v-if="unapprovedRewards.length > 0" class="styled-table">
          <thead>
            <tr>
              <th>Point ID</th>
              <th>Date</th>
              <th>Point Balance</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="reward in unapprovedRewards" :key="reward.pointId">
              <td>{{ reward.pointId }}</td>
              <td>{{ formatTimestamp(reward.timestamp) }}</td>
              <td>{{ reward.pointBalance }}</td>
            </tr>
          </tbody>
        </table>
        <p v-else>No unapproved points available.</p>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { useUserStore } from "@/store/index";

export default {
  data() {
    return {
      userId: "",
      username: "",
      email: "",
      roles: [],
      classTypes: [],
      fitnessClasses: [],
      registerUsername: "",
      registerClassType: "",
      referrer: null,
      referees: [],
      unapprovedRewards: [],
      redeemedRewards: [],
    };
  },
  computed: {
    points() {
      const userStore = useUserStore();
      return userStore.points;
    },
  },
  methods: {
    async fetchUserData() {
      const userStore = useUserStore();
      await userStore.viewTotalPoints();

      const userData = {
        username: userStore.user,
      };

      try {
        const response = await axios.post(
          "http://localhost:8000/user/user/view",
          userData,
          {
            headers: { Authorization: `Bearer ${userStore.token}` },
          }
        );

        const data = response.data;
        this.userId = data.userId;
        this.username = data.username;
        this.email = data.email;
        this.roles = data.roles;
        this.classTypes = data.classTypes;
      } catch (error) {
        console.error("Error fetching user data:", error);
      }
    },
    fetchFitnessClasses() {
      const userStore = useUserStore();
      axios
        .post(
          "http://localhost:8000/user/fitnessclass",
          {},
          {
            headers: { Authorization: `Bearer ${userStore.token}` },
          }
        )
        .then((response) => {
          this.fitnessClasses = response.data;
        })
        .catch((error) => {
          console.error("Error fetching fitness classes:", error);
        });
    },
    registerForClass(classType) {
      const userStore = useUserStore();
      const requestData = {
        username: userStore.user,
        classType: classType,
      };

      axios
        .post("http://localhost:8000/admin/registerclass", requestData, {
          headers: { Authorization: `Bearer ${userStore.token}` },
        })
        .then((response) => {
          this.registeredClasses = response.data;
          alert("Registered Successfully");
        })
        .catch((error) => {
          console.error(error);
          alert("An error occurred or Already registered!");
        });
    },
    fetchReferrer() {
      const userStore = useUserStore();
      const requestData = {
        username: userStore.user,
      };

      axios
        .post("http://localhost:8000/user/referrer/view", requestData, {
          headers: { Authorization: `Bearer ${userStore.token}` },
        })
        .then((response) => {
          this.referrer = response.data;
        })
        .catch((error) => {
          console.error("Error fetching referrer data:", error);
        });
    },

    fetchReferees() {
      const userStore = useUserStore();
      const requestData = {
        username: userStore.user,
      };

      axios
        .post("http://localhost:8000/user/referree/view", requestData, {
          headers: { Authorization: `Bearer ${userStore.token}` },
        })
        .then((response) => {
          this.referees = response.data;
        })
        .catch((error) => {
          console.error("Error fetching referees data:", error);
        });
    },
    fetchUnapprovedRewards() {
      const userStore = useUserStore();
      const requestData = {
        username: userStore.user,
      };

      axios
        .post("http://localhost:8000/user/unapprovedreward/view", requestData, {
          headers: { Authorization: `Bearer ${userStore.token}` },
        })
        .then((response) => {
          this.unapprovedRewards = response.data;
        })
        .catch((error) => {
          console.error("Error fetching unapproved rewards:", error);
        });
    },
    formatTimestamp(timestamp) {
      const date = new Date(timestamp);
      return date.toLocaleDateString() + " " + date.toLocaleTimeString();
    },
  },
  mounted() {
    document.title = "Red's Rewards - User";
    this.fetchUserData();
    this.fetchFitnessClasses();
    this.fetchReferrer();
    this.fetchReferees();
    this.fetchUnapprovedRewards();
  },
};
</script>

<style scoped>
.user-sections {
  padding: 40px;
  display: flex;
  flex-direction: column;
  align-items: center;
  min-height: 100vh;
}

.section {
  padding: 20px;
  margin-bottom: 40px;
  max-width: 800px;
  width: 100%;
  background: #fff;
  border-radius: 12px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  color: #333;
}

h1 {
  color: #880000;
  margin-bottom: 20px;
}

.styled-table {
  width: 100%;
  border-collapse: collapse;
  text-align: left;
  margin-top: 20px;
}

.styled-table th,
.styled-table td {
  padding: 8px;
  border: 1px solid #ddd;
}

.styled-table th {
  background-color: #eee;
  color: #333;
}

.styled-table tr:nth-child(even) {
  background-color: #f2f2f2;
}

.table-container {
  max-height: 230px;
  overflow-y: auto;
  margin-bottom: 20px;
}

.table-container::-webkit-scrollbar {
  width: 8px;
}

.table-container::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 10px;
}

.table-container::-webkit-scrollbar-thumb {
  background: #888;
  border-radius: 10px;
}

.table-container::-webkit-scrollbar-thumb:hover {
  background: #555;
}

thead {
  position: sticky;
  top: 0;
  z-index: 10;
}

@media (max-width: 740px) {
  .user-sections {
    padding: 20px;
  }

  .section {
    padding: 15px;
  }

  .styled-table th,
  .styled-table td {
    padding: 6px;
  }

  .table-container {
    max-height: 150px;
  }
}
</style>
