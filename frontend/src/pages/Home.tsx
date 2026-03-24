import React from "react";
import { Link } from "react-router-dom";

export default function Home() {
  return (
    <div className="min-h-screen bg-gray-50 text-gray-800">
      {/* Navbar */}
      <header className="flex items-center justify-between px-10 py-4 bg-white shadow-sm">
        {/* Logo */}
        <div className="flex items-center gap-2 font-bold text-lg">
          <span className="text-xl font-bold text-indigo-600">CashFlow</span>
        </div>

        {/* Menu */}
        <nav className="hidden md:flex gap-8 text-gray-600 font-medium">
          <a href="#">Home</a>
          <a href="#">Sobre</a>
          <a href="#">Contato</a>
        </nav>

        {/* Actions */}
        <div className="flex items-center gap-4">
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

      {/* Hero Section */}
      <main className="flex flex-col items-center justify-center text-center px-6 py-24">
        <h1 className="text-5xl md:text-6xl font-bold mb-6">
          Tenha o controle das suas finanças
        </h1>

        <p className="max-w-2xl text-gray-500 text-lg mb-10">
          Sua base para uma gestão financeira segura e inteligente. Acompanhe
          suas receitas e despesas sem esforço para alcançar seus objetivos
          financeiros.
        </p>

        {/* Buttons */}
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
    </div>
  );
}
