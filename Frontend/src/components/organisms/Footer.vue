<template>
  <footer class="app-footer">
    <div class="footer-inner">
      <div class="footer-grid">

        <div class="footer-brand">
          <h3>Boutique Hotel Technikum</h3>
          <p>Smart comfort in the heart of Vienna</p>
        </div>

        <div class="footer-section">
          <h4>Kontakt</h4>
          <address>
            <p>Höchstädtplatz 6</p>
            <p>1200 Wien, Österreich</p>
            <a href="tel:+43133340031">+43 1 333 40 31</a>
            <a href="mailto:info@hotel-technikum.at">info@hotel-technikum.at</a>
          </address>
        </div>

        <div class="footer-section">
          <h4>Links</h4>
          <nav>
            <router-link to="/about">Über uns</router-link>
            <router-link to="/impressum">Impressum</router-link>
          </nav>
        </div>

      </div>

      <div class="footer-bottom">
        © {{ currentYear }} Boutique Hotel Technikum. Alle Rechte vorbehalten.
      </div>
    </div>
  </footer>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'AppFooter',
  computed: {
    currentYear(): number {
      return new Date().getFullYear()
    },
  },
})
</script>

<style scoped>
.app-footer {
  background: #1c1917;
  color: #ffffff;
  padding: 3rem 0;
  margin-top: 5rem;
}

.footer-inner {
  max-width: 80rem;
  margin: 0 auto;
  padding: 0 1rem;
}

.footer-grid {
  display: grid;
  grid-template-columns: 1fr;
  gap: 2rem;
}

@media (min-width: 768px) {
  .footer-grid {
    grid-template-columns: repeat(3, 1fr);
  }
}

/* brand column */
.footer-brand h3 {
  font-size: 1.125rem;
  font-weight: 600;
  color: #ffffff;
  margin: 0 0 1rem;
}

.footer-brand p {
  font-size: 0.875rem;
  color: #a8a29e;
  line-height: 1.6;
  margin: 0;
}

/* contact / links columns */
.footer-section h4 {
  font-size: 1rem;
  font-weight: 500;
  color: #ffffff;
  margin: 0 0 1rem;
}

.footer-section address {
  font-style: normal;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.footer-section address p,
.footer-section address a {
  font-size: 0.875rem;
  color: #a8a29e;
  margin: 0;
  text-decoration: none;
}

.footer-section address a:hover {
  color: #ffffff;
}

.footer-section nav {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.footer-section nav a {
  font-size: 0.875rem;
  color: #a8a29e;
  text-decoration: none;
  transition: color 150ms ease;
}

.footer-section nav a:hover,
.footer-section nav a.router-link-active {
  color: #ffffff;
}

/* bottom bar */
.footer-bottom {
  margin-top: 2rem;
  padding-top: 2rem;
  border-top: 1px solid #292524;
  text-align: center;
  font-size: 0.875rem;
  color: #a8a29e;
}
</style>