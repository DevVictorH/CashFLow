import { FiUser } from "react-icons/fi";

export default function Header() {
  return (
    <div className="flex justify-between items-center bg-white p-4 shadow-sm rounded-xl">
      <input
        type="text"
        placeholder="Pesquisar..."
        className="border p-2 rounded-lg w-1/3"
      />

      <div className="flex items-center gap-4">

        <div className="flex items-center gap-2">
          <FiUser className="h-10 w-10 rounded-full bg-gray-100 p-2 text-gray-600" aria-label="Usuário" />
          <span className="font-medium">Victor</span>
        </div>
      </div>
    </div>
  );
}