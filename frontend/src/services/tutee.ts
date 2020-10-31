// interface TuteeServiceInterface {
//     getJobListingListWithParams(params: getJobListingListWithParamsProps): getJobListingListWithParamResposeProps;
// }
export default class TuteeService {
    static async getJobListingListWithParams(params: getJobListingListWithParamsProps): Promise<void> {
        console.log("getJobListingListWithParams:", params);
    }
};