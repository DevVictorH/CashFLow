import { Link } from "react-router-dom";
import { HeaderHome } from "../components/HeaderHome";
import { FooterHome } from "../components/FooterHome";

export default function Home() {
  

  return (
    <div className="home-page min-h-screen bg-gray-50 text-gray-800 transition-colors duration-200 flex flex-col">
      <HeaderHome />
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

          <Link
            to="/about"
            className="bg-gray-200 text-gray-700 px-8 py-3 rounded-xl hover:bg-gray-300 transition"
          >
            Leia mais →
          </Link>
        </div>
      </main>

    <FooterHome />
    </div>
  );
}
