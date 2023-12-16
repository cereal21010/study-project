"use client"

import {useEffect, useRef} from "react";

const EditRow = ({todoTitle, onUpdateTodo, onViewMode}) => {

  const titleRef = useRef(null);

  useEffect(() => {
    titleRef.current.focus();
  }, []);

  const clickUpdateTodo = () => {
    onUpdateTodo(titleRef.current.value)
  }

  return (
    <>
      <input
        className="w-full py-3 mr-3 border border-slate-200 rounded-lg px-3 focus:outline-none focus:border-slate-500 hover:shadow"
        defaultValue={todoTitle}
        ref={titleRef}
        type="text"/>
      <button
        className="flex-shrink-0 bg-teal-500 hover:bg-teal-700 border-teal-500 hover:border-teal-700 text-sm border-4 text-white py-1 px-2 mr-2 rounded"
        type="button"
        onClick={clickUpdateTodo}
      >
        Save
      </button>
      <button
        className="flex-shrink-0 bg-amber-500 hover:bg-amber-700 border-amber-500 hover:border-amber-700 text-sm border-4 text-white py-1 px-2 rounded"
        onClick={onViewMode}
      >
        Cancel
      </button>
    </>
  );
}

export default EditRow;
