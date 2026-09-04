import { Link } from "react-router-dom";

export default function Register() {
  return (
        <><Link to="/">
      <header className="flex items-center justify-between px-10 py-4 bg-white shadow-sm">
        <div className="flex items-center gap-2 font-bold text-lg">
          <span className="text-xl font-bold text-indigo-600">CashFlow</span>
        </div>
      </header>
    </Link><div className="min-h-screen flex items-center justify-center bg-gray-50">

        <div className="bg-white p-8 rounded-2xl shadow-md w-full max-w-md">

          <h2 className="text-2xl font-bold mb-6 text-center">
            Criar conta
          </h2>

          <form className="space-y-4">

            <input
              type="text"
              placeholder="Nome"
              className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500" />

            <input
              type="email"
              placeholder="Email"
              className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500" />

            <input
              type="password"
              placeholder="Password"
              className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500" />

            <button className="w-full bg-purple-600 text-white py-3 rounded-lg hover:bg-purple-700 transition">
              Criar
            </button>

          </form>

          <p className="text-center text-gray-500 mt-4">
            Já possui uma conta?{" "}
            <Link to="/login" className="text-purple-600 font-medium">
              Login
            </Link>
          </p>

        </div>

      </div></>
  );
}