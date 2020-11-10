// interface TuteeServiceInterface {
//     getJobListingListWithParams(params: getJobListingListWithParamsProps): getJobListingListWithParamResposeProps;

import { BACKEND_BASE_URL } from "../config/constants";

// }
export default class TuteeService {
    static async getJobListingListWithParams(params: getJobListingListWithParamsProps): Promise<void> {
        console.log("getJobListingListWithParams:", params);
    }

    static makeOffer() {

    }

    //end point is /tutee/get
    

};