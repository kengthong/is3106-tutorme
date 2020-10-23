import React from "react";
import Header from "../../../components/Header/Header";
import Chat from "../../../components/Chat/Chat";
import BodyContainer from "../../../components/Layout/BodyContainer";

const ChatPage = () => {
    return (
        <div style={{ height: "100%" }}>
            <Header />
            <br />
            <div className="container">
                <Chat />
            </div>
        </div>
    );
};

export default ChatPage;