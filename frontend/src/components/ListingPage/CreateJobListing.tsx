import { AutoComplete, Checkbox, Form, Input, InputNumber, Menu, Popover, Select, Slider, Tooltip } from 'antd';
import React, { useEffect, useState } from 'react'
import DaysSelectButton from '../Common/DaysSelectButton';

const CreateJobListing = () => {
    const [formData, setFormData] = useState({
        tutorId: -1,
        listingTitle: "",
        subject: { //subject -> level 
            subjectName: "",
            subjectLevel: ""
        },
        rate: 0,
        timeslot: "",
        listingDesc: "",
        area: ""
    });

    const [subjectList, setSubjectList] = useState("");
    const [subjectLevel, setSubjectLevel] = useState("");


    // // get subject list
    // useEffect(() => {
    //     const getListing = (async () => {
    //         const response = await fetch("http://localhost:8080/tutorme-war/SubjectResource/subjectList");
    //         const data = await response.json();
    //         const [item] = data.results;
    //         setSubjectList(item);
    //         console.log(item);
    //     });
    // })

    const verifyForm = () => {
        let a = formData.tutorId != -1;
        let b = formData.listingTitle != "";
        let c = formData.subject.subjectName.length != 0;
        let d = formData.subject.subjectLevel.length != 0;
        let e = formData.rate > 0;
        let f = formData.timeslot != "";
        let g = formData.listingDesc != "";
        return a && b && c && d && e && f && g;
    };

    const handleChange = (e: any) => {
        const name = e && e.target && e.target.name ? e.target.name : "";
        const value = e && e.target && e.target.value ? e.target.value : "";
        setFormData((prevState) => ({
            ...prevState,
            [name]: value,
        }));
        console.log(e);
        console.log(formData);
    }

    const handleSubmit = (e: any) => {
        if (verifyForm()) {
            // create the listing and send to backend
        }
        alert("Form submitted");
    }



    // Probably will have a list of subjects here from {data}
    const subjectName = ["Additional Mathematics", "Elementary Mathematics", "Biology", "Chemistry", "Physics"];
    const days = [
        { label: 'Mon', value: 'Mon' },
        { label: 'Tue', value: 'Tue' },
        { label: 'Wed', value: 'Wed' },
        { label: 'Thu', value: 'Thu' },
        { label: 'Fri', value: 'Fri' },
        { label: 'Sat', value: 'Sat' },
        { label: 'Sun', value: 'Sun' }
    ];

    const layout = {
        labelCol: { span: 8 },
        wrapperCol: { span: 16 },
    };
    const tailLayout = {
        wrapperCol: { offset: 8, span: 16 },
    };


    return (
        <div>
            <h2 style={{ margin: "20px" }}>New Tuition Listing</h2>

            <div>
                <Form
                    {...layout}
                    name="createJobListing"
                    style={{ display: "flex", flexDirection: "column", margin: "20px" }}
                >
                    {/* Listing Title */}
                    <Form.Item
                        label="Listing Title"
                        rules={[{ required: true, message: 'Please input a listing title' }]}
                    >
                        <Input
                            name="listingTitle"
                            onChange={(e) => handleChange(e)}
                        />
                    </Form.Item>


                    {/* Subject */}
                    <Form.Item
                        label="Subject"
                        name="subject"
                        rules={[{ required: true, message: 'Please select a subject' }]}
                    >

                        <Select>

                        </Select>

                    </Form.Item>

                    {/* Hourly Rate */}
                    <Tooltip title="">
                        <Form.Item
                            label="Rates"
                            name="rate"
                            rules={[{ required: true, message: 'Please enter your hourly rate!' }]}
                        >
                            <InputNumber
                                onChange={(e) => handleChange(e)}
                            />  &nbsp;/Hour
                    </Form.Item>
                    </Tooltip>

                    <Form.Item
                        label="Timeslot"
                        name="timeslot"
                        rules={[{ required: true, message: 'Please select the days of availability!' }]}
                    >
                        <Tooltip
                            trigger="click"
                            title="Eg. Mon 10am - 2pm and weekends after 1pm">
                            <Input
                                name="timeslot"
                                placeholder="Enter your preferred timeslots"
                                onChange={(e) => handleChange(e)}
                            >

                            </Input>
                        </Tooltip>

                    </Form.Item>

                    {/* Listing description */}
                    <Form.Item
                        label="Description"
                        name="listingDesc"
                        rules={[{
                            required: true,
                            message: 'Please input a listing description!'
                        }]}
                    >
                        <Input.TextArea
                            name="listingDesc"
                            maxLength={500}
                            onChange={(e) => handleChange(e)}

                        />
                    </Form.Item>



                </Form>
            </div>
        </div>
    )
}

export default CreateJobListing





{/* Available Days
                    <Form.Item
                        label="Available days"
                        name="availableDays"
                        rules={[{ required: true, message: 'Please select the days of availability!' }]}
                    >
                        Available Days
                        <Checkbox.Group
                            name="daysGroup"
                            options={days}
                            onChange={(e) => handleChange(e)}
                        >
                        </Checkbox.Group>
                    </Form.Item> */}
