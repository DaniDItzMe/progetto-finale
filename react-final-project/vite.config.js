import react from '@vitejs/plugin-react'
import { defineConfig } from 'vite'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server:{
    watch: {
      usePolling: true
    },
    proxy:{
      "/api/login":{
        target:"http://localhost:8080",
        changeOrigin:true,
        rewrite: (path) => path.replace("/api/login", "/login")

      },
      "/api/logout":{
        target:"http://localhost:8080",
        changeOrigin:true,
        rewrite: (path) => path.replace("/api/logout", "/logout")

      },


    }
  }
})
