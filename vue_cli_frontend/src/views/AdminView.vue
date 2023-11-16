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
              <td>{{ fitnessClass.id }}</td>
              <td>{{ fitnessClass.type }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <div class="section">
      <h1>Email Management</h1>
      <p>Placeholder for now <a href="#">Access Emails</a></p>
    </div>

    <div class="section">
      <h1>Task Editing</h1>
      <p>Placeholder for now <a href="#">Edit Tasks</a></p>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { useUserStore } from "@/store/index";

export default {
  data() {
    return {
      users: [],
      searchQuery: "",
      fitnessClasses: [],
      newClass: {
        type: "",
      },
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
  },
  methods: {
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
        .catch((error) => console.log(error));
    },
  },
  mounted() {
    const userStore = useUserStore();
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