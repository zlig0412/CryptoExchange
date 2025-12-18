import figlet from "figlet"
import index from "./index.html"
import { CasdoorCallback } from './src/api/casdoorcallback'
import { GetCurrentUser, LogoutCurrentUser } from "./src/api/currentuser"

const server = Bun.serve({
  port: 3000,
  routes: {
    "/api/auth/casdoor/callback": CasdoorCallback,
    "/api/me": GetCurrentUser,
    "/api/logout": LogoutCurrentUser,
    "/*": index,
  }
});

console.log(`Listening on ${server.url}`);