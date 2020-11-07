import {BACKEND_BASE_URL} from "../config/constants";
import {Utility} from "../config/Utility";

export class TutorService {
    static async getTutorDetails(userId: number): Promise<tutorDataType | null> {

        const url = BACKEND_BASE_URL + '/tutor/' + userId;
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };

        const response = await Utility.fetchBuilder(url, 'GET', header, null);
        if(response.ok) {
            const data = await response.json();
            console.log('data = ', data);
            return data;
            // return await response.json();
            // console.log('data = ', data)
        } else {
            return null;
        }
    }
};