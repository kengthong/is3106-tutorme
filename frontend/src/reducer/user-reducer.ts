import {AUTHENTICATION_ERROR, LOGIN_SUCCESSFUL, LOGOUT_SUCCESSFUL} from "../config/constants";

export interface UserState {
    currentUser?: {
        personId: string;
    },
    isAuthenticated: boolean,
    error?: boolean,
    errorMsg?: string,
}
const defaultState: UserState = {
    currentUser: undefined,
    isAuthenticated: false,
    error: false,
    errorMsg: "",
};
type Action = {type: string, payload: any }
export const userReducer = (state: UserState = defaultState, action: Action) => {
    switch(action.type) {
        case LOGIN_SUCCESSFUL:
            return {
                ...state,
                error: false,
                errorMsg: "",
                isAuthenticated: true,
                currentUser: action.payload
            };
        case AUTHENTICATION_ERROR:
            return {
                ...state,
                error: true,
                isAuthenticated: false,
                errorMsg: JSON.parse(action.payload).error
            }

        case LOGOUT_SUCCESSFUL:
            return {
                error: false,
                errorMsg: undefined,
                isAuthenticated: false,
                currentUser: undefined
            }

        default:
            return state;
    }
}