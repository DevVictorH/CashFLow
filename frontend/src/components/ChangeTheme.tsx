import { useEffect, useState } from "react";

export function useTheme() {
    const [darkMode, setDarkMode] = useState(
        () => localStorage.getItem("cashflow-theme") === "dark",
    );

    useEffect(() => {
        document.body.classList.toggle("theme-dark", darkMode);
    }, [darkMode]);

    const toggleTheme = () => {
        setDarkMode((currentDarkMode) => {
            const nextTheme = !currentDarkMode;
            localStorage.setItem("cashflow-theme", nextTheme ? "dark" : "light");
            return nextTheme;
        });
    };

    return { darkMode, toggleTheme };
}