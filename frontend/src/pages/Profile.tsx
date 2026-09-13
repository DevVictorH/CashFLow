import { useEffect, useState } from "react";
import type { FormEvent } from "react";
import { FiSave } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import Header from "../components/Header";
import Sidebar from "../components/Sidebar";
import { getProfile, updateProfile } from "../services/authService";

export default function Profile() {
  const navigate = useNavigate();
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [isLoading, setIsLoading] = useState(true);
  const [isSaving, setIsSaving] = useState(false);

  useEffect(() => {
    const loadProfile = async () => {
      try {
        const profile = await getProfile();
        setName(profile.name);
        setEmail(profile.email);
      } catch {
        toast.error("Não foi possível carregar seus dados");
      } finally {
        setIsLoading(false);
      }
    };

    void loadProfile();
  }, []);

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setIsSaving(true);

    try {
      const profile = await updateProfile({ name, email, password });
      localStorage.setItem("userName", profile.name);
      setPassword("");
      toast.success("Dados atualizados com sucesso");
    } catch {
      toast.error("Não foi possível atualizar seus dados");
    } finally {
      setIsSaving(false);
    }
  };

  return (
    <div className="flex min-h-screen bg-gray-100">
      <Sidebar />

      <main className="flex flex-1 flex-col gap-6 p-6">
        <Header />

        <div className="flex items-center gap-3">
          <button
            type="button"
            onClick={() => navigate(-1)}
            title="Voltar"
            aria-label="Voltar"
            className="rounded-lg p-2 text-gray-600 transition hover:bg-white hover:text-indigo-600"
          >
           
          </button>
          <div>
            <h1 className="text-2xl font-bold text-gray-900">Meu perfil</h1>
            <p className="text-sm text-gray-500">Atualize seus dados de acesso</p>
          </div>
        </div>

        <section className="max-w-2xl rounded-2xl bg-white p-6 shadow-sm">
          <form onSubmit={handleSubmit} className="space-y-5">
            <div>
              <label htmlFor="profile-name" className="mb-1 block text-sm font-medium text-gray-700">
                Nome
              </label>
              <input
                id="profile-name"
                type="text"
                value={name}
                onChange={(event) => setName(event.target.value)}
                required
                disabled={isLoading || isSaving}
                className="w-full rounded-lg border border-gray-300 p-3 outline-none transition focus:border-indigo-500 focus:ring-2 focus:ring-indigo-100 disabled:bg-gray-100"
              />
            </div>

            <div>
              <label htmlFor="profile-email" className="mb-1 block text-sm font-medium text-gray-700">
                E-mail
              </label>
              <input
                id="profile-email"
                type="email"
                value={email}
                onChange={(event) => setEmail(event.target.value)}
                required
                disabled={isLoading || isSaving}
                className="w-full rounded-lg border border-gray-300 p-3 outline-none transition focus:border-indigo-500 focus:ring-2 focus:ring-indigo-100 disabled:bg-gray-100"
              />
            </div>

            <div>
              <label htmlFor="profile-password" className="mb-1 block text-sm font-medium text-gray-700">
                Nova senha
              </label>
              <input
                id="profile-password"
                type="password"
                value={password}
                onChange={(event) => setPassword(event.target.value)}
                placeholder="Deixe em branco para manter a senha atual"
                minLength={3}
                disabled={isLoading || isSaving}
                className="w-full rounded-lg border border-gray-300 p-3 outline-none transition focus:border-indigo-500 focus:ring-2 focus:ring-indigo-100 disabled:bg-gray-100"
              />
            </div>

            <button
              type="submit"
              disabled={isLoading || isSaving}
              className="inline-flex items-center gap-2 rounded-lg bg-indigo-600 px-4 py-3 font-medium text-white transition hover:bg-indigo-700 disabled:cursor-not-allowed disabled:opacity-60"
            >
              <FiSave size={18} />
              {isSaving ? "Salvando..." : "Salvar alterações"}
            </button>
          </form>
        </section>
      </main>
    </div>
  );
}
