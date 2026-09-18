import axios from "axios";
import { authConfig } from "./authService";

export type FinancialRecord = {
  id: number;
  source: string;
  categoryId: number | null;
  categoryName: string | null;
  amount: number;
};

type FinancialResponse = {
  id: number;
  description: string;
  amount: number;
  categoryId: number | null;
  categoryName: string | null;
};

export type FinancialRequest = {
  description: string;
  amount: number;
  categoryId: number;
};

const toRecord = (item: FinancialResponse): FinancialRecord => ({
  id: item.id,
  source: item.description,
  categoryId: item.categoryId,
  categoryName: item.categoryName,
  amount: item.amount,
});

const createFinancialService = (resource: "expenses" | "incomes") => ({
  async list() {
    const response = await axios.get<FinancialResponse[]>(`/api/${resource}`, authConfig());
    return response.data.map(toRecord);
  },
  async create(data: FinancialRequest) {
    await axios.post(`/api/${resource}`, data, authConfig());
  },
  async remove(id: number) {
    await axios.delete(`/api/${resource}/${id}`, authConfig());
  },
});

export const expenseService = createFinancialService("expenses");
export const incomeService = createFinancialService("incomes");
