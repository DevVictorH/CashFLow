import { useEffect, useState } from "react";
import { Navigate, Outlet } from "react-router-dom";
import { getProfile } from "../services/authService";

export default function ProtectedRoute() {
  const token = localStorage.getItem("token");
  const [isAuthenticated, setIsAuthenticated] = useState<boolean | null>(
    token ? null : false,
  );

  useEffect(() => {
    if (!token) {
      return;
    }

    getProfile()
      .then(() => setIsAuthenticated(true))
      .catch(() => {
        localStorage.removeItem("token");
        localStorage.removeItem("userName");
        setIsAuthenticated(false);
      });
  }, [token]);

  if (isAuthenticated === null) {
    return null;
  }

  return isAuthenticated ? <Outlet /> : <Navigate to="/" replace />;
}