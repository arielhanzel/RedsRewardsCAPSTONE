<template>
  <div class="home">
    <!-- Use store variables 
    <p v-if="user">User: {{ user }}</p>
    <p v-if="email">Email: {{ email }}</p>
    <p v-if="token">Token: {{ token }}</p>
     ... and so on -->

    <img alt="Reward logo" src="../assets/reward.png" />
    <HelloWorld msg="Welcome to Red's Rewards!" />
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from "@/components/HelloWorld.vue";
import { useUserStore } from "@/store/index";

export default {
  name: "HomeView",
  components: {
    HelloWorld,
  },
  setup() {
    const userStore = useUserStore();

    return {
      // expose properties to the template
      user: userStore.user,
      email: userStore.email,
      token: userStore.token,
      // ... other properties you want to expose
    };
  },
  mounted() {
    const userStore = useUserStore();
    if (userStore.loggedIn && userStore.user) {
      document.title = "Red's Rewards - " + userStore.user;
    } else {
      document.title = "Red's Rewards";
    }
    userStore.viewTotalPoints();
  },
};
</script>
