import React, { useEffect, useState } from "react";
import Header from "../../../components/Header/Header";
import TuteeProfileComponent from "../../../components/Tutee/TuteeProfileComponent";
import {BodyContainer} from "../../../components/Layout/BodyContainer";
import { Footer } from "../../../components/LandingPage/Footer";
import { Col, Row } from "antd";
import TuteeSettingsMenu from "../../../components/Tutee/TuteeSettingsMenu";
import TuteeService from "../../../services/tutee";
import { useSelector } from "react-redux";
import { IRootState } from "../../../store";
import { UserState } from "../../../reducer/user-reducer";

const TuteeProfilePage = () => {
  const userState = useSelector<IRootState, UserState>((state) => state.userReducer);
  const [userData, setUserData] = useState<{ user: tuteeDataType }>();
  useEffect(() => {
    const getTuteeDetails = async () => {
      if (userState.currentUser) {
        const data: tuteeDataType | null = await TuteeService.getTuteeDetails(userState.currentUser.personId);
        if (data) {
          setUserData({
            user: data,
          })
        }
      }
    }

    getTuteeDetails();
  }, []);

  /** To be uncommented later **/
  if (!userData || (userData && !userData.user)) {
    return (
      <div>
        Loading...
      </div>
    )
  }


  return (
    <div>
      <Header />
      <BodyContainer>
        <div style={{ padding: "20px 0" }} />
        <Row style={{ width: "100%" }} gutter={20}>
          <Col span={4}>
            <TuteeSettingsMenu />
          </Col>
          <Col span={20}>
            <TuteeProfileComponent {...userData} />
          </Col>
        </Row>
      </BodyContainer>
      <Footer />
    </div>
  );
};

export default TuteeProfilePage;
