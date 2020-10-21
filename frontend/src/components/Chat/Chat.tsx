import React, { useState } from "react";
import { ChatFeed, Message, ChatBubble, BubbleGroup } from "react-chat-ui";
import MsgEntry from "../Chat/MessageEntry"
import { Avatar, Layout, Menu, List } from "antd";

const { Header, Content, Footer, Sider } = Layout;
const { SubMenu } = Menu;

const styles = {
    button: {
        backgroundColor: '#fff',
        borderColor: '#1D2129',
        borderStyle: 'solid',
        borderRadius: 20,
        borderWidth: 2,
        color: '#1D2129',
        fontSize: 18,
        fontWeight: '300',
        paddingTop: 8,
        paddingBottom: 8,
        paddingLeft: 16,
        paddingRight: 16,
    },
    selected: {
        color: '#fff',
        backgroundColor: '#0084FF',
        borderColor: '#0084FF',
    },
};

const data = [
    {
        title: 'Ant Design Title 1',
    },
    {
        title: 'Ant Design Title 2',
    },
    {
        title: 'Ant Design Title 3',
    },
    {
        title: 'Ant Design Title 4',
    },
];

const Chat = () => {

    return (

        <div className="Row">
            <h1 className="text-center">Chat page</h1>

            <br />

            <div className="container" style={{ width: "20%", float: "left", borderRight: "solid", height: "100%" }}>
                <h4 className="text-center">Chat List</h4>

                <List
                    itemLayout="horizontal"
                    dataSource={data}
                    renderItem={item => (
                        <List.Item>
                            <List.Item.Meta
                                avatar={<Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />}
                                title={<a href="https://ant.design">{item.title}</a>}
                            />
                        </List.Item>
                    )}
                />

            </div>


            <div className="container" style={{ width: "80%", float: "right", height: "100%" }} >

                <div className="chatfeed-wrapper">
                    <ChatBubble
                        message={new Message({ id: 1, message: 'I float to the left!' })}
                    />
                    <ChatBubble
                        message={new Message({ id: 0, message: 'I float to the right!' })}
                    />

                    <BubbleGroup
                        messages={[
                            new Message({ id: 1, message: 'Hey!' }),
                            new Message({ id: 1, message: 'I forgot to mention...' }),
                            new Message({
                                id: 1,
                                message:
                                    "Oh no, I forgot... I think I was going to say I'm a BubbleGroup",
                            }),
                        ]}
                        id={1}
                        showSenderName={true}
                        senderName={'Elon Musk'}
                    />
                    <ChatBubble
                        message={new Message({ id: 2, message: "I 'm a single ChatBubble!" })}
                    />
                    <BubbleGroup
                        messages={[
                            new Message({ id: 0, message: 'How could you forget already?!' }),
                            new Message({
                                id: 0,
                                message: "Oh well. I'm a BubbleGroup as well",
                            }),
                        ]}
                        id={1}
                        showSenderName={true}
                        senderName={'Elon Musk'}
                    />
                </div>

                <footer style={{ position: "absolute", bottom: "10px", width: "75%" }}>
                    <MsgEntry />
                </footer>
            </div>

        </div>
    );

}

export default Chat;