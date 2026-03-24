import Sidebar from "../components/Sidebar";
import Header from "../components/Header";
import Card from "../components/Card";
import Table from "../components/Table";

export default function Dashboard() {
  return (
    <div className="flex bg-gray-100 min-h-screen">
      <Sidebar />

      <div className="flex-1 p-6 flex flex-col gap-6">
        <Header />

        <div className="grid grid-cols-3 gap-4">
          <Card title="Total Balance" value="$66,000" color="text-indigo-600" />
          <Card title="Income" value="$44,000" color="text-green-500" />
          <Card title="Expenses" value="$22,000" color="text-red-500" />
        </div>

       {/* <Charts /> */}

        <Table />
      </div>
    </div>
  );
}