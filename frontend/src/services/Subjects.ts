import { Utility } from "../config/Utility";
import { BACKEND_BASE_URL, GET_SUBJECT_SUCCESSFUL } from "../config/constants";
import { SubjectState } from "../reducer/subject-reducer";
import { store } from "../store";

export class SubjectsService {
    public static async getAllSubjects(): Promise<void> {
        const url = BACKEND_BASE_URL + '/subject/subjectList';
        const jsonHeader = Utility.getJsonHeader();
        await Utility.fetchBuilder(url, 'GET', jsonHeader, null)
            .then(async res => {
                if (res.ok) {

                    const result: subjectResponseType = await res.json();
                    const uniqueSubjects = Array.from(new Set(result.map(sub => sub.subjectName))).sort();
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