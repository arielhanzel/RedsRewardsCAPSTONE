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
        <button
          @click="useStore.signup(username, email, password)"
          type="submit"
        >
          Sign Up
        </button>
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

<script setup>
import { useUserStore } from "@/store";
const useStore = useUserStore();
const username = "";
const email = "";
const password = "";
</script>
