import Sidebar from "../components/Sidebar";
import Header from "../components/Header";
import AddIncomeModal from "../components/AddIncomeModal";
import { useEffect, useState } from "react";
import {
  getStoredIncomes,
  setStoredIncomes,
  type IncomeRecord,
} from "../utils/categories";

export default function Incomes() {
  const [incomes, setIncomes] = useState<IncomeRecord[]>([]);
  const [openModal, setOpenModal] = useState(false);

  useEffect(() => {
    setIncomes(getStoredIncomes());
  }, []);

  const handleAddIncome = (income: IncomeRecord) => {
    const updated = [...incomes, income];
    setIncomes(updated);
    setStoredIncomes(updated);
    setOpenModal(false);
  };

  const handleDeleteIncome = (indexToRemove: number) => {
    const updated = incomes.filter((_, index) => index !== indexToRemove);
    setIncomes(updated);
    setStoredIncomes(updated);
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
              {incomes.map((income, index) => (
                <div
                  key={index}
                  className="flex items-center justify-between bg-gray-50 p-4 rounded-xl hover:bg-gray-100"
                >
                  <div>
                    <p className="font-medium">{income.source}</p>
                    <p className="text-sm text-gray-500">{income.category} - R$ {income.amount.toFixed(2)}</p>
                  </div>

                  {/* Botão delete */}
                  <button
                    onClick={() => handleDeleteIncome(index)}
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
