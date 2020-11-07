import React, {useEffect, useState} from "react";
import Header from "../../../components/Header/Header";
import { useHistory } from 'react-router-dom';
import BodyContainer from "../../../components/Layout/BodyContainer";
import { Col, Row } from "antd";
import TutorSettingsMenu from "../../../components/TutorSettings/TutorMenu";
import EditableDetails from "../../../components/TutorSettings/Details/EditableDetails";
import {useSelector} from "react-redux";
import {IRootState} from "../../../store";
import {UserState} from "../../../reducer/user-reducer";
import {TutorService} from "../../../services/Tutor";

const TutorDetailsPage = () => {
    const userState = useSelector<IRootState, UserState>((state) => state.userReducer);
    const history = useHistory();
    const [ userData, setUserData] = useState<tutorDataType>();
    useEffect(() => {
        const getTutorDetails = async() => {
            if(userState.currentUser) {
                const data:tutorDataType | null = await TutorService.getTutorDetails(userState.currentUser.personId);
                if(data) {
                    setUserData(data);
                }
            }
        }

        getTutorDetails();
    }, []);
    if(userState.currentUser?.personEnum !== "TUTOR") {
        history.push("/error")
    }
    if(!userData) {
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
                <TutorSettingsMenu />
              </Col>
              <Col span={20}>
                <EditableDetails user={userData}/>
              </Col>
            </Row>
          </BodyContainer>
        </div>
    );
};

export default TutorDetailsPage;
