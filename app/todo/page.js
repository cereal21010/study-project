import TodoContainer from "@/containers/TodoContainer";

const Home = async () => {

  return (
    <main className="max-w-4xl mx-auto mt-4">
      <div className="max-w-md mx-auto bg-white shadow-lg rounded-lg overflow-hidden mt-16">
        <div className="px-4 py-2">
          <h1 className="text-gray-800 font-bold text-2xl uppercase">To-Do List</h1>
        </div>
        <TodoContainer />
      </div>
    </main>
  )
}

export default Home;
