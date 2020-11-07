import React, { useEffect, useState } from 'react';
import { Button, Select } from "antd";
import { useHistory } from 'react-router-dom';

import { SubjectsService } from "../../services/Subjects";
import { useSelector } from "react-redux";
import { UserState } from "../../reducer/user-reducer";
import { SubjectState } from "../../reducer/subject-reducer";
import { IRootState } from "../../store";
import { inherits } from 'util';


const { Option } = Select;

export const InfoBanner = () => {

    return (
        <div className={"flex-row"} style={{ height: '480px' }}>
            <div style={{ flexWrap: "wrap", overflow: "hidden" }}>
                <img src={"https://s3-ap-southeast-2.amazonaws.com/geg-gug-webapp/images/1557364192-work_while_you_study_banner.jpg"} style={{ height: "480px", width: "1800px" }} />
            </div>
            <div className="flex-row align-center" style={{ backgroundColor: "#009696", width: "100%", justifyContent: "center" }}>

                <div className="flex-col" style={{ zIndex: 1, padding: 30, width: "70%" }}>
                    <span className="fs-24" style={{ color: '#fff', fontWeight: "bold" }}>
                        Handpicked Tutors, Tailored Experience
                    </span>
                    <br />
                    <span className="fs-18" style={{ color: '#fff' }}>
                        We offer insane flexibility, you can choose your own tutors and plan your own roadmap.
                        Students can easily communicate with the tutors through the messaging system, negotiate the
                        tutoring terms and conditions and make an offer.
                    </span>
                </div>

            </div>
        </div>
    )
}