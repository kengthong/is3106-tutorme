import React, {useEffect, useState} from 'react';
import {Button, Select} from "antd";
import {useHistory} from 'react-router-dom';

import {SubjectsService} from "../../services/Subjects";
import {useSelector} from "react-redux";
import {SubjectState} from "../../reducer/subject-reducer";
import {IRootState} from "../../store";


const {Option} = Select;

export const SubjectSection = () => {

    const subjectState = useSelector<IRootState, SubjectState>((state) => state.subjectReducer);
    const [selectedSub, setSelected] = useState<string>("");
    const history = useHistory();
    const loadSubjects = async () => {
        await SubjectsService.getAllSubjects();
    }
    useEffect(() => {
        if (!subjectState || !subjectState.uniqueSubjects || subjectState.uniqueSubjects.length === 0) {
            loadSubjects();
        }
    }, [subjectState]);

    const onChange = (val: string) => {
        setSelected(val);
    };

    const searchSubject = () => {
        if (selectedSub.length > 0) {
            const path = "/search?subject=" + selectedSub;
            history.push(path);
        }
    }

    return (
        <div className={"flex-row"} style={{height: '480px'}}>
            <div className="flex-row justify-end w-50 h-100 subject-section-form align-center"
                 style={{backgroundColor: '#438dc2', position: "relative"}}>
                <div className="flex-col" style={{zIndex: 1, padding: 30, width: "70%"}}>
                    <span className="fs-24" style={{color: '#fff', fontWeight: "bold"}}>
                        Tuition that actually delivers results
                    </span>
                    <br/>

                    <span className="fs-18" style={{color: '#fff'}}>
                        Find your tutors based on the myriad of tuition subjects we support.
                        From Primary School Leaving Examinations to Advanced-Levels, we've got your back!
                    </span>
                    <br/>
                    <div>
                        <Select
                            showSearch
                            style={{width: 200}}
                            placeholder="Search a subject"
                            optionFilterProp="children"
                            // onBlur={onBlur}
                            onChange={onChange}
                            filterOption={(input: string, option: any) =>
                                option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                            }
                        >
                            {subjectState.uniqueSubjects && subjectState.uniqueSubjects.map((sub, i) => (
                                <Option key={i} value={sub}>{sub}</Option>
                            ))}
                        </Select>
                        <Button onClick={searchSubject} type='primary'>
                            Find a tutor
                        </Button>
                    </div>
                </div>
            </div>
            <div style={{flexWrap: "wrap", overflow: "hidden"}}>
                <img src={"https://cdn.mytutor.co.uk/images/uploads/TV-ad-hero-banner-_flipped_.jpg"}
                     style={{objectFit: "cover"}}/>
            </div>
        </div>
    )
}