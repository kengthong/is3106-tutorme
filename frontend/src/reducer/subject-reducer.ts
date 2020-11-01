import {GET_SUBJECT_SUCCESSFUL } from "../config/constants";

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
            // console.log(action.payload)
            return {
                ...state,
                uniqueSubjects: action.payload.uniqueSubjects,
                subjects: action.payload.subjects
            };

        default:
            return state;
    }
};