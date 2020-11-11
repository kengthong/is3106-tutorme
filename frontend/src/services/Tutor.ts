import { BACKEND_BASE_URL } from "../config/constants";
import { Utility } from "../config/Utility";

export class TutorService {
    static async getTutorDetails(userId: number): Promise<tutorDataType | null> {

        const url = BACKEND_BASE_URL + '/tutor/get/' + userId;
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };

        const response = await Utility.fetchBuilder(url, 'GET', header, null);
        if (response.ok) {
            const data = await response.json();
            console.log('data =', data)
            return data;
            // return await response.json();
        } else {
            return null;
        }
    }

    static async updateTutorDetails(userId: number, user: tutorDataType) {
        const url = BACKEND_BASE_URL + '/tutor/update/' + userId;
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };

        const response = await Utility.fetchBuilder(url, 'PUT', header, user);
        if (response.ok) {
            const data = await response.json();
            return true;
        } else {
            return false;
        }
    }

    static async getAllTutors() {
        const url = BACKEND_BASE_URL + '/tutor/getAll';
        const jsonHeader = Utility.getJsonHeader();
        const token = localStorage.getItem("token");
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };
        const response = await Utility.fetchBuilder(url, 'GET', header, null);
        if (response.ok) {
            return await response.json();
        } else {
            return false;
        }
    }
};