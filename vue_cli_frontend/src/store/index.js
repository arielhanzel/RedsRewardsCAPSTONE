import { defineStore } from "pinia";
import router from "../router";
import axios from "axios";

export const useUserStore = defineStore("user", {
  state: () => ({
    user: null,
    email: null,
    password: null,
    points: 220,
    pointsNeeded: 1000,
    loggedIn: false,
    token: localStorage.getItem("userToken"),
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
    setToken(token) {
      localStorage.setItem("userToken", token);
    },
    async login(username, password) {
      // Call an authentication service to log in the user (e.g., Firebase Authentication).
      // Replace this with your actual authentication logic.
      // const user = await authService.login(email, password);
      // if (this.loggedIn) {
      //   alert("Already logged in");
      //   router.push("/");
      // } else {
      //   if (password === "password" && username === "username") {
      //     const user = username;
      //     // Update the user in the store state.
      //     this.user = user;
      //     this.points = 780;
      //     this.loggedIn = true;
      //     router.push("/");
      //     alert("Successfully logged in");
      //   } else {
      //     alert("Wrong username or password");
      //   }
      // }
      const userData = {
        username: username,
        password: password,
      };

      axios
        .post("http://192.168.0.165:8000/auth/login", userData)
        .then((response) => {
          if (response.data.user != null) {
            this.token = response.data.jwt;
            localStorage.setItem("userToken", response.data.jwt);
            console.log("Token: ", localStorage.getItem("userToken"));
            this.user = username;
            this.loggedIn = true;
            console.log("User logged in:", response.data);
            router.push("/");
          } else {
            console.log("User not found:", response);
            alert("User not found");
          }
        })
        .catch((error) => {
          console.error("Error loggin in user:", error);
          alert("Error", error);
        });
    },
    logout() {
      console.log("User logged out:", this.user);
      this.token = null;
      localStorage.removeItem("userToken");
      console.log("Token: ", localStorage.getItem("userToken"));
      this.loggedIn = false;
      this.user = null;
    },
    signup(username, email, password) {
      const userData = {
        username: username,
        password: password,
      };
      const emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
      if (username !== "" && password !== "") {
        if (emailPattern.test(email)) {
          // this.user = username;
          // this.email = email;
          // this.password = password;
          // this.loggedIn = true;
          // router.push("/");
          axios
            .post("http://192.168.0.165:8000/auth/register", userData)
            .then((response) => {
              console.log("User registered:", response.data);
              this.login(username, password);
            })
            .catch((error) => {
              console.error("Error loggin in user:", error);
              alert("Error", error);
            });
        } else {
          alert("Invalid email address");
        }
      } else {
        alert("Please fill out all fields");
      }
    },
    redeemReward(reward) {
      if (this.points >= reward.points) {
        alert(`You have redeemed a ${reward.name}!`);
        console.log(`Redeemed: ${reward.name}`);
        this.points -= reward.points;
      } else {
        alert(
          `Insufficient points to redeem this reward. You need ${
            reward.points - this.points
          } more points.`
        );
      }
    },
    addPoints(points) {
      this.points += points;
    },
    // Define actions here
  },
});
