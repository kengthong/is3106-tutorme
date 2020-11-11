import {AUTHENTICATION_ERROR, BACKEND_BASE_URL, LOGIN_SUCCESSFUL, LOGOUT_SUCCESSFUL} from "../config/constants";
import {store} from "../store";
import {Utility} from "../config/Utility";

const jsonHeader= {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
};
export class StaffService {
    static async login (email: string, password: string) {
        const url = BACKEND_BASE_URL + '/person/login';
        const body = { email, password };
        await Utility.fetchBuilder(url, 'POST', jsonHeader, body)
            .then(async (res) => {
                if(res.ok) {
                    const text = await res.text();
                    const data = JSON.parse(text);
                    const user = JSON.parse(data.user);
                    const token = data.jwtToken;
                    localStorage.setItem("staffToken", token);
                    store.dispatch({
                        type: LOGIN_SUCCESSFUL,
                        payload: user
                    })
                } else {
                    const body = await res.json();
                    store.dispatch({
                        type: AUTHENTICATION_ERROR,
                        payload: body.error
                    })
                }
            })
            .catch( err => {
                console.log(err);
            })
    }

    static register() {

    }

    static logout() {
        localStorage.removeItem("staffToken");
        store.dispatch({
            type: LOGOUT_SUCCESSFUL,
            payload: undefined
        })
    }

    static async banUser(userId: number) {
        const url = BACKEND_BASE_URL + '/staff/ban/' + userId;
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };

        const response = await Utility.fetchBuilder(url, 'PUT', header, null);
        console.log('response =', response)
        if (response.ok) {
            return true;
        } else {
            return false;
        }
    }

    static async unBanUser(userId: number) {
        const url = BACKEND_BASE_URL + '/staff/unban/' + userId;
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };

        const response = await Utility.fetchBuilder(url, 'PUT', header, null);
        if (response.ok) {
            return true;
        } else {
            return false;
        }
    }

    static async getAllJobListing() {
        const url = BACKEND_BASE_URL + '/staff/jobListings/';
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };

        const response = await Utility.fetchBuilder(url, 'GET', header, null);
        if (response.ok) {
            const data = response.json();
            return data;
        } else {
            return [];
        }
    }

    static async getOfferList() {
        const url = BACKEND_BASE_URL + '/staff/offers';
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };

        const response = await Utility.fetchBuilder(url, 'GET', header, null);
        if (response.ok) {
            const data = response.json();
            return data;
        } else {
            return [];
        }
    }
}