import React from "react";
import Header from "../../../components/Header/Header";
import Chat from "../../../components/Chat/Chat";
import BodyContainer from "../../../components/Layout/BodyContainer";
import NewChat from "../../../components/Chat/NewChat";

const ChatPage = () => {
    return (
        <div style={{ height: "100%" }}>
            <Header />
            <div style={{height: 'calc(100vh - 90px)', maxWidth: '1170px', marginLeft: 'auto', marginRight: 'auto'}}>
                {/*<Chat />*/}
                <NewChat/>
            </div>
        </div>
    );
};

export default ChatPage;