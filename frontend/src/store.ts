import { combineReducers, createStore } from 'redux'
import { userReducer } from "./reducer/user-reducer";
import {subjectReducer} from "./reducer/subject-reducer";

export const store = createStore(combineReducers({
    userReducer,
    subjectReducer
}));