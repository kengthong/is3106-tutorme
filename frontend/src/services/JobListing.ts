import {Utility} from "../config/Utility";
import {BACKEND_BASE_URL} from "../config/constants";

const jsonHeader= {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
};
export class JobListingService {
    static async getJobListingListWithParams(params: getJobListingListWithParamsProps): Promise<getJobListingListWithParamResposeProps | []> {
        // console.log("getJobListingListWithParams:", params);
        const { subject, level, name, price } = params;
        const priceRange = this.getPriceRange(price);
        const url = BACKEND_BASE_URL + '/jobListing/jobListingList?' + new URLSearchParams({
            subject: subject? subject : "",
            level: level? level : "",
            name: name? name : "",
            minPrice: priceRange.minPrice.toString(),
            maxPrice: priceRange.maxPrice.toString()
        });
        const token = localStorage.getItem("token");
        const header = {
            ...jsonHeader,
            // "Authorization": "Bearer " + token
        }
        const response = await Utility.fetchBuilder(url, 'GET', header, null);
        if(response.ok) {
            return await response.json();
            // console.log('data = ', data)
        } else {
            return [];
        }
    }

    private static getPriceRange(price?: string) {
        let minPrice = 0, maxPrice = 999;
        if(price !== undefined) {
            switch(price) {
                case "s":
                    maxPrice = 30;
                    break;

                case "m":
                    minPrice = 30;
                    maxPrice = 60;
                    break;

                case "l":
                    minPrice = 60;
                    maxPrice = 999;
                    break;

                default:
                    break;
            }
        }
        return {
            minPrice,
            maxPrice
        }
    }
}