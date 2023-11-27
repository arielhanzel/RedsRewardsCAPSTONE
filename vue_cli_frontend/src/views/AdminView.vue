<template>
  <div class="admin-sections">
    <div class="title-section"><h1>Admin Page</h1></div>
    <div class="section">
      <h1>User Lookup</h1>
      <input
        type="text"
        v-model="searchQuery"
        placeholder="Search by name..."
        class="search-input"
      />
      <div class="table-container">
        <table>
          <thead>
            <tr>
              <th>User ID</th>
              <th>Name</th>
              <th>Email</th>
              <th>Role</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in filteredUsers" :key="user.id">
              <td>{{ user.userId }}</td>
              <td>{{ user.username }}</td>
              <td>{{ user.email }}</td>
              <td>{{ user.roles[0].authority }}</td>
            </tr>
          </tbody>
        </table>
      </div>

      <form @submit.prevent="deleteUser" class="add-class-form">
        <input
          type="text"
          v-model="deleteUsername"
          placeholder="Username to delete"
        />
        <button type="submit" :class="{ 'red-button': deleteUsername }">
          Delete User
        </button>
      </form>
    </div>

    <div class="section">
      <h1>Fitness Class Management</h1>

      <form @submit.prevent="addFitnessClass" class="add-class-form">
        <input
          type="text"
          v-model="newClass.type"
          placeholder="Class Type (e.g., Yoga)"
        />
        <button type="submit" :class="{ 'red-button': newClass.type }">
          Add New Class
        </button>
      </form>

      <div class="table-container">
        <table>
          <thead>
            <tr>
              <th>Class ID</th>
              <th>Type</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="fitnessClass in fitnessClasses" :key="fitnessClass.id">
              <td>{{ fitnessClass.classId }}</td>
              <td>{{ fitnessClass.type }}</td>
            </tr>
          </tbody>
        </table>
      </div>

      <form @submit.prevent="deleteFitnessClass" class="add-class-form">
        <input
          type="text"
          v-model="newClass.type"
          placeholder="Class Type (e.g., Yoga)"
        />
        <button type="submit" :class="{ 'red-button': newClass.type }">
          Delete Class
        </button>
      </form>

      <form @submit.prevent="registerFitnessClass" class="add-class-form">
        <input type="text" v-model="username" placeholder="Customer name" />
        <input
          type="text"
          v-model="classType"
          placeholder="Class Type (e.g., Yoga)"
        />
        <button type="submit" :class="{ 'red-button': newClass.type }">
          register FitnessClass for User
        </button>
      </form>

      <form @submit.prevent="unregisterFitnessClass" class="add-class-form">
        <input type="text" v-model="username" placeholder="Customer name" />
        <input
          type="text"
          v-model="classType"
          placeholder="Class Type (e.g., Yoga)"
        />
        <button type="submit" :class="{ 'red-button': classType }">
          Unregister FitnessClass for User
        </button>
      </form>

      <div v-if="registeredClasses">
        <h1>Class registered by {{ registeredClasses.username }}</h1>
        <table>
          <thead>
            <tr>
              <th>#</th>
              <th>Class Type</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="(classType, index) in registeredClasses.classTypes"
              :key="index"
            >
              <td>{{ index + 1 }}</td>
              <td>{{ classType }}</td>
            </tr>
          </tbody>
        </table>
      </div>

      <h1>User's Registered Class Lookup</h1>
      <input
        type="text"
        v-model="searchQuery"
        placeholder="Search by name..."
        class="search-input"
      />
      <div v-if="searchQuery">
        <h1>Search Results</h1>
        <table>
          <thead>
            <tr>
              <th>User ID</th>
              <th>Username</th>
              <th>Class Types</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in filteredUsers" :key="user.id">
              <td>{{ user.userId }}</td>
              <td>{{ user.username }}</td>
              <td>
                <ul>
                  <li
                    v-for="(classType, index) in user.classTypes"
                    :key="index"
                  >
                    {{ classType }}
                  </li>
                </ul>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="section">
      <h1>Reward Points Overview</h1>
      <div class="table-container">
        <table>
          <thead>
            <tr>
              <th>Name</th>
              <th>Date/Time</th>
              <th>Points Earned</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="user in rewardPoints" :key="user.userId">
              <td>{{ user.username }}</td>
              <td>{{ user.timestamp }}</td>
              <td>{{ user.pointsEarned }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="section">
      <h1>Unapproved Rewards</h1>
      <input
        type="text"
        v-model="unapprovedRewardsSearchQuery"
        placeholder="Search by name..."
        class="search-input"
      />
      <div class="table-container">
        <table>
          <thead>
            <tr>
              <th>Point ID</th>
              <th>Username</th>
              <th>Points</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="reward in filteredUnapprovedRewards"
              :key="reward.pointID"
            >
              <td>{{ reward.pointId }}</td>
              <td>{{ reward.username }}</td>
              <td>{{ reward.pointBalance }}</td>
              <td>
                <button
                  @click="approveReward(reward)"
                  class="unapproved-rewards-button"
                >
                  Approve
                </button>
              </td>
            </tr>
          </tbody>
        </table>
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
      username: null,
      role: null,
      users: [],
      searchQuery: "",
      fitnessClasses: [],
      newClass: {
        type: "",
      },
      rewardPoints: [],
      registeredClasses: null,
      unapprovedRewards: [],
      unapprovedRewardsSearchQuery: "",
      deleteUsername: "",
    };
  },
  computed: {
    filteredUsers() {
      if (this.searchQuery) {
        return this.users.filter((user) =>
          user.username.toLowerCase().includes(this.searchQuery.toLowerCase())
        );
      }
      return this.users;
    },
    filteredUnapprovedRewards() {
      if (this.unapprovedRewardsSearchQuery) {
        return this.unapprovedRewards.filter((reward) =>
          reward.username
            .toLowerCase()
            .includes(this.unapprovedRewardsSearchQuery.toLowerCase())
        );
      }
      return this.unapprovedRewards;
    },
  },
  methods: {
    fetchUser() {
      const userStore = useUserStore();
      axios
        .post(
          "http://localhost:8000/admin/user/allview",
          {
            username: userStore.user,
          },
          {
            headers: {
              Authorization: `Bearer ${userStore.token}`,
            },
          }
        )
        .then((response) => {
          this.users = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    deleteUser() {
      if (this.deleteUsername.trim().toLowerCase() === "admin") {
        alert("Cannot delete the admin user.");
        return;
      }

      const userStore = useUserStore();
      const userData = {
        username: this.deleteUsername,
      };

      axios
        .post("http://localhost:8000/admin/delete/user", userData, {
          headers: { Authorization: `Bearer ${userStore.token}` },
        })
        .then(() => {
          this.users = this.users.filter(
            (user) => user.username !== this.deleteUsername
          );
          alert("User deleted successfully.");
          this.deleteUsername = ""; // Reset the input field
        })
        .catch((error) => {
          console.error(error);
          alert("An error occurred: " + error.response.data.message);
        });
    },
    fetchFitnessClasses() {
      const userStore = useUserStore();
      axios
        .post("http://localhost:8000/admin/fitnessclass", null, {
          headers: { Authorization: `Bearer ${userStore.token}` },
        })
        .then((response) => {
          this.fitnessClasses = response.data;
        })
        .catch((error) => console.log(error));
    },
    addFitnessClass() {
      const userStore = useUserStore();
      if (this.newClass.type.trim() === "") {
        alert("Please enter a class type.");
        return;
      }
      axios
        .post("http://localhost:8000/admin/fitnessclass/add", this.newClass, {
          headers: { Authorization: `Bearer ${userStore.token}` },
        })
        .then((response) => {
          this.fitnessClasses.push(response.data);
          this.newClass.type = "";
        })
        .catch((error) => {
          console.log(error);
          alert("Cannot Add Fitness Class!");
        });
    },

    deleteFitnessClass() {
      const userStore = useUserStore();
      if (this.newClass.type.trim() === "") {
        alert("Please enter a class type.");
        return;
      }

      axios
        .post(
          "http://localhost:8000/admin/fitnessclass/delete",
          this.newClass,
          {
            headers: { Authorization: `Bearer ${userStore.token}` },
          }
        )
        .then((response) => {
          // Remove the deleted class from the fitnessClasses array
          this.fitnessClasses = this.fitnessClasses.filter(
            (fitnessClass) => fitnessClass.type !== this.newClass.type
          );
          this.newClass.type = ""; // Reset the input field
          alert(response.data);
        })
        .catch((error) => {
          console.error(error);
          alert("An error occurred during class deletion");
        });
    },

    registerFitnessClass() {
      const userStore = useUserStore();
      const requestData = {
        username: this.username,
        classType: this.classType,
      };

      if (this.username.trim() === "" && this.classType.trim() === "") {
        alert("Please enter both username and class type.");
        return;
      }

      axios
        .post("http://localhost:8000/admin/registerclass", requestData, {
          headers: { Authorization: `Bearer ${userStore.token}` },
        })
        .then((response) => {
          this.registeredClasses = response.data;
          this.fetchUser();
          alert("Registered Successfully!");
        })
        .catch((error) => {
          console.error(error);
          alert("An error occurred, cannot registered class!");
        });
    },

    unregisterFitnessClass() {
      const userStore = useUserStore();
      const requestData = {
        username: this.username,
        classType: this.classType,
      };

      if (this.username.trim() === "" || this.classType.trim() === "") {
        alert("Please enter both username and class type.");
        return;
      }

      axios
        .post("http://localhost:8000/admin/unregisterclass", requestData, {
          headers: { Authorization: `Bearer ${userStore.token}` },
        })
        .then((response) => {
          alert(response.data + "Unregistered Successfully!");
          this.fetchUser();
          // Optional: Code to update the UI accordingly
        })
        .catch((error) => {
          console.error(error);
          alert("An error occurred during class unregistration");
        });
    },

    fetchRewardPoints() {
      const userStore = useUserStore();
      axios
        .post("http://localhost:8000/admin/rewardpoint/allview", null, {
          headers: { Authorization: `Bearer ${userStore.token}` },
        })
        .then((response) => {
          this.rewardPoints = response.data.map((point) => {
            return {
              username: point.username,
              timestamp: new Date(point.timestamp).toLocaleString(),
              pointsEarned: point.pointBalance,
            };
          });
        })
        .catch((error) => console.log(error));
    },
    fetchUnapprovedRewards() {
      const userStore = useUserStore();
      axios
        .post("http://localhost:8000/admin/unapprovedreward/allview", null, {
          headers: { Authorization: `Bearer ${userStore.token}` },
        })
        .then((response) => {
          this.unapprovedRewards = response.data;
        })
        .catch((error) => console.error(error));
    },

    approveReward(reward) {
      console.log("Approving reward:", reward);
      const userStore = useUserStore();
      axios
        .post(
          "http://localhost:8000/admin/unapprovedreward/approve",
          {
            pointId: reward.pointId,
            username: reward.username,
            pointBalance: reward.pointBalance,
          },
          {
            headers: {
              Authorization: `Bearer ${userStore.token}`,
              "Content-Type": "application/json",
            },
          }
        )
        .then(() => {
          this.unapprovedRewards = this.unapprovedRewards.filter(
            (r) => r.pointId !== reward.pointId
          );
        })
        .catch((error) => console.error(error));
    },
  },
  mounted() {
    const userStore = useUserStore();
    this.username = userStore.user;
    this.role = userStore.role;

    document.title = "Red's Rewards - Admin";
    axios
      .post(
        "http://localhost:8000/admin/user/allview",
        {
          username: userStore.user,
        },
        {
          headers: {
            Authorization: `Bearer ${userStore.token}`,
          },
        }
      )
      .then((response) => {
        this.users = response.data;
      })
      .catch((error) => {
        console.log(error);
      });
    this.fetchFitnessClasses();
    this.fetchRewardPoints();
    this.fetchUnapprovedRewards();
  },
};
</script>
<style scoped>
body,
html {
  height: 100%;
  margin: 0;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto,
    "Helvetica Neue", Arial, sans-serif;
  color: #555;
}

body {
  overflow-x: hidden;
}

.admin-sections {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  padding: 40px;
}

.section {
  position: relative;
  border-radius: 12px;
  padding: 40px;
  margin-bottom: 40px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: all 0.3s;
  max-width: 800px;
  width: 100%;
  background: linear-gradient(135deg, #f5f7fa, #eef2f7);
  border: 1px solid #e1e8ed;
}

.title-section {
  position: relative;
  border-radius: 12px;
  padding: 0 40px 0 40px;
  margin-bottom: 40px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
  transition: all 0.3s;
  max-width: 800px;
  width: 100%;
  background: linear-gradient(135deg, #f5f7fa, #eef2f7);
  border: 1px solid #e1e8ed;
  text-align: center;
}

.section:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.12);
}

h1 {
  color: #880000;
  font-size: 28px;
  margin-bottom: 24px;
}

p,
table {
  font-size: 18px;
  line-height: 1.6;
  margin-bottom: 24px;
  color: #7f8c8d;
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

thead {
  position: sticky;
  top: 0;
}

tr:nth-child(even) {
  background-color: #f2f2f2;
}

.table-container {
  max-height: 185px;
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

.search-input {
  width: 100%;
  padding: 8px 15px;
  margin: 20px 0;
  box-sizing: border-box;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 16px;
  background-color: white;
  transition: border-color 0.3s;
}

.search-input:focus {
  outline: none;
  border-color: #880000;
}

.add-class-form {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.add-class-form input {
  margin-right: 10px;
}

button {
  padding: 5px 10px;
  font-size: 14px;
  border: 1px solid #ddd;
  border-radius: 15px;
  background-color: #313131;
  transition: background-color 0.3s, border-color 0.3s;
  cursor: pointer;
}

button.red-button {
  border-color: #880000;
  background-color: #880000;
  color: rgb(255, 255, 255);
}

button:disabled {
  background-color: #313131;
  border-color: #bbb;
  color: #aaa;
  cursor: not-allowed;
}
.section .unapproved-rewards-button {
  padding: 4px 8px;
  font-size: 12px;
  border: 1px solid #ddd;
  border-radius: 15px;
  background-color: #313131;
  color: white;
  cursor: pointer;
}

.section .unapproved-rewards-button:hover {
  background-color: #880000;
}

@media (max-width: 740px) {
  .admin-sections {
    padding: 20px;
    margin-left: 15px;
    margin-right: 15px;
  }

  .section {
    padding: 30px;
  }

  h1 {
    font-size: 24px;
  }

  p,
  table {
    font-size: 16px;
  }

  .table-container {
    max-height: 130px;
  }

  .search-input {
    font-size: 14px;
  }
}
</style>
