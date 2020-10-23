import React, { useState } from "react";
import { ChatFeed, Message, ChatBubble, BubbleGroup } from "react-chat-ui";
import { Avatar, Input, List, Button } from "antd";

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

//Fake data for testing
const messages = [
    {
        id: "mark",
        message: 'Hey guys!',
        senderName: 'Mark'
    },
    {
        id: 2,
        message: 'Hey Evan here!',
        senderName: 'Evan'
    }
]

const Chat = () => {

    const [formData, setFormData] = useState({
        message: "",
    });

    const handleChange = (e: any) => {
        console.log(e);
        const name = e && e.target && e.target.name ? e.target.name : "";
        const value = e && e.target && e.target.value ? e.target.value : "";
        setFormData((prevState) => ({
            ...prevState,
            [name]: value,
        }));

        console.log(formData);
    };

    const onMessageSubmit = (e: any) => {
        console.log(e);
    };

    return (

        <div className="" style={{border: '1px solid #e8e8e8', height: '100%'}}>
            {/*<h1 className="text-center">Chat page</h1>*/}

            <br />

            <div className="container" style={{ width: "20%", float: "left", borderRight: "solid", height: "100%" }}>
                <h4 className="text-center">Chat List</h4>

                <List
                    itemLayout="horizontal"
                    dataSource={messages}
                    renderItem={item => (
                        <List.Item>
                            <List.Item.Meta
                                avatar={<Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />}
                                title={<a href="https://ant.design">{item.senderName}</a>}
                            />
                        </List.Item>
                    )}
                />

            </div>


            <div className="container" style={{ width: "80%", float: "right", height: "100%" }} >
                <div className="chatfeed-wrapper">
                    <ChatFeed
                        messages={messages}
                        showSenderName
                    />

                    <footer style={{ position: "absolute", bottom: "10px", width: "100%" }}>
                        <form onSubmit={e => onMessageSubmit(e)}>
                            <Input
                                name="message"
                                placeholder="Type a message..."
                                className="message-input"
                                onChange={(e) => handleChange(e)}
                                style={{ width: "50%" }}
                            />
                            <Button type="primary" onClick={onMessageSubmit} >Send</Button>
                        </form>
                    </footer>
                </div>


            </div>

        </div>
    );

}

export default Chat;