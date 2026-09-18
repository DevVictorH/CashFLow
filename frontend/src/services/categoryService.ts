import axios from "axios";
import { authConfig } from "./authService";

export type CategoryType = "EXPENSE" | "INCOME";

export type Category = {
  id: number;
  name: string;
  type: CategoryType;
};

const API_URL = "/api/categories";

export const getCategories = async () => {
  const response = await axios.get<Category[]>(API_URL, authConfig());
  return response.data;
};

export const createCategory = async (name: string, type: CategoryType) => {
  await axios.post(API_URL, { name, type }, authConfig());
};

export const deleteCategory = async (categoryId: number) => {
  await axios.delete(`${API_URL}/${categoryId}`, authConfig());
};
