import React from "react";
import Header from "../../../components/Header/Header";
import Chat from "../../../components/Chat/Chat";

const ChatPage = () => {
    return (
        <div style={{ height: "100%" }}>
            <Header />
            <div style={{ height: 'calc(100vh - 90px)', maxWidth: '1170px', marginLeft: 'auto', marginRight: 'auto' }}>
                {/*<Chat />*/}
                <Chat />
            </div>
            <br />
        </div>
    );
};

export default ChatPage;