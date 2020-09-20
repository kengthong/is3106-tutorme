import React from "react";
import { Button, Input } from "antd";

const { TextArea } = Input;

const MakeOfferComponent = (props: any) => {
  const tutorName = "test";
  const defaultMessage = `Hi ${tutorName}, I’m looking for a tutor to help me improve grades. Are you available for a free meeting? I’d like to find out more about how you work. I’m looking forward to your reply!`;
  return (
    <div
      style={{
        width: "100%",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
      }}
    >
      <span style={{ fontWeight: 500, fontSize: "20px", textAlign: "center" }}>
        Send {tutorName} a message
      </span>

      <div style={{ margin: "20px 0" }}>
        <TextArea rows={5} value={defaultMessage} />
      </div>

      <Button type={"primary"} style={{ margin: "20px 0" }}>
        <span>SEND A MESSAGE</span>
      </Button>

      <div style={{ textAlign: "center", opacity: 0.85 }}>
        Expected response time: 24h
      </div>
    </div>
  );
};

export default MakeOfferComponent;
