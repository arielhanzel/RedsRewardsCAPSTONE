<template>
  <div class="admin-sections">
    <div class="title-section"><h1>Admin Page</h1></div>
    <div class="section">
      <h1>User Lookup</h1>
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
            <tr v-for="user in users" :key="user.id">
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
      <h1>Attendance Management</h1>
      <p>View attendance records. <a href="#">Access Attendance</a></p>
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
    };
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
}
</style>
