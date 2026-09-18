import Sidebar from "../components/Sidebar";
import Header from "../components/Header";
import AddCategoryModal from "../components/AddCategoryModal";
import { useEffect, useState } from "react";
import { createCategory, deleteCategory, getCategories, type Category, type CategoryType } from "../services/categoryService";

export default function Categories() {
  const [categories, setCategories] = useState<Category[]>([]);
  const [openModal, setOpenModal] = useState(false);

  useEffect(() => {
    getCategories().then(setCategories).catch(() => setCategories([]));
  }, []);

  const handleAddCategory = async (category: { name: string; type: CategoryType }) => {
    await createCategory(category.name, category.type);
    setCategories(await getCategories());
    setOpenModal(false);
  };

  const handleDeleteCategory = async (categoryId: number) => {
    await deleteCategory(categoryId);
    setCategories((current) => current.filter((category) => category.id !== categoryId));
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
          <h1 className="text-2xl font-bold">Todas categorias</h1>

          <button
            onClick={() => setOpenModal(true)}
            className="bg-green-100 text-green-700 px-4 py-2 rounded-lg font-medium hover:bg-green-200"
          >
            + Add Categoria
          </button>
        </div>

        {/* Card */}
        <div className="bg-white rounded-2xl shadow p-6">
          <h2 className="font-semibold mb-4">Category Sources</h2>

          {categories.length === 0 ? (
            <p className="text-gray-500">
              Sem categorias. Adicione alguma para começar!
            </p>
          ) : (
            <div className="space-y-3">
              {categories.map((cat) => (
                <div
                  key={cat.id}
                  className="flex items-center justify-between bg-gray-50 p-4 rounded-xl hover:bg-gray-100"
                >
                  <div>
                    <p className="font-medium">{cat.name}</p>
                    <p className="text-sm text-gray-500">{cat.type === "INCOME" ? "Income" : "Expense"}</p>
                  </div>

                  {/* Botão delete */}
                  <button
                    onClick={() => void handleDeleteCategory(cat.id)}
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
        <AddCategoryModal
          onClose={() => setOpenModal(false)}
          onAdd={handleAddCategory}
        />
      )}
    </div>
  );
}