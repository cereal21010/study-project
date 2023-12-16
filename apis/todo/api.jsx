import baseAxios from "@/apis";

/**
 * TodoList 조회
 * @returns {Promise<Response>}
 */
export const getTodoList = () => {
  // baseAxios('/todos') -> response: Promise {pending}
  return baseAxios.get('/todos').then(res => res.data);

}

export const insertTodo = async (todo) => {
  return baseAxios.post('/todos', todo).then(res => res.data);
}

export const deleteTodo = (id) => {
  return baseAxios.delete(`/todos/${id}`).then(res => res.status < 400);
}

export const updateTodo = (todo) => {
  return baseAxios.patch(`/todos/${todo.id}`, todo).then(res => res.data);
}

export const updateTodoCheck = (todoId, checked) => {
  return baseAxios.patch(`/todos/${todoId}`, { isCompleted: checked }).then(res => res.data);
}
