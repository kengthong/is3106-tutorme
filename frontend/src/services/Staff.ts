import { AUTHENTICATION_ERROR, BACKEND_BASE_URL, LOGIN_SUCCESSFUL, LOGOUT_SUCCESSFUL } from "../config/constants";
import { store } from "../store";
import { Utility } from "../config/Utility";

const jsonHeader = {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
};
export class StaffService {
    static async login(email: string, password: string) {
        const url = BACKEND_BASE_URL + '/person/login';
        const body = { email, password };
        await Utility.fetchBuilder(url, 'POST', jsonHeader, body)
            .then(async (res) => {
                if (res.ok) {
                    const text = await res.text();
                    const data = JSON.parse(text);
                    const user = JSON.parse(data.user);
                    console.log('data =', data)
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
            .catch(err => {
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
        const token = localStorage.getItem("staffToken");
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
        const token = localStorage.getItem("staffToken");
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
        const token = localStorage.getItem("staffToken");
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
        const token = localStorage.getItem("staffToken");
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

    static async getDashboard() {
        const url = BACKEND_BASE_URL + '/staff/home';
        const token = localStorage.getItem("staffToken");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };

        const response = await Utility.fetchBuilder(url, 'GET', header, null);
        if (response.ok) {
            const data = response.json();
            console.log(data)
            return data;
        } else {
            return [];
        }
    }

    static async getAllStaff() {
        const url = BACKEND_BASE_URL + '/staff/getStaffs';
        const token = localStorage.getItem("staffToken");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };

        const response = await Utility.fetchBuilder(url, 'GET', header, null);
        if (response.ok) {
            const data = response.json();
            console.log(data)
            return data;
        } else {
            return [];
        }
    }


    public static async getChatList(userId: number): Promise<chatListResponseType> {
        const token = localStorage.getItem("staffToken");
        const url = "http://localhost:8080/tutorme-war/webresources/message/conversations?personId=" + userId;
        const header = {
            ...jsonHeader,
            'Authorization': 'Bearer ' + token
        }

        const response = await Utility.fetchBuilder(url, 'GET', header, null);
        if(response.ok) {
            return await response.json();
        } else {
            console.log(response);
            return []
        }

    }

    public static async getMessagesByUserIds(currentUserId: string, chatUserId: string): Promise<chatMessagesType> {
        const url = "http://localhost:8080/tutorme-war/webresources/message/conversation?" + new URLSearchParams({
            //p1Id: currentUserId, -----TODO: DOUBLE CHECK PLS!!!
            p2Id: chatUserId
        });
        const token = localStorage.getItem("staffToken");
        let data = [];
        if(token) {
            const header = {
                ...jsonHeader,
                'Authorization': 'Bearer ' + token
            }

            const response = await Utility.fetchBuilder(url, 'GET', header, null);
            if(response.ok) {
                data = await response.json();
            } else {
                console.log(response);
            }
        }
        return data;
    }

    public static async sendMessage(senderId: string, receiverId: string, body: string): Promise<boolean> {
        const url = "http://localhost:8080/tutorme-war/webresources/message/sendMessage";
        const token = localStorage.getItem("staffToken");
        if(token) {
            const header = {
                ...jsonHeader,
                'Authorization': 'Bearer ' + token
            }
            const fetchBody = {
                senderId,
                receiverId,
                body
            };
            const res = await Utility.fetchBuilder(url, 'POST', header, fetchBody);
            console.log('res = ', res)
            if(res.ok) {
                return true;
            }
        }

        return false;
    }

    static async getAllTutees() {
        const url = BACKEND_BASE_URL + '/staff/getTutees';
        const token = localStorage.getItem("staffToken");
        const jsonHeader = Utility.getJsonHeader();
        const header = {
            ...jsonHeader,
            "Authorization": "Bearer " + token
        };
        const response = await Utility.fetchBuilder(url, 'GET', header, null);
        if (response.ok) {
            return await response.json();
        } else {
            return false;
        }
    }
}