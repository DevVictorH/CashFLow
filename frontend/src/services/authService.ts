import { api } from "./api";

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
  const response = await api.post("/auth/login", {
    email,
    password,
  });

  return response.data as LoginData;
};

export const register = async (data: RegisterData) => {
  await api.post("/auth/register", data);
};

export const requestPasswordRecovery = async (email: string) => {
  await api.post("/auth/recovery-password", { email });
};

export const verifyRecoveryCode = async (email: string, code: string) => {
  await api.post("/auth/verify-recovery-code", { email, code });
};

export const resetPassword = async (email: string, code: string, newPassword: string) => {
  await api.post("/auth/change-password", { email, code, newPassword });
};

export const getProfile = async () => {
  const response = await api.get("/auth/me", authConfig());
  return response.data as ProfileData;
};

export const updateProfile = async (data: ProfileUpdateData) => {
  const response = await api.put("/auth/me", data, authConfig());
  return response.data as ProfileData;
};