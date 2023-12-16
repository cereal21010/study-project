import {useQuery} from "react-query";
import {getTodoList} from "@/apis/todo/api";

export const useTodoList = () => {
  return useQuery('GET_TODO_LIST', getTodoList);
}

