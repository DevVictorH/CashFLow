import { useNavigate } from "react-router-dom";
import { FiLogOut, FiUser } from "react-icons/fi";

export default function Header() {
  const navigate = useNavigate();
  const userName = localStorage.getItem("userName") ?? "Usuário";

  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("userName");
    navigate("/", { replace: true });
  };

  return (
    <div className="flex justify-between items-center bg-white p-4 shadow-sm rounded-xl">
      <input
        type="text"
        placeholder="Pesquisar..."
        className="border p-2 rounded-lg w-1/3"
      />

      <div className="flex items-center gap-4">

        <div className="flex items-center gap-2">
          <button
            type="button"
            onClick={() => navigate("/profile")}
            title="Meu perfil"
            aria-label="Abrir meu perfil"
            className="rounded-full focus:outline-none focus:ring-2 focus:ring-indigo-500"
          >
            <FiUser className="h-10 w-10 rounded-full bg-gray-100 p-2 text-gray-600 hover:bg-indigo-100" />
          </button>
          <span className="font-medium">{userName}</span>
          <button
            type="button"
            onClick={handleLogout}
            title="Sair"
            aria-label="Sair da conta"
            className="p-2 text-gray-600 hover:text-red-600 transition"
          >
            <FiLogOut size={20} />
          </button>
        </div>
      </div>
    </div>
  );
}