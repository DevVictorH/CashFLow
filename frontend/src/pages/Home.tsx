import { Link } from "react-router-dom";
import { HeaderHome } from "../components/HeaderHome";
import { FooterHome } from "../components/FooterHome";

export default function Home() {
  

  return (
    <div className="home-page min-h-screen bg-gray-50 text-gray-800 transition-colors duration-200 flex flex-col">
      <HeaderHome />
      <main className="home-hero relative flex flex-1 flex-col items-center justify-center overflow-hidden px-6 py-24 text-center">
        <div className="home-hero__art" aria-hidden="true">
          <div className="home-hero__orbit" />
          <div className="home-hero__spark home-hero__spark--one" />
          <div className="home-hero__spark home-hero__spark--two" />

          <div className="home-hero__chart-card">
            <div className="home-hero__card-heading">
              <span className="home-hero__card-icon">↗</span>
              <span>Visão financeira</span>
            </div>
            <svg viewBox="0 0 180 58" role="presentation">
              <defs>
                <linearGradient id="hero-chart-gradient" x1="0" x2="1" y1="0" y2="0">
                  <stop offset="0%" stopColor="#c4b5fd" />
                  <stop offset="100%" stopColor="#7c3aed" />
                </linearGradient>
              </defs>
              <path
                d="M3 47 C22 42 24 34 43 38 S65 45 83 28 S109 34 127 19 S155 22 177 5"
                fill="none"
                stroke="url(#hero-chart-gradient)"
                strokeLinecap="round"
                strokeWidth="3"
              />
              <path
                d="M3 47 C22 42 24 34 43 38 S65 45 83 28 S109 34 127 19 S155 22 177 5 V58 H3 Z"
                fill="url(#hero-chart-gradient)"
                opacity=".12"
              />
              <circle cx="127" cy="19" r="4" fill="#8b5cf6" />
            </svg>
            <div className="home-hero__chart-labels">
              <span>Organização</span>
              <span>Planejamento</span>
            </div>
          </div>

          <div className="home-hero__bars-card">
            <span className="home-hero__bars-label">Seu dinheiro, em equilíbrio</span>
            <div className="home-hero__bars">
              <i />
              <i />
              <i />
              <i />
              <i />
              <i />
              <i />
            </div>
          </div>
        </div>

        <div className="home-hero__content relative z-10">
          <h1 className="mb-6 text-4xl font-bold sm:text-5xl md:text-6xl">
            Tenha o controle das suas finanças
          </h1>

          <p className="mb-10 max-w-2xl text-lg text-gray-500">
            Sua base para uma gestão financeira segura e inteligente. Acompanhe
            suas receitas e despesas sem esforço para alcançar seus objetivos
            financeiros.
          </p>

          <div className="flex flex-col items-stretch justify-center gap-3 sm:flex-row sm:items-center sm:gap-4">
            <Link to="/register">
              <button className="rounded-xl bg-purple-600 px-8 py-3 text-white shadow-md transition hover:bg-purple-700">
                Comece agora de graça
              </button>
            </Link>

            <Link
              to="/about"
              className="rounded-xl bg-gray-200 px-8 py-3 text-gray-700 transition hover:bg-gray-300"
            >
              Leia mais →
            </Link>
          </div>
        </div>
      </main>

    <FooterHome />
    </div>
  );
}
