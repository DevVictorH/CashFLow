import { useEffect, useMemo, useState } from "react";
import { getTransactions, type Transaction } from "../services/transactionService";

const formatCurrency = (value: number) =>
  value.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });

type TableProps = {
  showAll?: boolean;
  showDate?: boolean;
  pageSize?: number;
};

type SortKey = "name" | "createdAt" | "amount";
type SortDirection = "ascending" | "descending";

export default function Table({ showAll = false, showDate = false, pageSize = 5 }: TableProps) {
  const [transactions, setTransactions] = useState<Transaction[]>([]);
  const [sortKey, setSortKey] = useState<SortKey | null>(null);
  const [sortDirection, setSortDirection] = useState<SortDirection>("ascending");
  const [currentPage, setCurrentPage] = useState(1);

  useEffect(() => {
    getTransactions().then(setTransactions).catch(() => setTransactions([]));
  }, []);

  useEffect(() => {
    setCurrentPage(1);
  }, [showAll, pageSize]);

  const handleSort = (key: SortKey) => {
    if (sortKey === key) {
      setSortDirection((direction) => (direction === "ascending" ? "descending" : "ascending"));
    } else {
      setSortKey(key);
      setSortDirection("ascending");
    }
  };

  const sortedTransactions = useMemo(() => {
    const source = showAll ? [...transactions] : transactions.slice(0, 5);

    return source.sort((first, second) => {
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
  }, [transactions, showAll, sortDirection, sortKey]);

  const totalPages = showAll ? Math.max(1, Math.ceil(transactions.length / pageSize)) : 1;
  const paginatedTransactions = showAll
    ? sortedTransactions.slice((currentPage - 1) * pageSize, currentPage * pageSize)
    : sortedTransactions;

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
    <div className="rounded-2xl bg-white p-4 shadow-sm">
      <h2 className="mb-4 font-semibold">{showAll ? "Histórico de transações" : "Transações recentes"}</h2>

      <div className="table-scroll">
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
            paginatedTransactions.map((item) => (
              <tr key={`${item.type}-${item.id}`} className="border-t">
                <td>{item.name}</td>
                <td>{item.category}</td>
                {showDate && (
                  <td>{item.createdAt ? new Date(item.createdAt).toLocaleDateString("pt-BR") : "-"}</td>
                )}
                <td>
                  {item.type === "income" ? "+" : "-"}
                  {formatCurrency(item.amount)}
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
      </div>

      {showAll && transactions.length > 0 && (
        <div className="mt-4 flex items-center justify-between gap-4 border-t pt-4 text-sm text-gray-600">
          <span>
            Página {currentPage} de {totalPages}
          </span>

          <div className="flex gap-2">
            <button
              type="button"
              onClick={() => setCurrentPage((page) => Math.max(1, page - 1))}
              disabled={currentPage === 1}
              className="rounded-lg border px-3 py-1.5 disabled:cursor-not-allowed disabled:opacity-50"
            >
              Anterior
            </button>
            <button
              type="button"
              onClick={() => setCurrentPage((page) => Math.min(totalPages, page + 1))}
              disabled={currentPage === totalPages}
              className="rounded-lg border px-3 py-1.5 disabled:cursor-not-allowed disabled:opacity-50"
            >
              Próxima
            </button>
          </div>
        </div>
      )}
    </div>
  );
}