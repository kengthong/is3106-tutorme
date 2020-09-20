import React from "react";
import Header from "../../../components/Header/Header";
import BodyContainer from "../../../components/Layout/BodyContainer";
import { Col, Row } from "antd";
import TutorSettingsMenu from "../../../components/TutorSettings/TutorMenu";
import EditableDetails from "../../../components/TutorSettings/Details/EditableDetails";

const TutorDetailsPage = () => {
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
            <EditableDetails />
          </Col>
        </Row>
      </BodyContainer>
    </div>
  );
};

export default TutorDetailsPage;
