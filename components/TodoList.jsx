"use client"

import {useMemo, useState} from "react";
import TodoRow from "@/components/TodoRow";
import editRow from "@/components/parts/EditRow";

const TodoList = ({todoList, onDeleteTodo, onUpdateTodo}) => {

  const [editId, setEditId] = useState(0);

  // useMemo
  const todoRows = useMemo(() => {
    if (!todoList || todoList.length <= 0) {
      return []
    }

    return todoList.map(todo => {
      return (
        <li key={todo.id} className="py-4">
          <TodoRow
            todo={todo}
            isEditMode={todo.id === editId}
            setEditId={setEditId}
            onDeleteTodo={onDeleteTodo}
            onUpdateTodo={onUpdateTodo}
          />
        </li>
      );
    });

  }, [todoList, editId]);

  return (
    <ul className="divide-y divide-gray-200 px-4">
      {...todoRows}
    </ul>
  )
}

export default TodoList;
