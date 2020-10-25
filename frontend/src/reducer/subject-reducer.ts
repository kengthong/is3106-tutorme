import {AUTHENTICATION_ERROR, GET_SUBJECT_SUCCESSFUL, LOGIN_SUCCESSFUL, LOGOUT_SUCCESSFUL} from "../config/constants";

export interface SubjectState {
    uniqueSubjects: string[],
    subjects: subjectResponseType
}
const defaultState: SubjectState = {
    uniqueSubjects: [],
    subjects: []
};

export const subjectReducer = (state: SubjectState = defaultState, action: Action) => {
    switch(action.type) {
        case GET_SUBJECT_SUCCESSFUL:
            console.log(action.payload)
            return {
                ...state,
                uniqueSubjects: action.payload.uniqueSubjects,
                subjects: action.payload.subjects
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
};