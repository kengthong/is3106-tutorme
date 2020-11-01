import {combineReducers, createStore, Store} from 'redux'
import {userReducer, UserState} from "./reducer/user-reducer";
import {subjectReducer, SubjectState} from "./reducer/subject-reducer";
import {persistReducer} from 'redux-persist';
import storage from 'redux-persist/lib/storage';

export interface IRootState {
    userReducer: UserState,
    subjectReducer: SubjectState
}

const persistConfig = {
    key: 'root',
    storage: storage,
};
const persistUserReducer = persistReducer(persistConfig, combineReducers({
    userReducer,
    subjectReducer
}));


export const store: Store<IRootState, any> = createStore(persistUserReducer);