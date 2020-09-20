import React, { useState } from "react";
import { Image, Descriptions, Button } from "antd";

const TuteeProfile = () => {
  const [formData, setFormData] = useState({
    date: null,
    email: "hello@gmail.com",
    firstName: "Bob",
    gender: "Male",
    lastName: "Schumacher",
    password: "passpass",
    phoneNumber: "12345678",
  });

  return (
    <div>
      <div style={{ textAlign: "center", padding: "100" }}>
        <Image
          width={300}
          src="https://zos.alipayobjects.com/rmsportal/jkjgkEfvpUPVyRjUImniVslZfWPnJuuZ.png"
        />
      </div>
      <br />
      <br />
      <Descriptions
        title="My Profile"
        column={{ xxl: 3, xl: 3, lg: 3, md: 2, sm: 2, xs: 1 }}
      >
        <Descriptions.Item label="First Name">Melissa</Descriptions.Item>
        <Descriptions.Item label="Last Name">Tan</Descriptions.Item>
        <Descriptions.Item label="UserName">hello@gmail.com</Descriptions.Item>
        <Descriptions.Item label="Telephone">12345678</Descriptions.Item>
        <Descriptions.Item label="Live">Singapore, Singapore</Descriptions.Item>
        <Descriptions.Item label="Remark">Science Teacher</Descriptions.Item>
        <Descriptions.Item label="Address">
          Blk 18, Yishun Road, Singapore
        </Descriptions.Item>
      </Descriptions>
      <br />
      <div style={{ textAlign: "center", padding: "100" }}>
        <Button type="primary">Edit Profile</Button>
        <br />
        <br />
        <Button danger>Change Password</Button>
      </div>
    </div>
  );
};

export default TuteeProfile;
