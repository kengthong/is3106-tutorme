import React, {ChangeEvent, useEffect, useState} from 'react';
import {Avatar, Button, Input, List} from "antd";
import { ChatFeed, Message, ChatBubble, BubbleGroup } from "react-chat-ui";
import {ChatService} from "../../services/Chat";

const currUserId = 1;
const activeStyle = {
    backgroundColor: 'rgb(237 247 255)'
}

const NewChat = () => {
    const [chatList, setChatList] = useState<chatListType>([]);
    const [activeUserChatId, setActiveUserChatId] = useState("");
    const [messageInput, setMessageInput] = useState("");
    const [loading, setLoading] = useState(false);
    const [chatMessages, setChatMessages] = useState<Message[]>([]);

    useEffect(() => {
        const getChatList = async () => {
            const chatList: chatListResponseType = await ChatService.getChatList();
            const _chatList = chatList.map( c => ({
                userId: c.sender.personId == currUserId? c.receiver.personId : c.sender.personId,
                message: c.body,
                senderName: c.sender.personId == currUserId? c.receiver.firstName : c.sender.firstName,
            }));
            setChatList(_chatList);

        }

        getChatList();

    }, [])
    const handleChange = (e: ChangeEvent<HTMLInputElement>) => {
        const words = e.target.value;
        setMessageInput(words);
    }
    const onMessageSubmit = () => {
        const sendMessage = async () => {

            const ok = await ChatService.sendMessage(currUserId.toString(), activeUserChatId, messageInput);
            setChatList(chatList);
            if(ok) {
                setLoading(true);
                const newChatMessages: Message[] = chatMessages;
                newChatMessages.push(new Message({
                    id: 0,
                    message: messageInput
                }));
                console.log('ok');
                setChatMessages(newChatMessages);
                setMessageInput("");
                setLoading(false);
            } else {
                console.log('not ok')
                // message error
            }
        }

        sendMessage();

    }

    const onSelectChat = (id: number) => {
        console.log("user =", id);
        const getMessagesByUserIds = async () => {
            const chatMessages = await ChatService.getMessagesByUserIds(currUserId.toString(), id.toString());
            console.log(chatMessages)
            const chatMsgObj = chatMessages.map( (m,i) => {
                return new Message({
                    id: m.sender.personId === currUserId? 1 : 0,
                    message: m.body
                })
            })
            setChatMessages(chatMsgObj);
        }

        getMessagesByUserIds();
        setActiveUserChatId(id.toString());
    }
    return (
        <div className='flex-row h-100' style={{border: '1px solid #e8e8e8'}}>
            <div className='w-30'>
                <List
                    itemLayout="horizontal"
                    dataSource={chatList}
                    renderItem={item => (
                        <div onClick={() => onSelectChat(item.userId)} className="clickable" style={item.userId.toString() === activeUserChatId? activeStyle : {}}>
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

            <div className="chatfeed-wrapper flex-col justify-space-between w-70" style={{borderLeft: '1px solid #e8e8e8'}}>
                <div className="w-100 h-100">
                    {!loading && chatMessages.length > 0?
                        chatMessages.map( (m,i) => {
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

                <div>
                    <Input
                        name="message"
                        placeholder="Type a message..."
                        className="message-input"
                        value={messageInput}
                        onChange={(e) => handleChange(e)}
                        style={{ width: "50%" }}
                    />
                    <Button type="primary" onClick={onMessageSubmit} >Send</Button>
                </div>
            </div>
        </div>
    )
}

export default NewChat;
