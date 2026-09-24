import { FiMoon, FiSun } from "react-icons/fi";
import { Link } from "react-router-dom";
import { useTheme } from "./ChangeTheme";

export function HeaderHome() {
    const { darkMode, toggleTheme } = useTheme();

    return (
        <header className="flex items-center justify-between px-10 py-4 bg-white shadow-sm">
            <div className="flex items-center gap-2 font-bold text-lg">
                <span className="text-xl font-bold text-indigo-600">CashFlow</span>
            </div>

            <nav className="hidden md:flex gap-8 text-gray-600 font-medium">
                <Link to="/">Home</Link>
                <Link to="/about">Sobre</Link>
            </nav>

            <div className="flex items-center gap-4">
                <button
                    type="button"
                    aria-label="Toggle theme"
                    className="theme-toggle"
                    onClick={toggleTheme}
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
    )
        ;
}