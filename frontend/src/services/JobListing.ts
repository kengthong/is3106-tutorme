import { Utility } from "../config/Utility";
import { BACKEND_BASE_URL } from "../config/constants";

const jsonHeader = {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
};
export class JobListingService {
    static async getJobListingListWithParams(params: getJobListingListWithParamsProps): Promise<getJobListingListWithParamResposeProps | []> {
        // console.log("getJobListingListWithParams:", params);
        const { subject, level, name, price } = params;
        const priceRange = this.getPriceRange(price);
        const url = BACKEND_BASE_URL + '/jobListing/jobListingList?' + new URLSearchParams({
            subject: subject ? subject : "",
            level: level ? level : "",
            name: name ? name : "",
            minPrice: priceRange.minPrice.toString(),
            maxPrice: priceRange.maxPrice.toString()
        });
        const token = localStorage.getItem("token");
        const header = {
            ...jsonHeader,
            // "Authorization": "Bearer " + token
        }
        const response = await Utility.fetchBuilder(url, 'GET', header, null);
        if (response.ok) {
            return await response.json();
            // console.log('data = ', data)
        } else {
            return [];
        }
    }


    public static async getJobListing(listId: number): Promise<jobListingType | null> {
        const url = BACKEND_BASE_URL + "/jobListing/" + listId;
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };
        const response = await Utility.fetchBuilder(url, 'GET', header, null);
        if (response.ok) {
            const data = await response.json();
            console.log(data)
            return data;
        } else {
            return null;
        }

    }

    public static async createJobListing(body: createJobListingParams): Promise<boolean> {
        const url = "http://localhost:8080/tutorme-war/webresources/jobListing/create";
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };

        const response = await Utility.fetchBuilder(url, 'POST', header, body);
        console.log("response: " + response)
        if (response.ok) {
            return true;
        } else {
            return false;
        }

    }

    private static getPriceRange(price?: string) {
        let minPrice = 0, maxPrice = 999;
        if (price !== undefined) {
            switch (price) {
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

    public static async getMyJobListings() {
        const url = "http://localhost:8080/tutorme-war/webresources/tutor/jobListings";
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };

        const response = await Utility.fetchBuilder(url, 'GET', header, null);
        console.log("response: " + response)
        if (response.ok) {
            return await response.json();
        } else {
            return [];
        }
    }

    public static async postComment(body: any) {
        const url = "http://localhost:8080/tutorme-war/webresources/rating/rate";
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };

        const response = await Utility.fetchBuilder(url, 'POST', header, body);
        return response;
    }

    public static async activateJobListing(listId: number) {
        const url = "http://localhost:8080/tutorme-war/webresources/jobListing/activate/" + listId;
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };
        const response = await Utility.fetchBuilder(url, 'PUT', header, null);
        console.log("response: " + response)
        if (response.ok) {
            return await response;
        } else {
            return [];
        }
    }
    public static async editJobListing(body: createJobListingParams): Promise<boolean> {
        const url = "http://localhost:8080/tutorme-war/webresources/jobListing/edit";
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };

        const response = await Utility.fetchBuilder(url, 'PUT', header, body);
        console.log("response: " + response)
        if (response.ok) {
            return true;
        } else {
            return false;
        }

    }

    public static async editJobListing(body: createJobListingParams): Promise<boolean> {
        const url = "http://localhost:8080/tutorme-war/webresources/jobListing/edit";
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };

        const response = await Utility.fetchBuilder(url, 'PUT', header, body);
        console.log("response: " + response)
        if (response.ok) {
            return true;
        } else {
            return false;
        }

    }


    public static async deactivateJobListing(listId: number) {
        const url = "http://localhost:8080/tutorme-war/webresources/jobListing/deactivate/" + listId;
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };

        const response = await Utility.fetchBuilder(url, 'PUT', header, null);
        console.log("response: " + response)
        if (response.ok) {
            return await response;
        } else {
            return [];
        }
    }

}