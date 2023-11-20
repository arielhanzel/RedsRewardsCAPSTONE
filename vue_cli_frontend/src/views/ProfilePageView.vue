<template>
  <div>
    <h1>Profile Page</h1>
    <h2>Welcome to Your Red's Rewards Profile Page!</h2>
  </div>
  <div class="container">
    <!-- Your items go here -->
    <div class="item">Username:</div>
    <div class="item">Points:</div>
    <div class="item">Registered classes:</div>
    <div class="item">Attendance (Check-in):</div>
    <div class="item">Attendance (Check-out):</div>
    <div class="item">Reward Redemptions:</div>
  </div>
</template>

<style>
.container {
  display: flex;
  flex-direction: column;
  justify-content: flex-start; /* Align items to the top of the container */
  align-items: flex-start; /* Align items to the left of the container */
  height: 100vh; /* Set container height to 100% of the viewport height */
}

.item {
  /* Additional styles for your items */
  margin-bottom: 10px; /* Optional: Add margin between items */
}
</style>
