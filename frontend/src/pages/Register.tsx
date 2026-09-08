import { useState } from "react";
import type { FormEvent } from "react";
import axios from "axios";
import { toast } from "react-toastify";
import { Link, useNavigate } from "react-router-dom";
import { register } from "../services/authService";

export default function Register() {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role] = useState<"USER" | "ADMIN">("USER");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const navigate = useNavigate();

  const handleRegister = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setIsSubmitting(true);

    try {
      await register({ name, email, password, role });
      toast.success("Conta criada com sucesso");
      navigate("/login");
    } catch (error: unknown) {
      const message = axios.isAxiosError<{ message?: string }>(error)
        ? error.response?.data?.message ?? "Não foi possível criar a conta"
        : "Não foi possível criar a conta";
      toast.error(message);
    } finally {
      setIsSubmitting(false);
    }
  };

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

          <form onSubmit={handleRegister} className="space-y-4">

            <input
              type="text"
              placeholder="Nome"
              value={name}
              onChange={(event) => setName(event.target.value)}
              required
              className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500" />

            <input
              type="email"
              placeholder="Email"
              value={email}
              onChange={(event) => setEmail(event.target.value)}
              required
              className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500" />

            <input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(event) => setPassword(event.target.value)}
              required
              className="w-full p-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500" />

            <button
              type="submit"
              disabled={isSubmitting}
              className="w-full bg-purple-600 text-white py-3 rounded-lg hover:bg-purple-700 transition disabled:opacity-50">
              {isSubmitting ? "Criando..." : "Criar"}
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