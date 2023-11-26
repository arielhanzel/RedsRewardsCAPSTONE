<template>
  <div class="header">
    <router-link to="/">
      <h1 class="app-name">Red's Rewards</h1>
    </router-link>
    <div
      class="menu-toggle"
      @click="toggleMenu($event)"
      @mouseenter="isCursorOverMenu = true"
      @mouseleave="isCursorOverMenu = false"
    >
      <span :class="{ bar1: isMenuOpen }"></span>
      <span :class="{ bar2: isMenuOpen }"></span>
      <span :class="{ bar3: isMenuOpen }"></span>
    </div>
    <nav
      :class="{ 'show-menu': isMenuOpen || isCursorOverMenu }"
      @mouseenter="isCursorOverMenu = true"
      @mouseleave="isCursorOverMenu = false"
      @click="toggleMenu(false)"
    >
      <router-link to="/apicalls" v-if="useStore.role === 'ADMIN'"
        >ApiCalls</router-link
      >
      <router-link to="/">Home</router-link>
      <router-link to="/admin" v-if="useStore.role === 'ADMIN'"
        >Admin</router-link
      >
      <router-link v-if="useStore.token && useStore.token != 'null'" to="/user"
        >User</router-link
      >

      <router-link to="/about">About</router-link>
      <router-link
        v-if="useStore.token && useStore.token != 'null'"
        to="/redeem"
        >Redeem</router-link
      >
      <router-link
        v-if="useStore.token && useStore.token != 'null'"
        @click="useStore.logout"
        to="/login"
        >Logout</router-link
      >
      <router-link
        v-if="!(useStore.token && useStore.token != 'null')"
        to="/login"
        >Login</router-link
      >
      <router-link
        v-if="!(useStore.token && useStore.token != 'null')"
        to="/signup"
        >Sign Up</router-link
      >
    </nav>
  </div>
  <!-- <p>Token: {{ useStore.token }}</p> -->
  <router-view />
</template>

<script>
export default {
  data() {
    return {
      isMenuOpen: false,
      isCursorOverMenu: false,
    };
  },
  methods: {
    toggleMenu(event, state = null) {
      if (event) {
        event.stopPropagation(); // Prevent the event from propagating up to the parent
      }
      if (state === null) {
        this.isMenuOpen = !this.isMenuOpen;
      } else {
        this.isMenuOpen = state;
      }
      this.isCursorOverMenu = this.isMenuOpen;
    },

    handleBodyClick(event) {
      if (!this.$el.contains(event.target) && this.isMenuOpen) {
        this.toggleMenu(null, false);
      }
    },
  },
  mounted() {
    document.addEventListener("click", this.handleBodyClick);
    document.addEventListener("click", this.closeMenuOnClick);
  },
  beforeUnmount() {
    document.removeEventListener("click", this.handleBodyClick);
    document.removeEventListener("click", this.closeMenuOnClick);
  },
  closeMenuOnClick() {
    if (!this.isCursorOverMenu) {
      // Close only if not over the menu
      this.isMenuOpen = false;
    }
  },
};
</script>

<script setup>
import { useUserStore } from "@/store";
const useStore = useUserStore();
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: white;
  /*background-color: #2c3e50;*/
}

.header {
  padding: 20px;
  padding-left: -20px;
  display: flex;
  /*background-color: #2c3e50;*/
}

.app-name {
  margin-top: -10px;
  margin-right: 20px;
  color: #df0000;
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

@media only screen and (max-width: 740px) {
  .header {
    flex-direction: column;
    align-items: center;
  }

  .menu-toggle {
    display: block;
    cursor: pointer;
    position: absolute;
    top: 1em;
    right: 2.3em;
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
    position: absolute;
    top: 49px;
    right: 1em;
    z-index: 1;
    background-color: rgba(0, 0, 0, 0.5);
    border-radius: 10px;
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

  .menu-toggle .bar1 {
    transform: rotate(-45deg) translateY(12px);
  }

  .menu-toggle .bar2 {
    opacity: 0;
  }

  .menu-toggle .bar3 {
    transform: rotate(45deg) translateY(-12px);
  }
}
</style>
