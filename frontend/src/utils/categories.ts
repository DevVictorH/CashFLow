export interface Category {
  name: string;
  type: string;
}

export interface ExpenseRecord {
  source: string;
  category: string;
  amount: number;
  createdAt?: number;
}

export interface IncomeRecord {
  source: string;
  category: string;
  amount: number;
  createdAt?: number;
}

const STORAGE_KEY = "cashflow_categories";
const EXPENSES_KEY = "cashflow_expenses";
const INCOMES_KEY = "cashflow_incomes";
export const STORAGE_UPDATED_EVENT = "cashflow-storage-updated";

const readStoredList = <T,>(key: string): T[] => {
  if (typeof window === "undefined") return [];

  try {
    const stored = localStorage.getItem(key);
    const parsed = stored ? JSON.parse(stored) : [];

    return Array.isArray(parsed) ? parsed : [];
  } catch {
    return [];
  }
};

const writeStoredList = <T,>(key: string, data: T[]) => {
  localStorage.setItem(key, JSON.stringify(data));
  window.dispatchEvent(new Event(STORAGE_UPDATED_EVENT));
};

export const getStoredCategories = (): Category[] => {
  return readStoredList<Category>(STORAGE_KEY);
};

export const setStoredCategories = (categories: Category[]) => {
  writeStoredList(STORAGE_KEY, categories);
};

export const getCategoriesByType = (type: string): Category[] => {
  return getStoredCategories().filter((category) => category.type === type);
};

export const getStoredExpenses = (): ExpenseRecord[] => {
  return readStoredList<ExpenseRecord>(EXPENSES_KEY);
};

export const setStoredExpenses = (expenses: ExpenseRecord[]) => {
  writeStoredList(EXPENSES_KEY, expenses);
};

export const getStoredIncomes = (): IncomeRecord[] => {
  return readStoredList<IncomeRecord>(INCOMES_KEY);
};

export const setStoredIncomes = (incomes: IncomeRecord[]) => {
  writeStoredList(INCOMES_KEY, incomes);
};
