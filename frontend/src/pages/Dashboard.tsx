import Sidebar from "../components/Sidebar";
import Header from "../components/Header";
import Card from "../components/Card";
import Table from "../components/Table";
import { useEffect, useState } from "react";
import { expenseService, incomeService, type FinancialRecord } from "../services/financialService";
import Charts from "../components/Charts";

const formatCurrency = (value: number) =>
  value.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });

export default function Dashboard() {
  const [incomes, setIncomes] = useState<FinancialRecord[]>([]);
  const [expenses, setExpenses] = useState<FinancialRecord[]>([]);

  useEffect(() => {
    Promise.all([incomeService.list(), expenseService.list()]).then(([incomes, expenses]) => {
      setIncomes(incomes);
      setExpenses(expenses);
    }).catch(() => {
      setIncomes([]);
      setExpenses([]);
    });
  }, []);

  const incomeTotal = incomes.reduce((total, income) => total + income.amount, 0);
  const expenseTotal = expenses.reduce((total, expense) => total + expense.amount, 0);

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

        

        <Table />

        <Charts incomes={incomes} expenses={expenses} />
      </div>
    </div>
  );
}