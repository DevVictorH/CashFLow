import { useState } from "react";
import type { CategoryType } from "../services/categoryService";

interface Props {
  onClose: () => void;
  onAdd: (category: { name: string; type: CategoryType }) => Promise<void>;
}

export default function AddCategoryModal({ onClose, onAdd }: Props) {
  const [name, setName] = useState("");
  const [type, setType] = useState<CategoryType>("INCOME");

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!name) return;

    await onAdd({ name, type });
  };

  return (
    <div className="fixed inset-0 bg-black bg-opacity-30 flex items-center justify-center">
      
      <div className="bg-white rounded-2xl p-6 w-full max-w-lg shadow-lg relative">

        {/* Close */}
        <button
          onClick={onClose}
          className="absolute top-4 right-4 text-gray-400 hover:text-black"
        >
          ✕
        </button>

        <h2 className="text-xl font-bold mb-6">Add Categoria</h2>

        <form onSubmit={handleSubmit} className="space-y-4">

          {/* Name */}
          <input
            type="text"
            placeholder="Ex: Freelance, Salario, Compras"
            className="w-full p-3 border rounded-lg"
            onChange={(e) => setName(e.target.value)}
          />

          {/* Type */}
          <select
            className="w-full p-3 border rounded-lg"
            value={type}
            onChange={(e) => setType(e.target.value as CategoryType)}
          >
            <option value="INCOME">Receita</option>
            <option value="EXPENSE">Despesa</option>
          </select>

          {/* Button */}
          <div className="flex justify-end">
            <button className="bg-purple-600 text-white px-6 py-2 rounded-lg hover:bg-purple-700">
              Add Categoria
            </button>
          </div>

        </form>
      </div>
    </div>
  );
}