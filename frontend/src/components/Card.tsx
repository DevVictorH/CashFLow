type CardProps = {
  title: string;
  value: string;
  color: string;
};

export default function Card({ title, value, color }: CardProps) {
  return (
    <div className="bg-white p-4 rounded-2xl shadow-sm flex flex-col gap-2">
      <span className="text-gray-500">{title}</span>
      <h2 className={`text-2xl font-bold ${color}`}>{value}</h2>
    </div>
  );
}