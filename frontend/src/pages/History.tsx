import Header from "../components/Header";
import Sidebar from "../components/Sidebar";
import Table from "../components/Table";

export default function History() {
  return (
    <div className="flex min-h-screen bg-gray-100">
      <Sidebar />

      <main className="flex flex-1 flex-col gap-6 p-6">
        <Header />
        <h1 className="text-2xl font-bold">Histórico</h1>
        <Table showAll showDate pageSize={10} />
      </main>
    </div>
  );
}