import { useEffect, useState } from "react";
import { getTransactions, type Transaction } from "../services/transactionService";

const formatCurrency = (value: number) =>
  value.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });

type TableProps = {
  showAll?: boolean;
  showDate?: boolean;
};

type SortKey = "name" | "createdAt" | "amount";
type SortDirection = "ascending" | "descending";

export default function Table({ showAll = false, showDate = false }: TableProps) {
  const [transactions, setTransactions] = useState<Transaction[]>([]);
  const [sortKey, setSortKey] = useState<SortKey | null>(null);
  const [sortDirection, setSortDirection] = useState<SortDirection>("ascending");

  useEffect(() => {
    getTransactions().then(setTransactions).catch(() => setTransactions([]));
  }, []);

  const handleSort = (key: SortKey) => {
    if (sortKey === key) {
      setSortDirection((direction) => direction === "ascending" ? "descending" : "ascending");
    } else {
      setSortKey(key);
      setSortDirection("ascending");
    }
  };

  const displayedTransactions = showAll ? transactions : transactions.slice(0, 5);
  const sortedTransactions = [...displayedTransactions].sort((first, second) => {
    if (!sortKey) return 0;

    let comparison = 0;
    if (sortKey === "name") {
      comparison = first.name.localeCompare(second.name, "pt-BR", { sensitivity: "base" });
    } else if (sortKey === "amount") {
      comparison = first.amount - second.amount;
    } else {
      const firstDate = first.createdAt ? new Date(first.createdAt).getTime() : 0;
      const secondDate = second.createdAt ? new Date(second.createdAt).getTime() : 0;
      comparison = firstDate - secondDate;
    }

    return sortDirection === "ascending" ? comparison : -comparison;
  });

  const sortLabel = (label: string, key: SortKey) => (
    <button
      type="button"
      onClick={() => handleSort(key)}
      className="cursor-pointer px-2 py-1 font-semibold hover:text-indigo-600 focus:outline-none focus:ring-2 focus:ring-indigo-500"
    >
      {label}{sortKey === key ? (sortDirection === "ascending" ? " ↑" : " ↓") : ""}
    </button>
  );

  return (
    <div className="bg-white p-4 rounded-2xl shadow-sm">
      <h2 className="mb-4 font-semibold">{showAll ? "Histórico de transações" : "Transações recentes"}</h2>

      <table className="w-full text-center">
        <thead>
          <tr className="text-gray-500">
            <th aria-sort={sortKey === "name" ? sortDirection : "none"}>{sortLabel("Nome", "name")}</th>
            <th>Categoria</th>
            {showDate && <th aria-sort={sortKey === "createdAt" ? sortDirection : "none"}>{sortLabel("Data", "createdAt")}</th>}
            <th aria-sort={sortKey === "amount" ? sortDirection : "none"}>{sortLabel("Quantia", "amount")}</th>
          </tr>
        </thead>

        <tbody>
          {transactions.length === 0 ? (
            <tr>
              <td colSpan={showDate ? 4 : 3} className="border-t py-4 text-gray-500">
                Nenhuma transação registrada.
              </td>
            </tr>
          ) : (
            sortedTransactions.map((item) => (
              <tr key={`${item.type}-${item.id}`} className="border-t">
                <td>{item.name}</td>
                <td>{item.category}</td>
                {showDate && (
                  <td>{item.createdAt ? new Date(item.createdAt).toLocaleDateString("pt-BR") : "-"}</td>
                )}
                <td >
                  {item.type === "income" ? "+" : "-"}{formatCurrency(item.amount)}
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}