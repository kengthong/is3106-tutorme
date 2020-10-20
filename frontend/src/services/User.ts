import jwt from 'jwt-decode'
import {AUTHENTICATION_ERROR, BACKEND_BASE_URL, LOGIN_SUCCESSFUL, LOGOUT_SUCCESSFUL} from "../config/constants";
import { Dispatch } from "redux"
import { store } from "../store";
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
                    const token = await res.text();
                    const user = jwt(token);
                    console.log("token=", token);
                    localStorage.setItem("token", token);
                    store.dispatch({
                        type: LOGIN_SUCCESSFUL,
                        payload: user
                    })
                } else {
                    const body = await res.text();
                    store.dispatch({
                        type: AUTHENTICATION_ERROR,
                        payload: body
                    })
                }
            })
    }

    static register() {

    }

    static logout() {
        localStorage.removeItem("token");
        store.dispatch({
            type: LOGOUT_SUCCESSFUL,
            payload: undefined
        })
    }
}