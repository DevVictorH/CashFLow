import { Link } from "react-router-dom";

export default function Sidebar() {
  const items = [
    { name: "Dashboard", path: "/dashboard" },
    { name: "Category", path: "/categories" },
    { name: "Income", path: "/income" },
    { name: "Expense", path: "/expense" },
    { name: "Filters", path: "/filters" },
  ];

  return (
    <div className="w-64 h-screen bg-gray-50 p-4 shadow-sm">
      <h1 className="text-xl font-bold mb-6 text-indigo-600">Cashflow</h1>

      <nav className="flex flex-col gap-3">
        {items.map((item, index) => (
          <Link
            key={index}
            to={item.path}
            className="p-2 rounded-lg hover:bg-indigo-100 text-gray-700"
          >
            {item.name}
          </Link>
        ))}
      </nav>
    </div>
  );
}