"use client"

import {useEffect, useState} from "react";
import {updateTodoCheck} from "@/apis/todo/api";

const ViewRow = ({todo, onDeleteTodo, onEditMode}) => {

  const [isChecked, setIsChecked] = useState(false);

  /**
   * isChecked 초기값 세팅
   */
  useEffect(() => {
    if (!!todo) {
      setIsChecked(todo.isCompleted);
    }
  }, [todo]);

  const checkTodo = async (e) => {
    // update todoCheck
    const updatedTodo = await updateTodoCheck(todo.id, e.target.checked);
    setIsChecked(updatedTodo.isCompleted);
  }

  return (
    <>
      <input id="todo1" name="todo1" type="checkbox"
             className="h-4 w-4 text-teal-600 focus:ring-teal-500 border-gray-300 rounded"
             checked={isChecked}
             onChange={checkTodo}
      />
      <label htmlFor="todo1" className="ml-3 block text-gray-900 w-full">
        <span className={`text-lg font-medium ${!isChecked || 'line-through text-gray-400'}`}>{todo.title}</span>
      </label>
      <button
        className="border border-green-500 bg-green-500 text-white rounded-md px-2 py-2 m-2 transition duration-500 ease select-none hover:bg-green-600 focus:outline-none focus:shadow-outline"
        onClick={onEditMode}
      >
        Edit
      </button>
      <button
        className="border border-red-500 bg-red-500 text-white rounded-md px-2 py-2 m-2 transition duration-500 ease select-none hover:bg-red-600 focus:outline-none focus:shadow-outline"
        onClick={() => onDeleteTodo(todo.id)}
      >
        Delete
      </button>
    </>
  );

}

export default ViewRow;
