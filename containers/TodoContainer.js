"use client"

import TodoAdd from "@/components/TodoAdd";
import TodoList from "@/components/TodoList";
import {useEffect, useState} from "react";
import {deleteTodo, getTodoList, insertTodo, updateTodo} from "@/apis/todo/api";
import moment from "moment/moment";

const TodoContainer = () => {

  const [todoList, setTodoList] = useState([]);

  useEffect(() => {
    loadTodoList();
  }, []);

  const loadTodoList = async () => {
    const loadData = await getTodoList();
    setTodoList(loadData);
  }

  /**
   * todoList 추가
   * @param todoInfo
   */
  const handleAddTodo = async (todoInfo) => {
    const insertedTodoList = await insertTodo(todoInfo);
    insertedTodoList && loadTodoList();
    return !!insertedTodoList;
  }

  /**
   * todoList 삭제
   * @param id
   */
  const handleDeleteTodo = async (id) => {
    const result = await deleteTodo(id);
    result && loadTodoList();
  }

  const handleUpdateTodo = async (todo) => {
    const result = await updateTodo(todo);
    result && loadTodoList();
    return !!result;
  }


  return(
    <>
      <TodoAdd onAddTodo={handleAddTodo} />
      <TodoList todoList={todoList} onDeleteTodo={handleDeleteTodo} onUpdateTodo={handleUpdateTodo} />
    </>
  )
}

export default TodoContainer;
