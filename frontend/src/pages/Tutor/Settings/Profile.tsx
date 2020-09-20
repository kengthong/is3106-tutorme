import React from "react";
import Header from "../../../components/Header/Header";
import TutorProfileComponent from "../../../components/TutorSettings/Profile";
import BodyContainer from "../../../components/Layout/BodyContainer";
import { Col, Row } from "antd";
import TutorSettingsMenu from "../../../components/TutorSettings/TutorMenu";

const TutorProfilePage = () => {
  const user = {
    name: "Wayne",
    type: "tutee",
  };

  return (
    <div>
      <Header />
      <BodyContainer>
        <div style={{ padding: "20px 0" }} />
        <Row style={{ width: "100%" }} gutter={20}>
          <Col span={4}>
            <TutorSettingsMenu />
          </Col>
          <Col span={20}>
            <TutorProfileComponent user={user} />
          </Col>
        </Row>
      </BodyContainer>
    </div>
  );
};

export default TutorProfilePage;
