<template>
  <div class="header">
    <router-link to="/">
      <h1 class="app-name">Red's Rewards</h1>
    </router-link>
    <div class="menu-toggle" @click="toggleMenu">
      <span></span>
      <span></span>
      <span></span>
    </div>
    <nav :class="{ 'show-menu': isMenuOpen }">
      <router-link to="/">Home</router-link>
      <router-link to="/about">About</router-link>
      <router-link to="/login">Login</router-link>
      <router-link to="/signup">Sign Up</router-link>
    </nav>
  </div>
  <router-view />
</template>

<script>
export default {
  data() {
    return {
      isMenuOpen: false,
    };
  },
  methods: {
    toggleMenu() {
      this.isMenuOpen = !this.isMenuOpen;
    },
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: white;
  background-color: #2c3e50;
}

.header {
  padding: 20px;
  padding-left: -20px;
  display: flex;
  background-color: #2c3e50;
}

.app-name {
  margin-top: -10px;
  margin-right: 20px;
  color: red;
}

nav {
  margin-left: auto;
}

a {
  text-decoration: none;
  color: red;
  transition: color 0.2s ease-in-out;
}

nav a {
  font-weight: bold;
  color: white;
  padding: 5px;
  border-radius: 20px;
  transition: background-color 0.4s;
}

nav a:hover {
  background-color: white;
  color: red;
}

nav a.router-link-exact-active {
  color: rgb(255, 0, 0);
}

@media only screen and (max-width: 600px) {
  .header {
    flex-direction: column;
    align-items: center;
  }

  .menu-toggle {
    display: block;
    cursor: pointer;
    position: absolute;
    top: 1em;
    right: 3em;
  }

  .menu-toggle span {
    display: block;
    width: 30px;
    height: 3px;
    background-color: white;
    margin: 6px 0;
    transition: 0.4s;
  }

  nav {
    display: none;
  }

  .show-menu {
    display: flex;
    flex-direction: column;
    align-items: center;
  }

  .show-menu a {
    text-decoration: none;
    color: white;
    transition: color 0.2s ease-in-out;
    margin-top: 10px;
  }

  .show-menu nav a.router-link-exact-active {
    color: rgb(255, 0, 0);
  }
}
</style>
