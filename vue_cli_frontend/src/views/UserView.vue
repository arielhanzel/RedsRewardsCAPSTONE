<template>
  <div class="user-sections">
    <!-- User Overview Section -->
    <div class="section">
      <h1>User Overview</h1>
      <table class="user-info-table">
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
      <table class="fitness-classes-table">
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
                <button type="submit">Register</button>
              </form>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Available Classes Section -->
    <div class="section">
      <h1>Available Classes</h1>
      <!-- Table to display available classes -->
    </div>

    <!-- Referred and Referee Accounts Section -->
    <div class="section">
      <h1>Referred and Referee Accounts</h1>
      <!-- Table to display referred and referee accounts -->
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
        .post("http://localhost:8000/user/registerclass", requestData, {
          headers: { Authorization: `Bearer ${userStore.token}` },
        })
        .then((response) => {
          console.log("Class registered:", response.data);
          // Handle successful registration, e.g., show a success message
        })
        .catch((error) => {
          console.error("Error registering for class:", error);
          // Handle errors, e.g., show an error message
        });
    },
  },
  mounted() {
    document.title = "Red's Rewards - User";
    this.fetchUserData();
    this.fetchFitnessClasses();
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

.user-info-table {
  width: 100%;
  border-collapse: collapse;
  text-align: left;
}

.user-info-table th,
.user-info-table td {
  padding: 8px;
  border: 1px solid #ddd;
}

.user-info-table th {
  background-color: #eee;
  color: #333;
}

.user-info-table tr:nth-child(even) {
  background-color: #f2f2f2;
}

.fitness-classes-table {
  width: 100%;
  border-collapse: collapse;
  text-align: left;
  margin-top: 20px;
}

.fitness-classes-table th,
.fitness-classes-table td {
  padding: 8px;
  border: 1px solid #ddd;
}

.fitness-classes-table th {
  background-color: #eee;
  color: #333;
}

.fitness-classes-table tr:nth-child(even) {
  background-color: #f2f2f2;
}

@media (max-width: 740px) {
  .user-sections {
    padding: 20px;
  }

  .section {
    padding: 15px;
  }

  .user-info-table th,
  .user-info-table td {
    padding: 6px;
  }
}
</style>
