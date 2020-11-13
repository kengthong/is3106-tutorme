import React, { ChangeEvent, useEffect, useState } from 'react';
import { Avatar, Input, List, message } from "antd";
import { ChatBubble, Message } from "react-chat-ui";
import { ChatService } from "../../services/Chat";
import { useSelector } from "react-redux";
import { IRootState } from "../../store";
import { UserState } from "../../reducer/user-reducer";
import { StaffService } from '../../services/Staff';

// const currUserId = 1;
const activeStyle = {
    backgroundColor: 'rgb(237 247 255)'
}
const { Search } = Input;

const Chat = () => {
    const [chatList, setChatList] = useState<chatListType>([]);
    const [activeUserChatId, setActiveUserChatId] = useState("");
    const [messageInput, setMessageInput] = useState("");
    const [loading, setLoading] = useState(false);
    const [chatMessages, setChatMessages] = useState<Message[]>([]);
    const [currUserId, setCurrUserId] = useState(-1);
    const userState = useSelector<IRootState, UserState>((state) => state.userReducer);

    useEffect(() => {
        const getChatList = async () => {
            const id = userState.currentUser ? userState.currentUser.personId : -1;
            const userType = userState.currentUser?.personEnum.toLowerCase();
            setCurrUserId(id);
            if (id !== -1 && userType === 'staff') {

                const chatList: chatListResponseType = await StaffService.getChatList(id);

                const _chatList = chatList.map(c => ({
                    userId: c[0] ? c[0].sender.personId === id ? c[0].receiver.personId : c[0].sender.personId : -1,
                    message: c[0] ? c[0].body : "",
                    senderName: c[0] ? c[0].sender.personId === id ? c[0].receiver.firstName : c[0].sender.firstName : "",
                }));
                setChatList(_chatList);

            } else if (id !== -1) {

                const chatList: chatListResponseType = await ChatService.getChatList(id);

                const _chatList = chatList.map(c => ({
                    userId: c[0] ? c[0].sender.personId === id ? c[0].receiver.personId : c[0].sender.personId : -1,
                    message: c[0] ? c[0].body : "",
                    senderName: c[0] ? c[0].sender.personId === id ? c[0].receiver.firstName : c[0].sender.firstName : "",
                }));
                setChatList(_chatList);

            }
        }
        getChatList();
    }, [])

    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        const words = e.target.value;
        setMessageInput(words);
    }
    const onMessageSubmit = () => {
        const sendMessage = async () => {
            const userType = userState.currentUser?.personEnum.toLowerCase();
            if (userType === 'staff') {

                const ok = await StaffService.sendMessage(currUserId.toString(), activeUserChatId, messageInput);
                setChatList(chatList);
                if (ok) {
                    setLoading(true);
                    const newChatMessages: Message[] = chatMessages;
                    newChatMessages.push(new Message({
                        id: 0,
                        message: messageInput
                    }));
                    setChatMessages(newChatMessages);
                    setMessageInput("");
                    setLoading(false);
                } else {
                    // message error
                    message.error('This is an error message');
                }

            } else {

                const ok = await ChatService.sendMessage(currUserId.toString(), activeUserChatId, messageInput);
                setChatList(chatList);
                if (ok) {
                    setLoading(true);
                    const newChatMessages: Message[] = chatMessages;
                    newChatMessages.push(new Message({
                        id: 0,
                        message: messageInput
                    }));
                    setChatMessages(newChatMessages);
                    setMessageInput("");
                    setLoading(false);
                } else {
                    // message error
                    message.error('This is an error message');
                }

            }
        }

        if (messageInput !== "") {
            sendMessage();
        }

    }

    const onSelectChat = (id: number) => {
        const getMessagesByUserIds = async () => {
            const userType = userState.currentUser?.personEnum.toLowerCase();
            if (userType === 'staff') {

                const chatMessages = await StaffService.getMessagesByUserIds(currUserId.toString(), id.toString());
                const chatMsgObj = chatMessages.map((m, i) => {
                    return new Message({
                        id: m.sender.personId === currUserId ? 0 : 1,
                        message: m.body
                    })
                })
                setChatMessages(chatMsgObj);

            } else {

                const chatMessages = await ChatService.getMessagesByUserIds(currUserId.toString(), id.toString());
                const chatMsgObj = chatMessages.map((m, i) => {
                    return new Message({
                        id: m.sender.personId === currUserId ? 0 : 1,
                        message: m.body
                    })
                })
                setChatMessages(chatMsgObj);
            }
        }

        getMessagesByUserIds();
        setActiveUserChatId(id.toString());
    }

    return (
        <div className='flex-row h-100' style={{ border: '1px solid #e8e8e8' }}>
            <div className='w-30'>
                <List
                    itemLayout="horizontal"
                    dataSource={chatList}
                    renderItem={item => (
                        <div onClick={() => onSelectChat(item.userId)} className="clickable" style={item.userId.toString() === activeUserChatId ? activeStyle : {}}>
                            <List.Item>
                                <List.Item.Meta
                                    avatar={<Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />}
                                    title={item.senderName}
                                />
                            </List.Item>
                        </div>
                    )}
                />
            </div>

            <div className="chatfeed-wrapper flex-col justify-space-between w-70" style={{ borderLeft: '1px solid #e8e8e8' }}>
                <div className="w-100 h-100" style={{ overflow: 'auto' }}>
                    {!loading && activeUserChatId !== "" ?
                        chatMessages.map((m, i) => {
                            return (
                                <ChatBubble
                                    key={i}
                                    message={m}
                                />
                            )
                        })
                        :
                        <div className="opacity-65 fs-32 flex-row justify-center align-center h-100 w-100">
                            Select a chat
                        </div>
                    }
                </div>
                {!loading && activeUserChatId !== "" ?
                    (<div>
                        <Search
                            name="message"
                            placeholder="Type a message..."
                            className="message-input"
                            value={messageInput}
                            onChange={(e) => handleChange(e)}
                            onSearch={onMessageSubmit}
                            style={{ width: "100%" }}
                            enterButton={"Send"}
                        />
                    </div>)
                    :
                    null
                }
            </div>
        </div>
    )
}

export default Chat;
