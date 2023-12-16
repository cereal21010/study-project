"use client"

import {useState} from "react";
import moment from "moment";

const TodoAdd = ({onAddTodo}) => {

  const [inputTitle, setInputTitle] = useState('');

  const onInputTitle = (e) => {
    setInputTitle(e.target.value);
  }

  const clickAddTodo = () => {
    const isComplete = onAddTodo({
      title: inputTitle,
      createdDate: moment().format('YYYYMMDD'),
      isCompleted: false,
    });
    isComplete && setInputTitle('');
  }

  return (
    // <form className="w-full max-w-sm mx-auto px-4 py-2"></form> Todo: Form 테그 기능 확인해 보기
      <div className="w-full max-w-sm mx-auto px-4 flex items-center border-b-2 border-teal-500 py-2">
        <input
          className="appearance-none bg-transparent border-none w-full text-gray-700 mr-3 py-1 px-2 leading-tight focus:outline-none"
          type="text"
          placeholder="Add a task"
          value={inputTitle}
          onInput={(e) => {setInputTitle(e.target.value)}}
        />
        <button
          className="flex-shrink-0 bg-teal-500 hover:bg-teal-700 border-teal-500 hover:border-teal-700 text-sm border-4 text-white py-1 px-2 rounded"
          type="button"
          onClick={clickAddTodo}
        >
          Add
        </button>
      </div>
  )
}

export default TodoAdd;
