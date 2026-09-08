import axios from "axios";

const API_URL = "/api/auth";

export type RegisterData = {
  name: string;
  email: string;
  password: string;
  role: "USER" | "ADMIN";
};

export type LoginData = {
  token: string;
  name: string;
};

export const login = async (email: string, password: string) => {
  const response = await axios.post(`${API_URL}/login`, {
    email,
    password,
  });

  return response.data as LoginData;
};

export const register = async (data: RegisterData) => {
  await axios.post(`${API_URL}/register`, data);
};