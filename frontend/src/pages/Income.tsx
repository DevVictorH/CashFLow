import Sidebar from "../components/Sidebar";
import Header from "../components/Header";
import AddIncomeModal from "../components/AddIncomeModal";
import { useEffect, useState } from "react";
import { getCategories, type Category } from "../services/categoryService";
import { incomeService, type FinancialRecord, type FinancialRequest } from "../services/financialService";

export default function Incomes() {
  const [incomes, setIncomes] = useState<FinancialRecord[]>([]);
  const [categories, setCategories] = useState<Category[]>([]);
  const [openModal, setOpenModal] = useState(false);

  useEffect(() => {
    Promise.all([incomeService.list(), getCategories()])
      .then(([storedIncomes, storedCategories]) => {
        setIncomes(storedIncomes);
        setCategories(storedCategories);
      })
      .catch(() => {
        setIncomes([]);
        setCategories([]);
      });
  }, []);

  const handleAddIncome = async (income: FinancialRequest) => {
    await incomeService.create(income);
    setIncomes(await incomeService.list());
    setOpenModal(false);
  };

  const handleDeleteIncome = async (incomeId: number) => {
    await incomeService.remove(incomeId);
    setIncomes((current) => current.filter((income) => income.id !== incomeId));
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
          <h1 className="text-2xl font-bold">Todas receitas</h1>

          <button
            onClick={() => setOpenModal(true)}
            className="bg-green-100 text-green-700 px-4 py-2 rounded-lg font-medium hover:bg-green-200"
          >
            + Add Receita
          </button>
        </div>

        {/* Card */}
        <div className="bg-white rounded-2xl shadow p-6">
          <h2 className="font-semibold mb-4">Income Sources</h2>

          {incomes.length === 0 ? (
            <p className="text-gray-500">
              Sem receitas. Adicione alguma para começar!
            </p>
          ) : (
            <div className="space-y-3">
              {incomes.map((income) => (
                <div
                  key={income.id}
                  className="flex items-center justify-between bg-gray-50 p-4 rounded-xl hover:bg-gray-100"
                >
                  <div>
                    <p className="font-medium">{income.source}</p>
                    <p className="text-sm text-gray-500">
                      {income.categoryName ?? categories.find((category) => category.id === income.categoryId)?.name ?? "Sem categoria"} - R$ {income.amount.toFixed(2)}
                    </p>
                  </div>

                  {/* Botão delete */}
                  <button
                    onClick={() => void handleDeleteIncome(income.id)}
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
        <AddIncomeModal
          onClose={() => setOpenModal(false)}
          onAdd={handleAddIncome}
        />
      )}
    </div>
  );
}
