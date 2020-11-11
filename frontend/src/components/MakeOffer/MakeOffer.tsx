import React, { useState } from "react";
import { Button, Form, Input, message, Modal } from "antd";
import { useHistory } from "react-router-dom";
import { ChatService } from "../../services/Chat";
import { useSelector } from "react-redux";
import { UserState } from "../../reducer/user-reducer";
import { IRootState } from "../../store";


const { TextArea } = Input;
type jobListingDetailProps = {
  listing: jobListingType
}

const MakeOfferComponent = (props: any) => {
  const jobListing = props.listing;
  const tutorName = jobListing.tutor.firstName + " " + jobListing.tutor.lastName;
  const userState = useSelector<IRootState, UserState>((state) => state.userReducer);
  const defaultMessage = `Hi ${tutorName}, I’m looking for a tutor to help me improve grades. Are you available for a free meeting? I’d like to find out more about how you work. I’m looking forward to your reply!`;
  const [showSendMessage, setShowSendMessage] = useState(false);
  const [form] = Form.useForm();
  const history = useHistory();

  const onFinish = (fieldsValue: any) => {
    const messageValues = {
      ...fieldsValue,
      senderId: userState.currentUser?.personId.toString(),
      receiverId: jobListing.tutor.personId.toString()
    }
    sendMessage(messageValues);
    history.push('/chat');
  }

  const sendMessage = async (messageValues: any): Promise<void> => {
    const response = await ChatService.sendMessage(messageValues.senderId, messageValues.receiverId, messageValues.body);
    if (response) {
      return message.success("Message sent!")
    } else {
      return message.error("Error sending message. Please try again.")
    }
  }

  return (
    <div
      style={{
        width: "100%",
        display: "flex",
        flexDirection: "column",
        justifyContent: "center",
      }}
    >
      <Button
        type="primary"
        onClick={() => setShowSendMessage(true)}
      >
        Chat
      </Button>

      <Modal
        title="Sending a message..."
        visible={showSendMessage}
        onOk={() => setShowSendMessage(false)}
        onCancel={() => setShowSendMessage(false)}
        width={800}
        footer={null}
        style={{ display: "flex", flexDirection: "column" }}
      >
        <Form
          form={form}
          onFinish={onFinish}
        >

          <Form.Item
            style={{ margin: "20px 0" }}
            name="body"
            initialValue={defaultMessage}
            rules={[{
              required: true,
              message: "Please enter your message!"
            }]}
          >
            <Input.TextArea rows={5} />
          </Form.Item>

          <Button
            type={"primary"}
            htmlType="submit"
            style={{ margin: "20px 0" }}>
            <span>Send</span>
          </Button>
        </Form>

      </Modal>
    </div>

  );
};

export default MakeOfferComponent;
