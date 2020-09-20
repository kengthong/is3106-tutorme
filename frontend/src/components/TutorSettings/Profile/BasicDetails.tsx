import React from "react";
import { Col, Rate, Row } from "antd";

const BasicDetails = () => {
  const value = 5;
  return (
    <div>
      <Row style={{ margin: "32px 0" }}>
        <Col span={6}>
          <img
            alt="user profile pic"
            src="https://vignette.wikia.nocookie.net/mrbean/images/4/4b/Mr_beans_holiday_ver2.jpg/revision/latest?cb=20181130033425"
            width="160px"
            height="160px"
          />
        </Col>
        <Col span={18}>
          <Row>
            <Row
              style={{ fontSize: "18px", fontWeight: 600, width: "100%" }}
              justify="space-between"
            >
              <span>First Name and Last Name</span>
              <span>$45/hr</span>
            </Row>

            <Row>Bachelors in Computing - National University of Singapore</Row>
          </Row>

          <div>
            <Rate value={value} disabled /> 10 Reviews
          </div>
        </Col>
      </Row>

      <div style={{ fontSize: "16px", fontWeight: 600 }}>About me</div>

      <Row>
        Lorem Ipsum is simply dummy text of the printing and typesetting
        industry. Lorem Ipsum has been the industry's standard dummy text ever
        since the 1500s, when an unknown printer took a galley of type and
        scrambled it to make a type specimen book. It has survived not only five
        centuries, but also the leap into electronic typesetting, remaining
        essentially unchanged. It was popularised in the 1960s with the release
        of Letraset sheets containing Lorem Ipsum passages, and more recently
        with desktop publishing software like Aldus PageMaker including versions
        of Lorem Ipsum
      </Row>
    </div>
  );
};

export default BasicDetails;
