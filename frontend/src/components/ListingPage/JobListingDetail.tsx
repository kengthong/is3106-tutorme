import React, { useEffect, useState } from "react";
import ProfileIcon from '../../assets/profilepic.jpg';
import { Rate, Avatar, Card, Button, Collapse } from 'antd';
import { useHistory, useLocation } from "react-router-dom";
import qs from "qs";
import { JobListingService } from "../../services/JobListing";
import { title } from "process";
import Review from "../Review/Review";
import ListingDescription from "./ListingDescription";
import MakeOfferForm from "../Offer/MakeOfferForm";

type jobListingDetailProps = {
    listing: jobListingType
}

const JobListingDetail = (props: any) => {
    const { Panel } = Collapse;
    console.log(props);
    const { activeStatus, areas, createdDate, hourlyRates, jobListingDesc, jobListingId, reviewCount, reviewScore, subjects, timeslots, tutor, offers } = props.listing;
    const [loading, setLoading] = useState(true)

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

                    <div style={{ display: "flex", marginLeft: "50px", marginTop: "15px" }}>
                        <MakeOfferForm listing={props.listing} />
                        <Button style={{ margin: "10px" }}>
                            Chat
                        </Button>
                    </div>

                    {/* <h3 style={{ margin: "20px", marginLeft: "80px" }}>
                    Review: {reviewCount}
                </h3> */}
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
                        <ListingDescription />
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




// // const [formData, setFormData] = useState({
// //     firstName: "",
// //     lastName: "",
// //     rate: 0,
// //     subject: {
// //         id: 0,
// //         subjectName: "",
// //         subjectLevel: ""
// //     },
// //     title: "",
// //     rating: 0,

// // })

// // useEffect(() => {
// //     const getListing = (async () => {
// //         const response = await fetch("");
// //         const data = await response.json();
// //         const [joblisting] = data.results;
// //         setFormData(joblisting);
// //     });
// // })

// //fetch json, hardcoded to test 
// const firstName = "George";
// const lastName = " Tan";
// const subject = "Mathematics";
// const title = "Math tuition for Secondary and JC levels"
// const rates = "$90 per hour";
// const rating = 5;

// let teachingSubjects = ["A-Level Mathematics", "O-Level Mathematics"];

// const offerData = {

// }

{/* {teachingSubjects.map(subj => (
                                <li>{subj}</li>
                            ))} */}


{/* <Card
                            title="Qualifications"
                            headStyle={{
                                fontWeight: "bold"
                            }}
                            style={{
                                borderColor: "Black",
                                fontSize: "16px",
                                marginLeft: "10px"
                            }}
                        >
                            {qualifications.map(subj => (
                                <li>{subj}</li>
                            ))}
                        </Card> */}