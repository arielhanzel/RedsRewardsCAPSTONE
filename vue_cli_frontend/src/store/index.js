import { defineStore } from "pinia";
import router from "../router";

export const useUserStore = defineStore("user", {
  state: () => ({
    user: null,
    email: null,
    password: null,
    points: 220,
    pointsNeeded: 1000,
    loggedIn: false,
  }),
  getters: {
    progressPercentage() {
      return (this.points / this.pointsNeeded) * 100;
    },
    pointsToGoal() {
      return this.pointsNeeded - this.points;
    },
  },
  actions: {
    async login(username, password) {
      // Call an authentication service to log in the user (e.g., Firebase Authentication).
      // Replace this with your actual authentication logic.
      // const user = await authService.login(email, password);
      if (this.loggedIn) {
        alert("Already logged in");
        router.push("/");
      } else {
        if (password === "password" && username === "username") {
          const user = username;
          // Update the user in the store state.
          this.user = user;
          this.points = 780;
          this.loggedIn = true;
          router.push("/");
          alert("Successfully logged in");
        } else {
          alert("Wrong username or password");
        }
      }
    },
    logout() {
      this.user = null;
      this.loggedIn = false;
    },
    signup(username, email, password) {
      const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
      if (username !== "" && password !== "") {
        if (emailPattern.test(email)) {
          this.user = username;
          this.email = email;
          this.password = password;
          this.loggedIn = true;
          router.push("/");
        } else {
          alert("Invalid email address");
        }
      } else {
        alert("Please fill out all fields");
      }
    },
    // Define actions here
  },
});
