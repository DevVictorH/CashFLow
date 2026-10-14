import { getCategories } from "./categoryService";
import { expenseService, incomeService, type FinancialRecord } from "./financialService";

export type Transaction = {
  id: number;
  name: string;
  category: string;
  amount: number;
  createdAt: string | null;
  type: "income" | "expense";
};

export const getTransactions = async (): Promise<Transaction[]> => {
  const [incomes, expenses, categories] = await Promise.all([
    incomeService.list(),
    expenseService.list(),
    getCategories(),
  ]);
  const getCategoryName = (record: FinancialRecord) =>
    record.categoryName ?? categories.find((category) => category.id === record.categoryId)?.name ?? "Sem categoria";

  const transactions: Transaction[] = [
    ...incomes.map((income) => ({
      id: income.id,
      name: income.source,
      category: getCategoryName(income),
      amount: income.amount,
      createdAt: income.createdAt,
      type: "income" as const,
    })),
    ...expenses.map((expense) => ({
      id: expense.id,
      name: expense.source,
      category: getCategoryName(expense),
      amount: expense.amount,
      createdAt: expense.createdAt,
      type: "expense" as const,
    })),
  ];

  return transactions.sort((first, second) => {
    const firstDate = first.createdAt ? new Date(first.createdAt).getTime() : 0;
    const secondDate = second.createdAt ? new Date(second.createdAt).getTime() : 0;
    return secondDate - firstDate;
  });
};