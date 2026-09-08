import Sidebar from "../components/Sidebar";
import Header from "../components/Header";
import Card from "../components/Card";
import Table from "../components/Table";
import { useEffect, useState } from "react";
import {
  getStoredExpenses,
  getStoredIncomes,
  STORAGE_UPDATED_EVENT,
} from "../utils/categories";

const formatCurrency = (value: number) =>
  value.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });

export default function Dashboard() {
  const [incomeTotal, setIncomeTotal] = useState(0);
  const [expenseTotal, setExpenseTotal] = useState(0);

  useEffect(() => {
    const updateTotals = () => {
      setIncomeTotal(
        getStoredIncomes().reduce((total, income) => total + income.amount, 0),
      );
      setExpenseTotal(
        getStoredExpenses().reduce((total, expense) => total + expense.amount, 0),
      );
    };

    updateTotals();
    window.addEventListener(STORAGE_UPDATED_EVENT, updateTotals);
    window.addEventListener("storage", updateTotals);

    return () => {
      window.removeEventListener(STORAGE_UPDATED_EVENT, updateTotals);
      window.removeEventListener("storage", updateTotals);
    };
  }, []);

  return (
    <div className="flex bg-gray-100 min-h-screen">
      <Sidebar />

      <div className="flex-1 p-6 flex flex-col gap-6">
        <Header />

        <div className="grid grid-cols-3 gap-4">
          <Card
            title="Dinheiro total"
            value={formatCurrency(incomeTotal - expenseTotal)}
            color="text-indigo-600"
          />
          <Card title="Receitas" value={formatCurrency(incomeTotal)} color="text-green-500" />
          <Card title="Despesas" value={formatCurrency(expenseTotal)} color="text-red-500" />
        </div>

        {/* <Charts /> */}

        <Table />
      </div>
    </div>
  );
}