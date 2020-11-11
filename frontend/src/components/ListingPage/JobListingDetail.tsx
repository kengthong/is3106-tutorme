import React, { useEffect, useState } from "react";
import ProfileIcon from '../../assets/profilepic.jpg';
import { Rate, Avatar, Card, Button, Collapse } from 'antd';
import { useHistory, useLocation } from "react-router-dom";
import qs from "qs";
import Review from "../Review/Review";
import MakeOfferForm from "../Offer/MakeOfferForm";
import MakeOffer from "../MakeOffer/MakeOffer"
import { useSelector } from "react-redux";
import { UserState } from "../../reducer/user-reducer";
import { IRootState } from "../../store";

type jobListingDetailProps = {
    listing: jobListingType
}

const JobListingDetail = (props: any) => {
    const { Panel } = Collapse;
    console.log(props);
    const { activeStatus, areas, createdDate, hourlyRates, jobListingDesc, jobListingId, reviewCount, reviewScore, subjects, timeslots, tutor, offers } = props.listing;
    const userState = useSelector<IRootState, UserState>((state) => state.userReducer);
    const [loading, setLoading] = useState(true)

    function showTuteeButtons() {
        if (userState.currentUser && userState.currentUser.personEnum === "TUTEE") {
            return <div style={{ display: "flex", marginLeft: "50px", marginTop: "15px" }}>
                <MakeOfferForm listing={props.listing} />
                <MakeOffer listing={props.listing} />
            </div>
        } else {
            return <div></div>
        }
    }



    return (
        <div style={{ display: "flex", flexDirection: "column", justifyContent: "flex-start", marginTop: "40px" }}>
            <div style={{ display: "flex" }}>
                <div style={{ marginRight: "80px" }}>
                    <Avatar
                        shape="circle"
                        size={280}
                        src={ProfileIcon}
                    >
                    </Avatar>

                    {showTuteeButtons()}
                    {/* <div style={{ display: "flex", marginLeft: "50px", marginTop: "15px" }}>
                        <MakeOfferForm listing={props.listing} />
                        <MakeOffer listing={props.listing} />
                    </div> */}
                </div>

                {/* tutor name */}
                <div style={{ display: "flex", flexDirection: "column", justifyContent: "" }}>
                    <span style={{ fontSize: "32px", margin: "6px" }}>
                        {props.listing.tutor.firstName} {props.listing.tutor.lastName}
                    </span>

                    <span style={{ margin: "10px" }}>
                        <Rate
                            value={props.listing.reviewScore}
                            disabled />

                      &nbsp;({props.listing.reviewCount})
                </span>
                    <span>
                        <h4 style={{ marginTop: "20px", marginLeft: "10px" }}>
                            Rates: ${props.listing.hourlyRates} /h
                    </h4>
                    </span>

                    <span style={{ margin: "10px", paddingTop: "10px", width: "500px" }}>
                        <Card
                            title="Subjects and Levels"
                            headStyle={{
                                fontWeight: "bold"
                            }}
                            style={{
                                borderColor: "Black",
                                fontSize: "16px"
                            }}
                        >
                            <p style={{ fontSize: "20px" }}>
                                {props.listing.subjects[0].subjectName}
                            </p>

                            <h4>Levels</h4>
                            <li style={{ fontSize: "16px" }}>
                                - {props.listing.subjects[0].subjectLevel}
                            </li>

                        </Card>
                    </span>
                </div>
            </div>

            <div>
                <Collapse defaultActiveKey={['1']} style={{ marginTop: "50px" }}>
                    <Panel header="Description" key="1">
                        <Card
                            title={props.listing.jobListingTitle}
                            headStyle={{
                                fontWeight: "bold"
                            }}
                        >
                            <p> {props.listing.jobListingDesc} </p>
                        </Card>
                    </Panel>

                    <Panel header="Testimonials" key="2">
                        <Review />
                    </Panel>
                </Collapse>
            </div>


        </div >
    );
};

export default JobListingDetail;

