import {Utility} from "../config/Utility";

const jsonHeader= {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
};

export class ChatService {
    public static async getChatList() {
        const token = localStorage.getItem("token");
        const header = {
            ...jsonHeader,
            'Authorization': 'Bearer ' + token
        }

        // await Utility.fetchBuilder(url, 'POST', header, body)
        //     .then(res => {
        //
        //     })
        return [
            {
                body: 'test message from Person_8 to Person_18',
                createdDate: '2020-10-22T16:27:50.429Z[UTC]',
                messageId: 149,
                receiver: {
                    activeStatus: true,
                    createdDate: '2020-10-22T16:27:49.705Z[UTC]',
                    dob: '2002-11-28T20:31:39.411Z[UTC]',
                    email: 'test_tutee3@email.com',
                    firstName: 'test',
                    gender: 'MALE',
                    lastName: 'tutee',
                    mobileNum: '92723562',
                    personEnum: 'TUTEE',
                    personId: 18,
                    profileDesc: 'i am test tutee 3'
                },
                sender: {
                    activeStatus: true,
                    createdDate: '2020-10-22T16:27:49.683Z[UTC]',
                    dob: '2002-05-06T15:47:03.95Z[UTC]',
                    email: 'test_tutor2@email.com',
                    firstName: 'test',
                    gender: 'FEMALE',
                    lastName: 'tutor',
                    mobileNum: '87822185',
                    personEnum: 'TUTOR',
                    personId: 8,
                    avgRating: 0,
                    citizenship: 'SINGAPORE',
                    highestQualification: 'BACHELOR',
                    profileDesc: 'i am test tutor 2',
                    race: 'CHINESE'
                }
            },
            {
                body: 'test message from Person_18 to Person_8',
                createdDate: '2020-10-22T16:27:50.429Z[UTC]',
                messageId: 150,
                receiver: {
                    activeStatus: true,
                    createdDate: '2020-10-22T16:27:49.683Z[UTC]',
                    dob: '2002-05-06T15:47:03.95Z[UTC]',
                    email: 'test_tutor2@email.com',
                    firstName: 'test',
                    gender: 'FEMALE',
                    lastName: 'tutor',
                    mobileNum: '87822185',
                    personEnum: 'TUTOR',
                    personId: 8,
                    avgRating: 0,
                    citizenship: 'SINGAPORE',
                    highestQualification: 'BACHELOR',
                    profileDesc: 'i am test tutor 2',
                    race: 'CHINESE'
                },
                sender: {
                    activeStatus: true,
                    createdDate: '2020-10-22T16:27:49.705Z[UTC]',
                    dob: '2002-11-28T20:31:39.411Z[UTC]',
                    email: 'test_tutee3@email.com',
                    firstName: 'test',
                    gender: 'MALE',
                    lastName: 'tutee',
                    mobileNum: '92723562',
                    personEnum: 'TUTEE',
                    personId: 18,
                    profileDesc: 'i am test tutee 3'
                }
            }
        ]
    }

    public static async getMessagesByUserIds(currentUserId: string, chatUserId: string) {
        const url = "http://localhost:8080/tutorme-war/webresources/message/conversation" + new URLSearchParams({
            p1Id: currentUserId,
            p2Id: chatUserId
        });
        const token = localStorage.getItem("token");
        console.log(token)
        // if(token) {
        //     const header = {
        //         ...jsonHeader,
        //         'Authorization': 'Bearer ' + token
        //     }
        //
        //     const response = await Utility.fetchBuilder(url, 'GET', header, null);
        //     const jsonObj = await response.json();
        // }
        return [
            {
                body: 'test message from Person_1 to Person_7',
                createdDate: '2020-10-22T16:27:50.344Z[UTC]',
                messageId: 83,
                receiver: {
                    activeStatus: true,
                    createdDate: '2020-10-22T16:27:49.684Z[UTC]',
                    dob: '2000-03-16T10:23:16.843Z[UTC]',
                    email: 'test_tutor8@email.com',
                    firstName: 'test',
                    gender: 'MALE',
                    lastName: 'tutor',
                    mobileNum: '83916600',
                    personEnum: 'TUTOR',
                    personId: 7,
                    avgRating: 0,
                    citizenship: 'SINGAPORE',
                    highestQualification: 'BACHELOR',
                    profileDesc: 'i am test tutor 8',
                    race: 'CHINESE'
                },
                sender: {
                    activeStatus: true,
                    createdDate: '2020-10-22T16:27:49.653Z[UTC]',
                    dob: '1989-12-30T16:27:49.652Z[UTC]',
                    email: 'test_staff1@email.com',
                    firstName: 'test',
                    gender: 'MALE',
                    lastName: 'admin',
                    mobileNum: '81209502',
                    personEnum: 'STAFF',
                    personId: 1,
                    staffPositionEnum: 'MANAGER'
                }
            },
            {
                body: 'test message from Person_7 to Person_1',
                createdDate: '2020-10-22T16:27:50.344Z[UTC]',
                messageId: 84,
                receiver: {
                    activeStatus: true,
                    createdDate: '2020-10-22T16:27:49.653Z[UTC]',
                    dob: '1989-12-30T16:27:49.652Z[UTC]',
                    email: 'test_staff1@email.com',
                    firstName: 'test',
                    gender: 'MALE',
                    lastName: 'admin',
                    mobileNum: '81209502',
                    personEnum: 'STAFF',
                    personId: 1,
                    staffPositionEnum: 'MANAGER'
                },
                sender: {
                    activeStatus: true,
                    createdDate: '2020-10-22T16:27:49.684Z[UTC]',
                    dob: '2000-03-16T10:23:16.843Z[UTC]',
                    email: 'test_tutor8@email.com',
                    firstName: 'test',
                    gender: 'MALE',
                    lastName: 'tutor',
                    mobileNum: '83916600',
                    personEnum: 'TUTOR',
                    personId: 7,
                    avgRating: 0,
                    citizenship: 'SINGAPORE',
                    highestQualification: 'BACHELOR',
                    profileDesc: 'i am test tutor 8',
                    race: 'CHINESE'
                }
            }
        ]
    }

    public static async sendMessage(senderId: string, receiverId: string, body: string): Promise<boolean> {
        const url = "http://localhost:8080/tutorme-war/webresources/message/sendMessage";
        const token = localStorage.getItem("token");
        return true;
        // if(token) {
        //     const header = {
        //         ...jsonHeader,
        //         'Authorization': 'Bearer ' + token
        //     }
        //     const fetchBody = {
        //         senderId,
        //         receiverId,
        //         body
        //     };
        //     await Utility.fetchBuilder(url, 'POST', header, fetchBody)
        //         .then(res => {
        //             if(res.ok) {
        //                 return true;
        //             }
        //         });
        // }
        //
        // return false;
    }
}