import React, {useEffect, useState} from "react";
import Header from "../../../components/Header/Header";
import TutorProfileComponent from "../../../components/TutorSettings/Profile";
import {BodyContainer} from "../../../components/Layout/BodyContainer";
import {Col, Row} from "antd";
import TutorSettingsMenu from "../../../components/TutorSettings/TutorMenu";
import {TutorService} from "../../../services/Tutor";
import {useSelector} from "react-redux";
import {IRootState} from "../../../store";
import {UserState} from "../../../reducer/user-reducer";
import {useLocation} from "react-router-dom";
import qs from "qs";

const TutorProfilePage = () => {
    const userState = useSelector<IRootState, UserState>((state) => state.userReducer);
    const [userData, setUserData] = useState<{ user: tutorDataType | null, offerRatings: offerType[] }>();
    const location = useLocation();
    useEffect(() => {
        const getTutorDetails = async () => {
            const params: { [key: string]: any } = qs.parse(location.search, {ignoreQueryPrefix: true});
            console.log("params =", params)
            if (params.id) {
                const data: tutorDataType | null = await TutorService.getTutorDetails(params.id);
                if (data) {
                    let offerRatings: any[] = [];
                    data.jobListings && data.jobListings.forEach(jl => {
                        jl.offers?.forEach(o => {
                            offerRatings.push(o);
                        })
                    })
                    setUserData({
                        user: data,
                        offerRatings
                    })
                } else {
                    setUserData({
                        user: null,
                        offerRatings: []
                    })
                }
            }
        }

        getTutorDetails();
    }, []);
    if (!userData) {
        return (
            <div>
                Loading...
            </div>
        )
    }
  return (
      <div>
          <Header/>
          <BodyContainer>
              <div style={{padding: "20px 0"}}/>
              <Row style={{width: "100%"}} gutter={20}>
                  <Col span={20}>
                      <TutorProfileComponent {...userData} settingsPage={true}/>
                  </Col>
              </Row>
          </BodyContainer>
      </div>
  );
};

export default TutorProfilePage;
