type Transaction = {
  name: string;
  category: string;
  amount: string;
};

const data: Transaction[] = [
  {
    name: "Netflix",
    category: "Inscrição",
    amount: "-R$50",
  },
  {
    name: "Salario",
    category: "Receita",
    amount: "+R$3000",
  },
];

export default function Table() {
  return (
    <div className="bg-white p-4 rounded-2xl shadow-sm">
      <h2 className="mb-4 font-semibold">Transações recentes</h2>

      <table className="w-full text-center">
        <thead>
          <tr className="text-gray-500">
            <th>Nome</th>
            <th>Categoria</th>
            <th>Quantia</th>
          </tr>
        </thead>

        <tbody>
          {data.map((item, index) => (
            <tr key={index} className="border-t">
              <td>{item.name}</td>
              <td>{item.category}</td>
              <td>{item.amount}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}