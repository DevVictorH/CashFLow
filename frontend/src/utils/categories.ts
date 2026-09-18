export interface Category {
  name: string;
  type: string;
}

const STORAGE_KEY = "cashflow_categories";
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

