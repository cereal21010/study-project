"use Client"

import {useEffect, useState} from "react";
import ViewRow from "@/components/parts/ViewRow";
import EditRow from "@/components/parts/EditRow";

const TodoRow = ({todo, isEditMode, setEditId, onDeleteTodo, onUpdateTodo}) => {

  const clickUpdateTodo = (title) => {
    onUpdateTodo({
      ...todo,
      title,
    });
    setEditId(0);
  }

  const ClickEditMode = () => {
    setEditId(todo.id);
  }

  const onViewMode = () => {
    setEditId(0)
  }

  return (
    <div className="flex items-center">
      { isEditMode
        ? <EditRow todoTitle={todo.title} onUpdateTodo={clickUpdateTodo} onViewMode={onViewMode} />
        : <ViewRow todo={todo} onDeleteTodo={onDeleteTodo} onEditMode={ClickEditMode} />
      }
    </div>
  )

}

export default TodoRow;
