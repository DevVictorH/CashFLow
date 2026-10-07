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

export type ProfileData = {
  id: number;
  name: string;
  email: string;
};

export type ProfileUpdateData = {
  name: string;
  email: string;
  password: string;
};

export const authConfig = () => ({
  headers: {
    Authorization: `Bearer ${localStorage.getItem("token") ?? ""}`,
  },
});

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

export const requestPasswordRecovery = async (email: string) => {
  await axios.post(`${API_URL}/recovery-password`, { email });
};

export const verifyRecoveryCode = async (email: string, code: string) => {
  await axios.post(`${API_URL}/verify-recovery-code`, { email, code });
};

export const resetPassword = async (email: string, code: string, newPassword: string) => {
  await axios.post(`${API_URL}/change-password`, { email, code, newPassword });
};

export const getProfile = async () => {
  const response = await axios.get(`${API_URL}/me`, authConfig());
  return response.data as ProfileData;
};

export const updateProfile = async (data: ProfileUpdateData) => {
  const response = await axios.put(`${API_URL}/me`, data, authConfig());
  return response.data as ProfileData;
};