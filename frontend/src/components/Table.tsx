import { useEffect, useState } from "react";
import { getCategories } from "../services/categoryService";
import { expenseService, incomeService, type FinancialRecord } from "../services/financialService";

type Transaction = {
  name: string;
  category: string;
  amount: number;
  type: "income" | "expense";
};

const formatCurrency = (value: number) =>
  value.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });

const getTransactions = async (): Promise<Transaction[]> => {
  const [incomes, expenses, categories] = await Promise.all([
    incomeService.list(),
    expenseService.list(),
    getCategories(),
  ]);
  const getCategoryName = (record: FinancialRecord) =>
    record.categoryName ?? categories.find((category) => category.id === record.categoryId)?.name ?? "Sem categoria";

  const incomeTransactions: Transaction[] = incomes.map((income) => ({
    name: income.source,
    category: getCategoryName(income),
    amount: income.amount,
    type: "income",
  }));
  const expenseTransactions: Transaction[] = expenses.map((expense) => ({
    name: expense.source,
    category: getCategoryName(expense),
    amount: expense.amount,
    type: "expense",
  }));

  return [...incomeTransactions, ...expenseTransactions].slice(0, 10);
};

export default function Table() {
  const [transactions, setTransactions] = useState<Transaction[]>([]);

  useEffect(() => {
    getTransactions().then(setTransactions).catch(() => setTransactions([]));
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
              <tr key={`${item.type}-${item.name}-${index}`} className="border-t">
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