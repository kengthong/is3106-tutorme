import { createStore } from 'redux'
import userReducer from "./reducer/user-reducer";

export const store = createStore(userReducer);