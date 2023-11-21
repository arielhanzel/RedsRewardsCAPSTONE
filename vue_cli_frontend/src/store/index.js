import { defineStore } from "pinia";
import router from "../router";
import axios from "axios";

export const useUserStore = defineStore("user", {
  state: () => ({
    userId: localStorage.getItem("userId"),
    user: localStorage.getItem("username"),
    email: localStorage.getItem("email"),
    password: null,
    referrer: null,
    points: 0,
    pointsNeeded: 1000,
    loggedIn: false,
    token: localStorage.getItem("userToken"),
    role: null,
    registeredClasses: null,
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
    async initializeUser() {
      const token = localStorage.getItem("userToken");

      if (token) {
        this.token = token;
        this.loggedIn = true;

        this.viewTotalPoints();

        try {
          const response = await axios.get(
            "http://localhost:8000/user/user/role",
            {
              params: { username: localStorage.getItem("username") },
              headers: {
                Authorization: `Bearer ${localStorage.getItem("userToken")}`,
              },
            }
          );

          if (response.data) {
            this.role = response.data;
          }
        } catch (error) {
          console.error("Error fetching user data:", error);
          // Handle errors, e.g., by logging out the user
        }
      }
    },
    async login(username, password) {
      const userData = {
        username: username,
        password: password,
      };

      try {
        const response = await axios.post(
          "http://localhost:8000/auth/login",
          userData
        );

        if (response.data.jwt != null) {
          this.userId = response.data.userId;
          localStorage.setItem("userId", response.data.userId);
          this.user = username;
          localStorage.setItem("username", this.user);
          this.email = response.data.email;
          localStorage.setItem("email", response.data.email);
          this.role = response.data.role[0].authority;
          this.token = response.data.jwt;
          localStorage.setItem("userToken", response.data.jwt);

          this.loggedIn = true;

          console.log("User logged in:", response.data);
          router.push("/");
        } else {
          console.log("User not found:", response);
          alert("User not found");
        }
      } catch (error) {
        console.error("Error logging in user:", error);
        alert(`${error}`);
      }
    },
    logout() {
      console.log("User logged out:", this.user);
      this.token = null;
      localStorage.removeItem("user_id");
      localStorage.removeItem("username");
      localStorage.removeItem("email");
      localStorage.removeItem("role");
      localStorage.removeItem("userToken");
      console.log("Token: ", localStorage.getItem("userToken"));
      this.loggedIn = false;
      this.user = null;
      this.role = null;
    },
    signup(username, email, password, referrer) {
      const userData = {
        username: username,
        email: email,
        password: password,
        referrer: referrer,
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
            .post("http://localhost:8000/auth/register", userData)
            .then((response) => {
              console.log("User registered:", response.data);
              this.login(username, password);
            })
            .catch((error) => {
              console.error("Error loggin in user:", error);
              alert(`${error}`);
            });
        } else {
          alert("Invalid email address");
        }
      } else {
        alert("Please fill out all fields");
      }
    },
    async addPoints(points) {
      try {
        const username = this.user;
        const token = this.token; // Replace with your actual Bearer token

        // Make a POST request to the endpoint with the username and Bearer token
        const response = await axios.post(
          "http://localhost:8000/user/rewardpoint/addpoints",
          { username: username, points: points },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        const addedPoints = response.data;
        console.log("Added Points:", addedPoints);
        this.points += addedPoints.pointBalance;
      } catch (error) {
        console.error("Error adding points:", error);
      }
    },
    async viewTotalPoints() {
      try {
        const username = this.user;
        const token = this.token;

        // Make a POST request to the endpoint with the username and Bearer token
        const response = await axios.post(
          "http://localhost:8000/user/rewardpoint/totalpoints",
          { username: username },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        const totalPoints = response.data;
        console.log("Total Points:", totalPoints);
        this.points = totalPoints;
      } catch (error) {
        console.error("Error fetching total points:", error);
      }
    },
    async redeemReward(reward) {
      if (this.points >= reward.points) {
        try {
          const username = this.user;
          const token = this.token;
          const item = reward.name;
          const points = reward.points;

          // Make a POST request to the endpoint with the username and Bearer token
          const response = await axios.post(
            "http://localhost:8000/user/rewardredemption/redemption",
            { username: username, items: item, point: points },
            {
              headers: {
                Authorization: `Bearer ${token}`,
              },
            }
          );
          alert(`You have redeemed a ${reward.name}!`);
          console.log("Redeemed:", response.data);
          this.points -= reward.points;
        } catch (error) {
          console.error("Error fetching total points:", error);
        }
      } else {
        alert(
          `Insufficient points to redeem this reward. You need ${
            reward.points - this.points
          } more points.`
        );
      }
    },
    // Define actions here
  },
});
