import { AutoComplete, Checkbox, Form, Input, InputNumber, Menu, Select, Slider, Tooltip } from 'antd';
import React, { useState } from 'react'
import DaysSelectButton from '../Common/DaysSelectButton';

const CreateJobListing = () => {
    const [formData, setFormData] = useState({
        tutor: "",
        listingTitle: "",
        subject: {
            id: 0,
            subjectName: "",
            subjectLevel: ""
        },
        rate: 0, // default rate should be whatever is on the listing
        availableDay: [],
        listingDesc: ""
    });


    const verifyForm = () => {
        let a = formData.tutor != "";
        let b = formData.listingTitle != "";
        let c = formData.subject.subjectName.length != 0;
        let d = formData.subject.subjectLevel.length != 0;
        let e = formData.rate > 0;
        let f = formData.availableDay.length != 0;
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
    }



    const handleSubmit = (e: any) => {
        // Method: POST
        // Create the job listing
        // redirect to listing page or offer page
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


                    <Menu>

                        {/* 
                        {formData.subject.map((subject) =>
                            <Menu.Item key={subject.id} />


                        )} */}


                    </Menu>



                    {/* <Form.Item
                   label="Subject Level"
                   name="levels"
                   rules = {[{required:true, message: "Please select a subject level"}]}
                   >

                   </Form.Item> */}


                    {/* Subject */}
                    <Form.Item
                        label="Subject"
                        name="subject"
                        rules={[{ required: true, message: 'Please select a subject' }]}
                    >
                    </Form.Item>

                    {/* Hourly Rate */}
                    <Tooltip title="">
                        <Form.Item
                            label="Hourly Rates (Insert number)"
                            name="rate"
                            rules={[{ required: true, message: 'Please enter your hourly rate!' }]}
                        >
                            <InputNumber
                                onChange={(e) => handleChange(e)}
                            />  &nbsp;/Hour
                    </Form.Item>
                    </Tooltip>

                    {/* Available Days */}
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
                    </Form.Item>

                    {/* Listing description */}
                    <Form.Item
                        label="Tuition Description"
                        name="listingDesc"
                        rules={[{
                            required: true,
                            message: 'Please input some details about the tuition service you are providing so potential students can know more! For example: Topic related information, spoken language, etc'

                        }]}

                    >
                        <Input
                            onChange={(e: any) => handleChange(e)}
                        />
                    </Form.Item>



                </Form>
            </div>
        </div>
    )
}

export default CreateJobListing
