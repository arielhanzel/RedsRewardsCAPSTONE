<template>
  <div class="login">
    <h2>Login</h2>
    <div class="login-container">
      <form @submit.prevent="login" class="login-form">
        <input type="text" placeholder="Username" v-model="username" required />
        <div class="password-container">
          <input
            :type="passwordType"
            placeholder="Password"
            v-model="password"
            required
          />
          <span @click="togglePasswordVisibility">
            <img
              v-if="passwordType === 'password'"
              src="@/assets/showPW.svg"
              alt="Show Password"
              class="toggle-icon"
            />
            <img
              v-else
              src="@/assets/hidePW.svg"
              alt="Hide Password"
              class="toggle-icon"
            />
          </span>
        </div>
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

.password-container {
  position: relative;
  width: 100%;
}

.toggle-icon {
  position: absolute;
  right: 10px;
  top: 50%;
  transform: translateY(-50%);
  cursor: pointer;
  width: 20px;
  height: 20px;
}
</style>

<script>
import { ref } from "vue";
import { useUserStore } from "@/store";

export default {
  data() {
    return {
      username: "",
      password: "",
      passwordType: ref("password"),
    };
  },
  methods: {
    togglePasswordVisibility() {
      this.passwordType =
        this.passwordType === "password" ? "text" : "password";
    },
    async login() {
      const userStore = useUserStore();
      try {
        await userStore.login(this.username, this.password);
      } catch (error) {
        alert("Failed to login. Please try again later.");
        console.error("Error logging in:", error);
      }
    },
  },
  mounted() {
    document.title = "Red's Rewards - Login";
  },
};
</script>
