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
import ReviewsComponent from "../Reviews/Reviews";
import EditJobListing from "./EditJobListing";

const JobListingDetail = (props: any) => {
    const { Panel } = Collapse;

    const userState = useSelector<IRootState, UserState>((state) => state.userReducer);

    function showTuteeButtons() {
        if (userState.currentUser && userState.currentUser.personEnum === "TUTEE") {
            return <div style={{ display: "flex", marginLeft: "50px", marginTop: "15px" }}>
                <MakeOfferForm listing={props.listing} />
                <MakeOffer listing={props.listing} />
            </div>
        } else if (userState.currentUser && userState.currentUser.personEnum === "TUTOR" && userState.currentUser.personId === props.listing.tutor.personId) {
            return <div style={{ marginRight: "30px", marginTop: "15px" }}>
                <EditJobListing listing={props.listing} />
            </div>
        } else {
            return <div />
        }
    }

    return (
        <div className="flex-col" style={{ marginTop: "40px", backgroundColor: "#fff", padding: '48px', minHeight: "calc(100vh - 150px)", maxWidth: "1000px" }}>
            <div style={{ display: "flex" }}>
                <div style={{ marginRight: "80px" }}>
                    {props.listing.tutor.profileImage && props.listing.tutor.profileImage != "" ?
                        <Avatar
                            shape="circle"
                            size={280}
                            src={props.listing.tutor.profileImage}
                        /> :
                        <Avatar
                            shape="circle"
                            size={280}
                            style={{ fontSize: '48px' }}>
                            {props.listing.tutor.firstName.substring(0, 1).toUpperCase()}
                        </Avatar>
                    }


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
                            <p style={{ fontSize: "24px" }}>
                                {props.listing.subjects[0].subjectName}
                            </p>

                            <h4 style={{ marginBottom: "5px" }}>Levels</h4>


                            {props.listing.subjects.map((lvl: any) => (
                                <li>
                                    -{lvl.subjectLevel}
                                </li>
                            ))}


                        </Card>
                    </span>
                </div>
            </div>

            <div>
                <Collapse defaultActiveKey={['1']} style={{ marginTop: "50px", maxWidth: '850px' }}>
                    <Panel header="Description" key="1">
                        <div style={{ padding: '16px 16px' }}>
                            <div className="fs-24" >
                                {props.listing.jobListingTitle}
                            </div>

                            <div className="margin-top-btm-12">
                                {props.listing.jobListingDesc}
                            </div>
                        </div>
                    </Panel>

                    <Panel header="Testimonials" key="2">
                        {/*<Review />*/}
                        <div style={{ padding: '0px 16px' }}>
                            <ReviewsComponent
                                ratings={props.listing.offers} avgRating={props.listing.reviewScore} ratingCount={props.listing.reviewCount}
                                getListingDetails={props.getListingDetails} />
                        </div>
                    </Panel>
                </Collapse>
            </div>


        </div >
    );
};

export default JobListingDetail;

