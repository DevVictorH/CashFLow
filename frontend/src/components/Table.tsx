import { useEffect, useState } from "react";
import {
  getStoredExpenses,
  getStoredIncomes,
  STORAGE_UPDATED_EVENT,
  type ExpenseRecord,
  type IncomeRecord,
} from "../utils/categories";

type Transaction = {
  name: string;
  category: string;
  amount: number;
  type: "income" | "expense";
  createdAt?: number;
};

const formatCurrency = (value: number) =>
  value.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });

const getTransactions = (): Transaction[] => {
  const incomes: Transaction[] = getStoredIncomes().map((income: IncomeRecord) => ({
    name: income.source,
    category: income.category,
    amount: income.amount,
    type: "income",
    createdAt: income.createdAt,
  }));
  const expenses: Transaction[] = getStoredExpenses().map((expense: ExpenseRecord) => ({
    name: expense.source,
    category: expense.category,
    amount: expense.amount,
    type: "expense",
    createdAt: expense.createdAt,
  }));

  return [...incomes, ...expenses]
    .sort((first, second) => (second.createdAt ?? 0) - (first.createdAt ?? 0))
    .slice(0, 10);
};

export default function Table() {
  const [transactions, setTransactions] = useState<Transaction[]>([]);

  useEffect(() => {
    const updateTransactions = () => setTransactions(getTransactions());

    updateTransactions();
    window.addEventListener(STORAGE_UPDATED_EVENT, updateTransactions);
    window.addEventListener("storage", updateTransactions);

    return () => {
      window.removeEventListener(STORAGE_UPDATED_EVENT, updateTransactions);
      window.removeEventListener("storage", updateTransactions);
    };
  }, []);

  return (
    <div className="bg-white p-4 rounded-2xl shadow-sm">
      <h2 className="mb-4 font-semibold">Transações recentes</h2>

      <table className="w-full text-center">
        <thead>
          <tr className="text-gray-500">
            <th>Nome</th>
            <th>Categoria</th>
            <th>Quantia</th>
          </tr>
        </thead>

        <tbody>
          {transactions.length === 0 ? (
            <tr>
              <td colSpan={3} className="border-t py-4 text-gray-500">
                Nenhuma transação registrada.
              </td>
            </tr>
          ) : (
            transactions.map((item, index) => (
              <tr key={`${item.type}-${item.createdAt ?? index}`} className="border-t">
                <td>{item.name}</td>
                <td>{item.category}</td>
                <td className="income">
                  {item.type === "income" ? "+" : "-"}{formatCurrency(item.amount)}
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}