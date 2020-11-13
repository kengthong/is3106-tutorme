import React, { useEffect, useState } from "react";
import { Card, Statistic, Timeline } from "antd";
import Chat from "../../../components/Chat/Chat"

const ChatCompo = () => {
    return (
        <div style={{ height: "100%" }}>
            <div style={{ height: 'calc(100vh - 90px)', maxWidth: '1170px', marginLeft: 'auto', marginRight: 'auto' }}>
                {/*<Chat />*/}
                <Chat />
            </div>
            <br />
        </div>
    )
}

export default ChatCompo;