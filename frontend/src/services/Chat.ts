import {Utility} from "../config/Utility";

const jsonHeader= {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
};

export class ChatService {
    public static async getChatList(userId: number): Promise<chatListResponseType> {
        const token = localStorage.getItem("token");
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
            p1Id: currentUserId,
            p2Id: chatUserId
        });
        const token = localStorage.getItem("token");
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
        const token = localStorage.getItem("token");
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
}