<template>
  <div class="signup">
    <h2>Sign Up</h2>
    <div class="signup-container">
      <form @submit.prevent="signup" class="signup-form">
        <input type="text" placeholder="Username" v-model="username" required />
        <input type="email" placeholder="Email" v-model="email" required />
        <input
          type="password"
          placeholder="Password"
          v-model="password"
          required
        />
        <button type="submit">Sign Up</button>
        <span>
          <br />
          <br />
          Already have an account? Sign in
          <router-link to="/login">here</router-link>.
        </span>
      </form>
    </div>
  </div>
</template>

<style>
.signup {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 60vh;
}

.signup h2 {
  font-size: 3rem;
  margin-bottom: 1rem;
  color: rgb(255, 0, 0);
  margin-bottom: -50px;
}

.signup-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50vh;
}

.signup-form {
  padding: 20px;
  border: 2px solid #ccc;
  border-radius: 10px;
  background-color: rgba(0, 0, 0, 0);
  box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2);
  width: 300px;
  text-align: center;
}

input[type="email"],
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
import router from "../router";
import { useUserStore } from "@/store";

export default {
  data() {
    return {
      username: "",
      email: "",
      password: "",
    };
  },
  methods: {
    async signup() {
      const useStore = useUserStore();
      useStore.signup(this.username, this.email, this.password);
      if (useStore.check) {
        try {
          // Calling the backend
          const response = await axios.post(
            "http://localhost:8000/auth/register",
            {
              username: this.username,
              password: this.password,
              email: this.email,
            }
          );

          if (response.data) {
            // Assuming the server returns a token on successful login
            alert("Sign up successful! you have to log in to generate token");
            // Optionally, you can store the token in localStorage or Vuex store, etc.
            // localStorage.setItem('token', response.data.token);

            router.push("/login");
          } else {
            alert("Invalid username or password");
          }
        } catch (error) {
          // Handle error scenarios here
          alert("Failed to login. Please try again later.");
          alert(error);
          console.error("Error logging in:", error);
        }
      }
    },
  },
};
</script>
