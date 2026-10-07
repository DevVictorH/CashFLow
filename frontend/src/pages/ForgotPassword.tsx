import { useState } from "react";
import type { FormEvent } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import {
  requestPasswordRecovery,
  resetPassword,
  verifyRecoveryCode,
} from "../services/authService";

type RecoveryStep = "email" | "code" | "password";

const getErrorMessage = (error: unknown, fallback: string) => {
  if (axios.isAxiosError(error) && typeof error.response?.data === "string") {
    return error.response.data;
  }
  return fallback;
};

export default function ForgotPassword() {
  const [step, setStep] = useState<RecoveryStep>("email");
  const [email, setEmail] = useState("");
  const [code, setCode] = useState("");
  const [newPassword, setNewPassword] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const navigate = useNavigate();

  const handleRequestCode = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setIsSubmitting(true);
    try {
      await requestPasswordRecovery(email);
      setStep("code");
      toast.success("Enviamos um código de recuperação para seu e-mail.");
    } catch (error: unknown) {
      toast.error(getErrorMessage(error, "Não foi possível enviar o código."));
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleVerifyCode = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setIsSubmitting(true);
    try {
      await verifyRecoveryCode(email, code);
      setStep("password");
    } catch (error: unknown) {
      toast.error(getErrorMessage(error, "Código inválido ou expirado."));
    } finally {
      setIsSubmitting(false);
    }
  };

  const handleResetPassword = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setIsSubmitting(true);
    try {
      await resetPassword(email, code, newPassword);
      toast.success("Senha redefinida. Entre com sua nova senha.");
      navigate("/login");
    } catch (error: unknown) {
      toast.error(getErrorMessage(error, "Não foi possível redefinir sua senha."));
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <>
      <Link to="/">
        <header className="flex items-center justify-between bg-white px-10 py-4 shadow-sm">
          <span className="text-xl font-bold text-indigo-600">CashFlow</span>
        </header>
      </Link>
      <main className="flex min-h-[calc(100vh-64px)] items-center justify-center bg-gray-50 px-4 py-10">
        <section className="w-full max-w-md rounded-xl bg-white p-8 shadow-md">
          <h1 className="mb-2 text-center text-2xl font-bold text-gray-900">
            {step === "email" && "Esqueci minha senha"}
            {step === "code" && "Confira seu e-mail"}
            {step === "password" && "Crie uma nova senha"}
          </h1>
          <p className="mb-6 text-center text-sm text-gray-600">
            {step === "email" && "Informe o e-mail associado à sua conta."}
            {step === "code" && `Digite o código de recuperação enviado para ${email}.`}
            {step === "password" && "O código foi validado. Informe sua nova senha."}
          </p>

          {step === "email" && (
            <form onSubmit={handleRequestCode} className="space-y-4">
              <label className="block text-sm font-medium text-gray-700" htmlFor="recovery-email">
                E-mail
              </label>
              <input
                id="recovery-email"
                type="email"
                autoComplete="email"
                value={email}
                onChange={(event) => setEmail(event.target.value)}
                required
                className="w-full rounded-lg border border-gray-300 p-3 focus:border-purple-500 focus:outline-none focus:ring-2 focus:ring-purple-200"
              />
              <button
                type="submit"
                disabled={isSubmitting}
                className="w-full rounded-lg bg-purple-600 py-3 font-medium text-white transition hover:bg-purple-700 disabled:opacity-50"
              >
                {isSubmitting ? "Enviando..." : "Enviar código"}
              </button>
            </form>
          )}

          {step === "code" && (
            <form onSubmit={handleVerifyCode} className="space-y-4">
              <label className="block text-sm font-medium text-gray-700" htmlFor="recovery-code">
                Código de recuperação
              </label>
              <input
                id="recovery-code"
                type="text"
                inputMode="numeric"
                autoComplete="one-time-code"
                value={code}
                onChange={(event) => setCode(event.target.value.replace(/\D/g, "").slice(0, 6))}
                required
                minLength={6}
                maxLength={6}
                className="w-full rounded-lg border border-gray-300 p-3 tracking-[0.3em] focus:border-purple-500 focus:outline-none focus:ring-2 focus:ring-purple-200"
              />
              <button
                type="submit"
                disabled={isSubmitting || code.length !== 6}
                className="w-full rounded-lg bg-purple-600 py-3 font-medium text-white transition hover:bg-purple-700 disabled:opacity-50"
              >
                {isSubmitting ? "Verificando..." : "Confirmar código"}
              </button>
              <button
                type="button"
                disabled={isSubmitting}
                onClick={() => setStep("email")}
                className="w-full py-2 text-sm font-medium text-purple-700 hover:text-purple-800"
              >
                Usar outro e-mail
              </button>
            </form>
          )}

          {step === "password" && (
            <form onSubmit={handleResetPassword} className="space-y-4">
              <label className="block text-sm font-medium text-gray-700" htmlFor="new-password">
                Nova senha
              </label>
              <input
                id="new-password"
                type="password"
                autoComplete="new-password"
                value={newPassword}
                onChange={(event) => setNewPassword(event.target.value)}
                required
                className="w-full rounded-lg border border-gray-300 p-3 focus:border-purple-500 focus:outline-none focus:ring-2 focus:ring-purple-200"
              />
              <button
                type="submit"
                disabled={isSubmitting}
                className="w-full rounded-lg bg-purple-600 py-3 font-medium text-white transition hover:bg-purple-700 disabled:opacity-50"
              >
                {isSubmitting ? "Salvando..." : "Redefinir senha"}
              </button>
            </form>
          )}

          <p className="mt-5 text-center text-sm text-gray-600">
            <Link to="/login" className="font-medium text-purple-700 hover:text-purple-800">
              Voltar ao login
            </Link>
          </p>
        </section>
      </main>
    </>
  );
}