import {AUTHENTICATION_ERROR, BACKEND_BASE_URL, LOGIN_SUCCESSFUL, LOGOUT_SUCCESSFUL} from "../config/constants";
import {store} from "../store";
import {Utility} from "../config/Utility";

const jsonHeader= {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
};
export class UserService {
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
                    localStorage.setItem("token", token);
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

    static async register(firstName: string, lastName: string, email:string, password: string, phoneNumber: string, gender: string, date: string, accountType: string) {
        const url = BACKEND_BASE_URL + '/person/register';
        const body = {firstName, lastName, email, password, phoneNumber, gender, date, accountType}

        const response = await Utility.fetchBuilder(url, 'POST', jsonHeader, body)
        console.log("response: " + response)
        return response;
    }

    static async rehydrate(){
        const url = BACKEND_BASE_URL + '/person/get';
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };

        await Utility.fetchBuilder(url, 'GET', header, null)
            .then(async (res) => {
                console.log(res);

                if(res.ok) {
                    const data = await res.json();
                    console.log(data);
                    
                    store.dispatch({
                        type: LOGIN_SUCCESSFUL,
                        payload: data
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

    static async sendFeedback(body: string){
        const url = BACKEND_BASE_URL + '/message/sendFeedback';
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };
        const message = {body}

        const response = await Utility.fetchBuilder(url, 'POST', header, message)
        console.log("response: " + response)
        if (response.ok) {
            return true;
        } else {
            return false;
        }
    }

    static logout() {
        localStorage.removeItem("token");
        store.dispatch({
            type: LOGOUT_SUCCESSFUL,
            payload: undefined
        })
    }

    static async uploadImage(imgString: string) {
        const url = BACKEND_BASE_URL + '/person/uploadImage';
        const token = localStorage.getItem("token");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };
        const body = {

        }

        const response = await Utility.fetchBuilder(url, 'POST', header, body)
        console.log("response: " + response)
        return response;
    }
}