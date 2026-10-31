import { api } from "./api";
import { authConfig } from "./authService";

export type CategoryType = "EXPENSE" | "INCOME";

export type Category = {
  id: number;
  name: string;
  type: CategoryType;
};

export const getCategories = async () => {
  const response = await api.get<Category[]>("/categories", authConfig());
  return response.data;
};

export const createCategory = async (name: string, type: CategoryType) => {
  await api.post("/categories", { name, type }, authConfig());
};

export const deleteCategory = async (categoryId: number) => {
  await api.delete(`/categories/${categoryId}`, authConfig());
};
