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