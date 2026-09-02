import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { FiMoon, FiSun } from "react-icons/fi";

export default function Home() {
  const [darkMode, setDarkMode] = useState(false);

  useEffect(() => {
    const savedTheme = localStorage.getItem("cashflow-theme");
    const shouldUseDark = savedTheme === "dark";
    setDarkMode(shouldUseDark);
    document.body.classList.toggle("theme-dark", shouldUseDark);
  }, []);

  const handleToggleTheme = () => {
    const nextTheme = !darkMode;
    setDarkMode(nextTheme);
    localStorage.setItem("cashflow-theme", nextTheme ? "dark" : "light");
    document.body.classList.toggle("theme-dark", nextTheme);
  };

  return (
    <div className="min-h-screen bg-gray-50 text-gray-800 transition-colors duration-200 flex flex-col">
      <header className="flex items-center justify-between px-10 py-4 bg-white shadow-sm">
        <div className="flex items-center gap-2 font-bold text-lg">
          <span className="text-xl font-bold text-indigo-600">CashFlow</span>
        </div>

        <nav className="hidden md:flex gap-8 text-gray-600 font-medium">
          <a href="#">Home</a>
          <a href="#">Sobre</a>
          <a href="#">Contato</a>
        </nav>

        <div className="flex items-center gap-4">
          <button
            type="button"
            aria-label="Toggle theme"
            className="theme-toggle"
            onClick={handleToggleTheme}
          >
            {darkMode ? <FiSun /> : <FiMoon />}
          </button>

          <Link to="/login">
            <button className="text-gray-600 hover:text-black">Login</button>
          </Link>
          <Link to="/register">
            <button className="bg-purple-600 text-white px-5 py-2 rounded-lg shadow hover:bg-purple-700 transition">
              Comece agora
            </button>
          </Link>
        </div>
      </header>

      <main className="flex-1 flex flex-col items-center justify-center text-center px-6 py-24">
        <h1 className="text-5xl md:text-6xl font-bold mb-6">
          Tenha o controle das suas finanças
        </h1>

        <p className="max-w-2xl text-gray-500 text-lg mb-10">
          Sua base para uma gestão financeira segura e inteligente. Acompanhe
          suas receitas e despesas sem esforço para alcançar seus objetivos
          financeiros.
        </p>

        <div className="flex gap-4">
          <Link to="/register">
            <button className="bg-purple-600 text-white px-8 py-3 rounded-xl shadow-md hover:bg-purple-700 transition">
              Comece agora de graça
            </button>
          </Link>

          <button className="bg-gray-200 text-gray-700 px-8 py-3 rounded-xl hover:bg-gray-300 transition">
            Leia mais →
          </button>
        </div>
      </main>

      <footer className="bg-white py-4 px-6 shadow-sm mt-auto">
        <div className="text-center text-sm text-gray-600">
          © 2026 CashFlow. Todos os direitos reservados.
        </div>
      </footer>
    </div>
  );
}
