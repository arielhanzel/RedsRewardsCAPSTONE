import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import AboutView from "../views/AboutView.vue";
import LoginView from "../views/LoginView.vue";
import SignUpView from "../views/SignUpView.vue";
import RedeemView from "../views/RedeemView.vue";
import AdminView from "../views/AdminView.vue";
//import ProfilePageView from "../views/ProfilePageView.vue";
import ApiCalls from "../views/ApiCalls.vue";
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
  {
    path: "/redeem",
    name: "redeem",
    component: RedeemView,
    meta: { requiresAuth: true },
  },
  {
    path: "/admin",
    name: "admin",
    component: AdminView,
    meta: { requiresAuth: true, requiresAdmin: true },
  },
  //{
  // path: "/profile",
  // name: "profile",
  // component: ProfilePageView,
  //},
  {
    path: "/apicalls",
    name: "apicalls",
    component: ApiCalls,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach(async (to) => {
  const userStore = useUserStore();
  if (!userStore.loggedIn && localStorage.getItem("userToken")) {
    try {
      await userStore.initializeUser();
    } catch (error) {
      console.error("Error initializing user:", error);
      return { path: "/login" };
    }
  }

  if (to.meta.requiresAuth && !userStore.loggedIn) {
    alert("Please log in");
    return { path: "/login", query: { nextUrl: to.path } };
  }

  if (to.meta.requiresAdmin && userStore.role !== "ADMIN") {
    alert("Access denied. Admins only.");
    return { path: "/" };
  }

  return true;
});

export default router;
