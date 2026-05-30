<template>
  <ion-app>

    <!-- Mobile Side Menu -->
    <ion-menu content-id="main-content" side="start">
      <ion-header>
        <ion-toolbar>
          <ion-title>Menü</ion-title>
        </ion-toolbar>
      </ion-header>
      <ion-content>
        <ion-list class="mobile-menu">
          <ion-menu-toggle
              v-for="item in navItems"
              :key="item.path"
          >
            <ion-item
                button
                :router-link="item.path"
                router-direction="root"
                lines="none"
            >
              <ion-icon :icon="item.icon" slot="start" />
              <ion-label>{{ item.label }}</ion-label>
            </ion-item>
          </ion-menu-toggle>
        </ion-list>
      </ion-content>
    </ion-menu>

    <ion-page id="main-content">
      <!-- Header / Navbar -->
      <ion-header class="app-header">
        <ion-toolbar class="app-toolbar">
          <ion-buttons slot="start" class="mobile-menu-btn">
            <ion-menu-button />
          </ion-buttons>
          <ion-title
              class="brand-title"
              @click="goHome"
          >
            Boutique Hotel Technikum
          </ion-title>

          <!-- Desktop Navigation -->
          <nav slot="end" class="desktop-nav" aria-label="Hauptnavigation">
            <RouterLink
                v-for="item in navItems"
                :key="item.path"
                :to="item.path"
                exact-active-class="active"
            >
              {{ item.label }}
            </RouterLink>
          </nav>
        </ion-toolbar>
      </ion-header>

      <ion-content :fullscreen="true">
        <ion-router-outlet />
      </ion-content>
    </ion-page>
  </ion-app>
</template>

<script setup lang="ts">
import {
  IonApp,
  IonButtons,
  IonContent,
  IonHeader,
  IonIcon,
  IonItem,
  IonLabel,
  IonList,
  IonMenu,
  IonMenuButton,
  IonMenuToggle,
  IonPage,
  IonRouterOutlet,
  IonTitle,
  IonToolbar
} from '@ionic/vue';

import {
  homeOutline,
  bedOutline,
  informationCircleOutline,
  documentTextOutline
} from 'ionicons/icons';

import { RouterLink, useRouter } from 'vue-router';

const router = useRouter();

const navItems = [
  {
    label: 'Home',
    path: '/home',
    icon: homeOutline
  },
  {
    label: 'Zimmer',
    path: '/rooms',
    icon: bedOutline
  },
  {
    label: 'Über uns',
    path: '/about',
    icon: informationCircleOutline
  },
  {
    label: 'Impressum',
    path: '/impressum',
    icon: documentTextOutline
  }
];

function goHome() {
  router.push('/home');
}
</script>

<style>
body {
  background: #ffffff !important;
}

ion-content {
  --background: #ffffff;
}

ion-menu ion-content {
  --background: #ffffff;
}

ion-menu ion-toolbar {
  --background: #ffffff;
  --color: #1c1917;
}

ion-menu ion-item {
  --background: #ffffff;
  --color: #1c1917;
}

.app-toolbar {
  --background: #ffffff;
  --color: #1c1917;
  --border-color: #e7e5e4;
}

.brand-title {
  font-size: 1.1rem;
  font-weight: 700;
  cursor: pointer;
}

.mobile-menu-btn {
  display: flex;
}

.desktop-nav {
  display: none;
  align-items: center;
  gap: 1.5rem;
  padding-right: 1rem;
}

.desktop-nav a {
  color: #44403c;
  text-decoration: none;
  font-size: 0.95rem;
  font-weight: 500;
  cursor: pointer;
  padding: 6px 0;
  transition: color 0.2s ease;
}

.desktop-nav a:hover,
.desktop-nav a.active {
  color: var(--app-dark-orange);
}

.mobile-menu {
  background-color: #ffffff;
}

/* Desktop breakpoint */
@media (min-width: 768px) {
  .mobile-menu-btn {
    display: none !important;
  }

  .desktop-nav {
    display: flex;
  }
}
</style>