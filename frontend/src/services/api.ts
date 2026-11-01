import axios from "axios";

const configuredBaseURL = import.meta.env.VITE_API_URL?.replace(/\/+$/, "");
const baseURL = configuredBaseURL
  ? configuredBaseURL.endsWith("/api")
    ? configuredBaseURL
    : `${configuredBaseURL}/api`
  : "/api";

export const api = axios.create({
  baseURL,
});
