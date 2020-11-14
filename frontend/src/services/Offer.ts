import {Utility} from "../config/Utility";

export class OfferService {

    static async createOffer(body: createOfferParams): Promise<boolean> {
        const url = "http://localhost:8080/tutorme-war/webresources/offer/makeOffer/";
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };

        const response = await Utility.fetchBuilder(url, 'POST', header, body);
        if (response.ok) {
            return true;
        } else {
            return false;
        }
    }

    static async rejectOffer(offerId: number): Promise<boolean> {
        const url = "http://localhost:8080/tutorme-war/webresources/offer/reject/" + offerId;
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };
        const response = await Utility.fetchBuilder(url, 'PUT', header, offerId);
        if (response.ok) {
            return true;
        } else {
            return false;
        }
    }

    static async acceptOffer(offerId: number): Promise<boolean> {
        const url = "http://localhost:8080/tutorme-war/webresources/offer/accept/" + offerId;
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };

        const response = await Utility.fetchBuilder(url, 'PUT', header, offerId);
        if (response.ok) {
            return true;
        } else {
            return false;
        }
    }

    static async withdrawOffer(offerId: number): Promise<boolean> {
        const url = "http://localhost:8080/tutorme-war/webresources/offer/withdraw/" + offerId;
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };
        const response = await Utility.fetchBuilder(url, 'PUT', header, offerId);
        if (response.ok) {
            return true;
        } else {
            return false;
        }
    }

    static async getOffers() {
        const url = "http://localhost:8080/tutorme-war/webresources/offer/get";
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };

        const response = await Utility.fetchBuilder(url, 'GET', header, null);
        if (response.ok) {
            return await response.json();
        } else {
            return [];
        }
    }
};