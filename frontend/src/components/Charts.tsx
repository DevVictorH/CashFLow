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
  Legend,
} from "recharts";
import type { FinancialRecord } from "../services/financialService";

type MonthlyData = {
  key: string;
  name: string;
  income: number;
  expenses: number;
};

type CategoryData = {
  name: string;
  value: number;
};

const COLORS = [
  "var(--chart-category-1)",
  "var(--chart-category-2)",
  "var(--chart-category-3)",
  "var(--chart-category-4)",
  "var(--chart-category-5)",
  "var(--chart-category-6)",
];
const AXIS_COLOR = "var(--text-muted)";
const AXIS_LINE_COLOR = "var(--border-color)";
const TOOLTIP_STYLE = {
  backgroundColor: "var(--bg-surface)",
  border: "1px solid var(--border-color)",
  borderRadius: "12px",
  color: "var(--text-primary)",
};
const formatCurrency = (value: number) =>
  value.toLocaleString("pt-BR", { style: "currency", currency: "BRL" });

function buildMonthlyData(incomes: FinancialRecord[], expenses: FinancialRecord[]): MonthlyData[] {
  const currentDate = new Date();
  const monthlyData = Array.from({ length: 6 }, (_, index) => {
    const monthDate = new Date(currentDate.getFullYear(), currentDate.getMonth() - 5 + index, 1);
    const key = `${monthDate.getFullYear()}-${String(monthDate.getMonth() + 1).padStart(2, "0")}`;

    return {
      key,
      name: monthDate.toLocaleDateString("pt-BR", { month: "short", year: "2-digit" }),
      income: 0,
      expenses: 0,
    };
  });
  const monthlyByKey = new Map(monthlyData.map((month) => [month.key, month]));
  const currentMonth = monthlyData[monthlyData.length - 1];

  for (const income of incomes) {
    const month = income.createdAt ? monthlyByKey.get(income.createdAt.slice(0, 7)) : undefined;
    if (month) month.income += income.amount;
    else if (!income.createdAt) currentMonth.income += income.amount;
  }

  for (const expense of expenses) {
    const month = expense.createdAt ? monthlyByKey.get(expense.createdAt.slice(0, 7)) : undefined;
    if (month) month.expenses += expense.amount;
    else if (!expense.createdAt) currentMonth.expenses += expense.amount;
  }

  return monthlyData;
}

function buildCategoryData(expenses: FinancialRecord[]): CategoryData[] {
  const totalsByCategory = new Map<string, number>();

  for (const expense of expenses) {
    const category = expense.categoryName ?? "Sem categoria";
    totalsByCategory.set(category, (totalsByCategory.get(category) ?? 0) + expense.amount);
  }

  return Array.from(totalsByCategory, ([name, value]) => ({ name, value }));
}

type ChartsProps = {
  incomes: FinancialRecord[];
  expenses: FinancialRecord[];
};

export default function Charts({ incomes, expenses }: ChartsProps) {
  const monthlyData = buildMonthlyData(incomes, expenses);
  const categoryData = buildCategoryData(expenses);

  return (
    <div className="grid grid-cols-1 xl:grid-cols-2 gap-4">
      <div className="bg-white p-4 rounded-2xl shadow-sm min-w-0">
        <h2 className="mb-2 font-semibold">Fluxo mensal</h2>
        <div className="h-64">
          <ResponsiveContainer width="100%" height="100%">
            <BarChart data={monthlyData}>
              <XAxis
                dataKey="name"
                tick={{ fill: AXIS_COLOR, fontSize: 12 }}
                axisLine={{ stroke: AXIS_LINE_COLOR }}
                tickLine={{ stroke: AXIS_LINE_COLOR }}
              />
              <YAxis
                tickFormatter={(value: number) => value.toLocaleString("pt-BR")}
                tick={{ fill: AXIS_COLOR, fontSize: 12 }}
                axisLine={{ stroke: AXIS_LINE_COLOR }}
                tickLine={{ stroke: AXIS_LINE_COLOR }}
              />
              <Tooltip
                formatter={(value) => formatCurrency(Number(value ?? 0))}
                contentStyle={TOOLTIP_STYLE}
              />
              <Legend />
              <Bar dataKey="income" name="Receitas" fill="var(--chart-income)" />
              <Bar dataKey="expenses" name="Despesas" fill="var(--chart-expenses)" />
            </BarChart>
          </ResponsiveContainer>
        </div>
      </div>

      <div className="bg-white p-4 rounded-2xl shadow-sm min-w-0">
        <h2 className="mb-2 font-semibold">Despesas por categoria</h2>
        <div className="h-64">
          {categoryData.length === 0 ? (
            <div className="flex h-full items-center justify-center text-sm text-gray-500">
              Nenhuma despesa cadastrada.
            </div>
          ) : (
            <ResponsiveContainer width="100%" height="100%">
              <PieChart>
                <Pie data={categoryData} dataKey="value" nameKey="name" outerRadius={80}>
                  {categoryData.map((category, index) => (
                    <Cell
                      key={category.name}
                      fill={COLORS[index % COLORS.length]}
                      stroke="var(--bg-surface)"
                    />
                  ))}
                </Pie>
                <Tooltip
                  formatter={(value) => formatCurrency(Number(value ?? 0))}
                  contentStyle={TOOLTIP_STYLE}
                />
                <Legend />
              </PieChart>
            </ResponsiveContainer>
          )}
        </div>
      </div>
    </div>
  );
}