export default function Header() {
  return (
    <div className="flex justify-between items-center bg-white p-4 shadow-sm rounded-xl">
      <input
        type="text"
        placeholder="Pesquisar..."
        className="border p-2 rounded-lg w-1/3"
      />

      <div className="flex items-center gap-4">
        <span>🔔</span>

        <div className="flex items-center gap-2">
          <img
            src="https://i.pravatar.cc/40"
            className="rounded-full"
            alt="User"
          />
          <span className="font-medium">Victor</span>
        </div>
      </div>
    </div>
  );
}