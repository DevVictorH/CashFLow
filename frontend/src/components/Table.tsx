type Transaction = {
  name: string;
  category: string;
  amount: string;
  status: string;
};

const data: Transaction[] = [
  {
    name: "Netflix",
    category: "Subscription",
    amount: "-$50",
    status: "Paid",
  },
  {
    name: "Salary",
    category: "Income",
    amount: "+$3000",
    status: "Done",
  },
];

export default function Table() {
  return (
    <div className="bg-white p-4 rounded-2xl shadow-sm">
      <h2 className="mb-4 font-semibold">Recent Transactions</h2>

      <table className="w-full text-center">
        <thead>
          <tr className="text-gray-500">
            <th>Name</th>
            <th>Category</th>
            <th>Amount</th>
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