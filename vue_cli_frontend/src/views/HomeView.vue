<template>
  <div class="home">
    <img alt="Reward logo" src="../assets/reward.png" />
    <HelloWorld msg="Welcome to Red's Rewards!" />
  </div>
</template>

<script>
// @ is an alias to /src
import HelloWorld from "@/components/HelloWorld.vue";

export default {
  name: "HomeView",
  components: {
    HelloWorld,
  },
};
</script>
