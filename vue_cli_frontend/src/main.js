import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import { createPinia } from "pinia";
import { useUserStore } from "@/store";

const pinia = createPinia();
const app = createApp(App);
app.use(pinia);
const userStore = useUserStore();
app.use(userStore).use(router).mount("#app");
