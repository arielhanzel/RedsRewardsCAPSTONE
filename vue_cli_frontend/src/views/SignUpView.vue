<template>
  <div class="signup">
    <h2>Sign Up</h2>
    <div class="signup-container">
      <form @submit.prevent="signup" class="signup-form">
        <input type="text" placeholder="Username" v-model="username" required />
        <input type="email" placeholder="Email" v-model="email" required />
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
          <ul class="password-criteria">
            <li :class="{ valid: isMinLength }">At least 8 characters</li>
            <li :class="{ valid: hasUpperCase }">
              At least one uppercase letter
            </li>
            <li :class="{ valid: hasSpecialChar }">
              At least one special character
            </li>
          </ul>
        </div>
        <input type="text" placeholder="Referrer name" v-model="referrer" />
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
  margin-bottom: 40px;
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

.password-container {
  position: relative;
  width: 100%;
}

.toggle-icon {
  position: absolute;
  right: 10px;
  top: 28px;
  cursor: pointer;
  width: 20px;
  height: 20px;
}

.password-criteria {
  list-style-type: none;
  padding: 0;
  text-align: left;
}

.password-criteria li {
  color: grey;
  font-size: 0.8rem;
}

.password-criteria li.valid {
  color: green;
}
</style>

<script>
import { ref } from "vue";
import { useUserStore } from "@/store";

export default {
  data() {
    return {
      username: "",
      email: "",
      password: "",
      passwordType: ref("password"),
    };
  },
  methods: {
    togglePasswordVisibility() {
      this.passwordType =
        this.passwordType === "password" ? "text" : "password";
    },
    async signup() {
      const userStore = useUserStore();
      if (this.isMinLength && this.hasUpperCase && this.hasSpecialChar) {
        try {
          await userStore.signup(
            this.username,
            this.email,
            this.password,
            this.referrer
          );
        } catch (error) {
          alert("An error occurred during sign up.");
          console.error("Error during sign up:", error);
        }
      } else {
        alert("Password does not meet the required criteria.");
      }
    },
  },
  mounted() {
    document.title = "Red's Rewards - Sign Up";
  },

  computed: {
    isMinLength() {
      return this.password.length >= 8;
    },
    hasUpperCase() {
      return /[A-Z]/.test(this.password);
    },
    hasSpecialChar() {
      return /[^A-Za-z0-9]/.test(this.password);
    },
  },
};
</script>
