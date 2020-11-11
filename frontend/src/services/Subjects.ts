import { Utility } from "../config/Utility";
import { BACKEND_BASE_URL, GET_SUBJECT_SUCCESSFUL } from "../config/constants";
import { SubjectState } from "../reducer/subject-reducer";
import { store } from "../store";

const jsonHeader = {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
};

export class SubjectsService {
    public static async getAllSubjects(): Promise<void> {
        const url = BACKEND_BASE_URL + '/subject/subjectList';
        await Utility.fetchBuilder(url, 'GET', jsonHeader, null)
            .then(async res => {
                if (res.ok) {

                    const result: subjectResponseType = await res.json();
                    // console.log(result);
                    const uniqueSubjects = Array.from(new Set(result.map(sub => sub.subjectName)));
                    const payload: SubjectState = {
                        uniqueSubjects,
                        subjects: result
                    };
                    store.dispatch({
                        type: GET_SUBJECT_SUCCESSFUL,
                        payload
                    })
                }
            })


    }
}