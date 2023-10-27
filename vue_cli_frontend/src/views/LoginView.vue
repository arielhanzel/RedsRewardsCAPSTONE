<template>
  <div class="login">
    <h2>Login</h2>
    <div class="login-container">
      <form @submit.prevent="login" class="login-form">
        <input type="text" placeholder="username" v-model="username" required />
        <input
          type="password"
          placeholder="password"
          v-model="password"
          required
        />
        <button type="submit">Log In</button>
        <span>
          <br />
          <br />
          Don't have an account? Sign up
          <router-link to="/signup">here</router-link>.
        </span>
      </form>
    </div>
  </div>
</template>

<style>
.login {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 60vh;
}

.login h2 {
  font-size: 3rem;
  margin-bottom: 1rem;
  color: rgb(255, 0, 0);
  margin-bottom: -50px;
}

.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50vh;
}

.login-form {
  padding: 20px;
  border: 2px solid #ccc;
  border-radius: 10px;
  background-color: rgba(0, 0, 0, 0);
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
  width: 300px;
  text-align: center;
}

input[type="text"],
input[type="password"] {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  display: inline-block;
  border: 1px solid #ccc;
  border-radius: 4px;
  box-sizing: border-box;
}

button[type="submit"] {
  font-weight: bold;
  font-size: larger;
  height: 30px;
  width: 100%;
  color: white;
  border: none;
  background-color: rgb(255, 0, 0);
  margin-top: 10px;
  border-radius: 15px;
  cursor: pointer;
  transition: 0.4s;
}

button[type="submit"]:hover {
  color: red;
  background-color: white;
}
</style>

<script>
import axios from "axios";
import { useUserStore } from "@/store";

export default {
  data() {
    return {
      username: "",
      password: "",
    };
  },
  methods: {
    async login() {
      try {
        // Calling the backend
        const response = await axios.post("http://localhost:8000/auth/login", {
          username: this.username,
          password: this.password,
        });

        if (response.data.jwt) {
          // Assuming the server returns a token on successful login
          alert("Login successful!");
          // Optionally, you can store the token in localStorage or Vuex store, etc.
          // localStorage.setItem('token', response.data.token);

          const store = useUserStore();
          store.login(response.data.user.username);
          store.setToken(response.data.jwt);
        } else {
          alert("Invalid username or password");
        }
      } catch (error) {
        // Handle error scenarios here
        alert("Failed to login. Please try again later.");
        alert(error);
        console.error("Error logging in:", error);
      }
    },
  },
};
</script>
