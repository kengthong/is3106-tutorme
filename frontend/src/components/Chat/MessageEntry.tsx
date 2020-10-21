import React, { useState } from "react";
import { Button, Input } from 'antd';


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

const MessageEntry = () => {

    return (

        <div >
            <form>
                <div style={{ display: "flex", flexDirection: "row", alignContent: "center" }} >
                    <Input placeholder="Write your message..." />
                    <Button type="primary" >Send</Button>
                </div>
            </form>
        </div>

    )
}

export default MessageEntry;