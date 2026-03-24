import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  Tooltip,
  ResponsiveContainer,
  PieChart,
  Pie,
  Cell,
} from "recharts";

type BarData = {
  name: string;
  income: number;
  expenses: number;
};

type PieData = {
  name: string;
  value: number;
};

const barData: BarData[] = [
  { name: "Jan", income: 4000, expenses: 2400 },
  { name: "Feb", income: 3000, expenses: 1398 },
  { name: "Mar", income: 5000, expenses: 3800 },
];

const pieData: PieData[] = [
  { name: "Food", value: 400 },
  { name: "Shopping", value: 300 },
  { name: "Transport", value: 200 },
  { name: "Subscription", value: 100 },
];

const COLORS = ["#6366F1", "#3B82F6", "#22C55E", "#F59E0B"];

export default function Charts() {
  return (
    <div className="grid grid-cols-2 gap-4">
      
      {/* Bar Chart */}
      <div className="bg-white p-4 rounded-2xl shadow-sm h-64">
        <h2 className="mb-2 font-semibold">Cash Flow</h2>

        <ResponsiveContainer width="100%" height="100%">
          <BarChart data={barData}>
            <XAxis dataKey="name" />
            <YAxis />
            <Tooltip />
            <Bar dataKey="income" fill="#6366F1" />
            <Bar dataKey="expenses" fill="#3B82F6" />
          </BarChart>
        </ResponsiveContainer>
      </div>

      {/* Pie Chart */}
      <div className="bg-white p-4 rounded-2xl shadow-sm h-64">
        <h2 className="mb-2 font-semibold">Spending</h2>

        <ResponsiveContainer width="100%" height="100%">
          <PieChart>
            <Pie data={pieData} dataKey="value" outerRadius={80}>
              {pieData.map((_, index) => (
                <Cell key={index} fill={COLORS[index]} />
              ))}
            </Pie>
          </PieChart>
        </ResponsiveContainer>
      </div>

    </div>
  );
}