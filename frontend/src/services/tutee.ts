// interface TuteeServiceInterface {
//     getJobListingListWithParams(params: getJobListingListWithParamsProps): getJobListingListWithParamResposeProps;

import { BACKEND_BASE_URL } from "../config/constants";
import { Utility } from "../config/Utility";

// }
export default class TuteeService {
    static async getJobListingListWithParams(params: getJobListingListWithParamsProps): Promise<void> {
        console.log("getJobListingListWithParams:", params);
    }

    static makeOffer() {

    }

    static async getTuteeDetails(userId: number): Promise<tuteeDataType | null> {

        //Just need to call this backend API with token to get.
        const url = BACKEND_BASE_URL + '/tutee/get';
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };

        const response = await Utility.fetchBuilder(url, 'GET', header, null);
        if (response.ok) {
            const data = await response.json();
            return data;
            // return await response.json();
        } else {
            return null;
        }
    }

    static async updateTuteeDetails(userId: number, user: tuteeDataType) {
        const url = BACKEND_BASE_URL + '/tutee/update';
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

};