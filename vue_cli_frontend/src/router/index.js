import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import AboutView from "../views/AboutView.vue";
import LoginView from "../views/LoginView.vue";
import SignUpView from "../views/SignUpView.vue";
import { useUserStore } from "@/store";

const routes = [
  {
    path: "/",
    name: "home",
    component: HomeView,
    // meta: { requiresAuth: true },
  },
  {
    path: "/about",
    name: "about",
    component: AboutView,
    // meta: { requiresAuth: true },
  },
  {
    path: "/login",
    name: "login",
    component: LoginView,
  },
  {
    path: "/signup",
    name: "signup",
    component: SignUpView,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to) => {
  const useStore = useUserStore();
  if (to.meta.requiresAuth) {
    if (!useStore.loggedIn) {
      alert("Please log in");
      return {
        path: "/login",
        query: { nextUrl: to.path },
      };
    } else {
      return true;
    }
  }
});

export default router;
