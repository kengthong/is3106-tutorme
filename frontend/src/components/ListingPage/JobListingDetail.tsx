import React, { useEffect, useState } from "react";
import ProfileIcon from '../../assets/profilepic.jpg';
import { Rate, Avatar, Card, Button } from 'antd';



const JobListingDetail = () => {

    const [formData, setFormData] = useState({
        firstName: "",
        lastName: "",
        rate: 0,
        subject: {
            id: 0,
            subjectName: "",
            subjectLevel: ""
        },
        title: "",
        rating: 0,

    })

    useEffect(() => {
        const getListing = (async () => {
            const response = await fetch("");
            const data = await response.json();
            const [joblisting] = data.results;
            setFormData(joblisting);
        });
    })

    //fetch json, hardcoded to test 
    const firstName = "George";
    const lastName = " Tan";
    const subject = "Mathematics";
    const title = "Math tuition for Secondary and JC levels"
    const rates = "$90 per hour";
    const rating = 5;

    let teachingSubjects = ["A-Level Mathematics", "O-Level Mathematics"];

    return (
        <div style={{ display: "flex", justifyContent: "flex-start", marginTop: "40px" }}>

            <div style={{ display: "flex", flexDirection: "column", marginRight: "80px" }}>
                <Avatar
                    shape="circle"
                    size={280}
                    src={ProfileIcon}
                >
                </Avatar>

                <h3 style={{ margin: "20px", marginLeft: "80px" }}>
                    {firstName} {lastName}
                </h3>
            </div>

            <div style={{ display: "flex", flexDirection: "column", justifyContent: "" }}>

                <span style={{ fontSize: "32px", margin: "6px" }}>
                    {title}
                </span>

                <span style={{ margin: "10px" }}>
                    <Rate
                        value={rating}
                        disabled />
                </span>

                <span>
                    <h4 style={{ marginTop: "30px" }}>
                        Rates: {rates}
                    </h4>
                </span>

                <span style={{}}>
                    <span style={{ margin: "10px", paddingTop: "20px" }}>
                        <Card
                            title="Subjects"
                            headStyle={{
                                fontWeight: "bold"
                            }}
                            style={{
                                borderColor: "Black",
                                fontSize: "16px"
                            }}
                        >
                            {teachingSubjects.map(subj => (
                                <li>{subj}</li>
                            ))}

                        </Card>

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
                    </span>


                </span>
            </div>
        </div >
    );
};

export default JobListingDetail;