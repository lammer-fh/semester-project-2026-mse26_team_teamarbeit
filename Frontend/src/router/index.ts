import { createRouter, createWebHistory } from '@ionic/vue-router';
import { RouteRecordRaw } from 'vue-router';
import HomeView from '../views/HomeView.vue'
import AboutView from '../views/AboutView.vue'
import RoomsView from '../views/RoomsView.vue'
import ImprintView from '../views/ImprintView.vue'
import RoomDetailsView from "@/views/RoomDetailsView.vue";
import AvailabilityCheck from "../views/AvailabilityCheck.vue";

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/availability/:id',
    name: 'AvailabilityCheck',
    component: AvailabilityCheck
  },
  {
    path: '/home',
    name: 'Home',
    component: HomeView
  },
  {
    path: '/about',
    name: 'About',
    component: AboutView
  },
  {
    path: '/rooms',
    name: 'Rooms',
    component: RoomsView
  },
  {
    path: '/rooms/:id',
    name: 'RoomDetail',
    component: RoomDetailsView
  },
  {
    path: '/impressum',
    name: 'Impressum',
    component: ImprintView
  }
]
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})
export default router
