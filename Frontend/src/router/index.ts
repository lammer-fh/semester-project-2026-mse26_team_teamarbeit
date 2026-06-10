import { createRouter, createWebHistory } from '@ionic/vue-router';
import { RouteRecordRaw } from 'vue-router';
import HomeView from '../views/HomeView.vue'
import AboutView from '../views/AboutView.vue'
import RoomsView from '../views/RoomsView.vue'
import ImprintView from '../views/ImprintView.vue'
import BookingView from '../views/BookingView.vue'
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
    component: HomeView,
    meta: { title: 'Home' }
  },
  {
    path: '/about',
    name: 'About',
    component: AboutView,
    meta: { title: 'Über uns' }
  },
  {
    path: '/rooms',
    name: 'Rooms',
    component: RoomsView,
    meta: { title: 'Zimmer' }
  },
  {
    path: '/rooms/:id',
    name: 'RoomDetail',
    component: RoomDetailsView,
    meta: { title: 'Zimmer Details' }
  },
  {
    path: '/rooms/:id/book',
    name: 'Booking',
    component: BookingView,
    meta: { title: 'Buchung' }
  },
  {
    path: '/impressum',
    name: 'Impressum',
    component: ImprintView,
    meta: { title: 'Impressum' }
  }
]
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})
const DEFAULT_TITLE = 'Boutique Hotel Technikum'
router.afterEach((to) => {
  document.title = to.meta.title ? DEFAULT_TITLE + " - " + (to.meta.title as string) : DEFAULT_TITLE
})
export default router
