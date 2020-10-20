import { AutoComplete, Checkbox, Form, Input, InputNumber, Select, Slider, Tooltip } from 'antd';
import React, { useState } from 'react'
import DaysSelectButton from '../Common/DaysSelectButton';

const CreateJobListing = () => {
    const [formData, setFormData] = useState({
        listingTitle: "",
        subject: "",
        levels: [""],
        rate: "",
        availableDay: [],
        listingDesc: ""
    });

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

    // const handleSubmit = (e: any) => {



    // }

    const handleChange = (e: any) => {
        console.log(e);
        const name = e && e.target && e.target.name ? e.target.name : "";
        const value = e && e.target && e.target.value ? e.target.value : "";
        setFormData((prevState) => ({
            ...prevState,
            [name]: value,
        }));

    }

    return (
        <div>
            <div>
                <Form
                    {...layout}
                    name="createJobListing"
                    style={{ display: "flex", flexDirection: "column", margin: "20px" }}
                >

                    <Form.Item
                        label="Listing Title"
                        name="listingTitle"
                        rules={[{ required: true, message: 'Please input a listing title' }]}
                    >
                        <Input
                            onChange={(e) => handleChange(e)}
                        />
                    </Form.Item>

                    <Form.Item
                        label="Subject"
                        name="subject"
                        rules={[{ required: true, message: 'Please select a subject' }]}
                    >
                        {/* <AutoComplete
                            style={{ width: 200 }}
                            options={subjectName}
                            placeholder="Type in and select the subject"
                            filterOption={(inputValue, subjectName) =>
                                subjectName.value.toUpperCase().indexOf(inputValue.toUpperCase()) !== -1
                            }
                        /> */}
                    </Form.Item>

                    <Tooltip title="">
                        <Form.Item
                            label="Hourly Rates (Insert number)"
                            name="hourlyRate"
                            rules={[{ required: true, message: 'Please enter your hourly rate!' }]}
                        >
                            <InputNumber
                                onChange={(e) => handleChange(e)}
                            />  &nbsp;/Hour
                    </Form.Item>
                    </Tooltip>,



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

                    <Form.Item
                        label="Tuition Description"
                        name="listDesc"
                        rules={[{
                            required: true, message: 'Please input some details about the tuition service you are providing so potential students can know more! For example: Topic related information, spoken language, etc'

                        }]}
                    >
                        <Input />
                    </Form.Item>



                </Form>
            </div>
        </div>
    )
}

export default CreateJobListing
