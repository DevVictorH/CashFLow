import Sidebar from "../components/Sidebar";
import Header from "../components/Header";
import AddExpenseModal from "../components/AddExpenseModal";
import { useEffect, useState } from "react";
import { getCategories, type Category } from "../services/categoryService";
import { expenseService, type FinancialRecord, type FinancialRequest } from "../services/financialService";

export default function Expenses() {
  const [expenses, setExpenses] = useState<FinancialRecord[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [openModal, setOpenModal] = useState(false);

  useEffect(() => {
    Promise.all([expenseService.list(), getCategories()])
      .then(([storedExpenses, storedCategories]) => {
        setExpenses(storedExpenses);
        setCategories(storedCategories);
      })
      .catch(() => {
        setExpenses([]);
        setCategories([]);
      });
  }, []);

  const handleAddExpense = async (expense: FinancialRequest) => {
    await expenseService.create(expense);
    setExpenses(await expenseService.list());
    setOpenModal(false);
  };

  const handleDeleteExpense = async (expenseId: number) => {
    await expenseService.remove(expenseId);
    setExpenses((current) => current.filter((expense) => expense.id !== expenseId));
  };

  return (
    <div className="flex bg-gray-100 min-h-screen">

      {/* Sidebar */}
      <Sidebar />

      {/* Conteúdo */}
      <div className="flex-1 p-6 flex flex-col gap-6">

        <Header />

        {/* Header da página */}
        <div className="flex justify-between items-center">
          <h1 className="text-2xl font-bold">Todas despesas</h1>

          <button
            onClick={() => setOpenModal(true)}
            className="bg-red-100 text-red-700 px-4 py-2 rounded-lg font-medium hover:bg-red-200"
          >
            + Add Despesa
          </button>
        </div>

        {/* Card */}
        <div className="bg-white rounded-2xl shadow p-6">
          <h2 className="font-semibold mb-4">Expense Sources</h2>

          {expenses.length === 0 ? (
            <p className="text-gray-500">
              Sem despesas. Adicione alguma para começar!
            </p>
          ) : (
            <div className="space-y-3">
              {expenses.map((exp) => (
                <div
                  key={exp.id}
                  className="flex items-center justify-between bg-gray-50 p-4 rounded-xl hover:bg-gray-100"
                >
                  <div>
                    <p className="font-medium">{exp.source}</p>
                    <p className="text-sm text-gray-500">
                      {exp.categoryName ?? categories.find((category) => category.id === exp.categoryId)?.name ?? "Sem categoria"} - R$ {exp.amount.toFixed(2)}
                    </p>
                  </div>

                  {/* Botão delete */}
                  <button
                    onClick={() => void handleDeleteExpense(exp.id)}
                    className="text-gray-400 hover:text-red-500 text-lg"
                  >
                    ✕
                  </button>
                </div>
              ))}
            </div>
          )}
        </div>

      </div>

      {/* Modal */}
      {openModal && (
        <AddExpenseModal
          onClose={() => setOpenModal(false)}
          onAdd={handleAddExpense}
        />
      )}
    </div>
  );
}
