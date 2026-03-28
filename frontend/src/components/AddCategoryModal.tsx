import { useState } from "react";

interface Props {
  onClose: () => void;
  onAdd: (category: { name: string; type: string }) => void;
}

export default function AddCategoryModal({ onClose, onAdd }: Props) {
  const [name, setName] = useState("");
  const [type, setType] = useState("Income");

  const handleSubmit = (e: any) => {
    e.preventDefault();

    if (!name) return;

    onAdd({ name, type });
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

        <h2 className="text-xl font-bold mb-6">Add Category</h2>

        <form onSubmit={handleSubmit} className="space-y-4">

          {/* Icon placeholder */}
          <div className="flex items-center gap-2 text-purple-600 cursor-pointer">
            📷 <span>Pick Icon</span>
          </div>

          {/* Name */}
          <input
            type="text"
            placeholder="e.g., Freelance, Salary, Groceries"
            className="w-full p-3 border rounded-lg"
            onChange={(e) => setName(e.target.value)}
          />

          {/* Type */}
          <select
            className="w-full p-3 border rounded-lg"
            value={type}
            onChange={(e) => setType(e.target.value)}
          >
            <option>Income</option>
            <option>Expense</option>
          </select>

          {/* Button */}
          <div className="flex justify-end">
            <button className="bg-purple-600 text-white px-6 py-2 rounded-lg hover:bg-purple-700">
              Add Category
            </button>
          </div>

        </form>
      </div>
    </div>
  );
}