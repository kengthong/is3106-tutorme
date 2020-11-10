import { AutoComplete, Button, Checkbox, Form, Input, InputNumber, Menu, message, Popover, Select, Slider, Tooltip } from 'antd';
import React, { useEffect, useState } from 'react'
import { SubjectsService } from "../../services/Subjects";
import { useSelector } from "react-redux";
import { IRootState } from "../../store";
import { SubjectState } from "../../reducer/subject-reducer";
import { JobListingService } from '../../services/JobListing';

const { Option } = Select;

const CreateJobListing = () => {
    const [form] = Form.useForm();
    const subjectState = useSelector<IRootState, SubjectState>((state) => state.subjectReducer);
    const [levels, setLevels] = useState<string[]>([]);
    const [levelSelected, setLevelSelected] = useState(null);

    const loadSubjects = async () => {
        await SubjectsService.getAllSubjects();
    }
    useEffect(() => {
        if (!subjectState || !subjectState.uniqueSubjects || subjectState.uniqueSubjects.length === 0) {
            loadSubjects();
        }
    }, []);

    const layout = {
        labelCol: { span: 8 },
        wrapperCol: { span: 16 },
    };
    const tailLayout = {
        wrapperCol: { offset: 8, span: 16 },
    };

    const onSelectSubject = (sub: any) => {
        console.log('subject chosen =', sub);
        const availableLevels: string[] = [];
        subjectState.subjects.forEach(s => {
            if (s.subjectName == sub && s.subjectLevel != undefined) {
                availableLevels.push(s.subjectLevel);
            }
        });
        console.log('availableLevels=', availableLevels);
        availableLevels.sort();

        setLevels(availableLevels);
    }

    const onFinish = (fieldsValue: any) => {
        console.log("fieldsValue =", fieldsValue)
        createJobListing(fieldsValue);
    }

    const createJobListing = async (createJobListingParams: any): Promise<void> => {
        const response = await JobListingService.createJobListing(createJobListingParams);
        if (response) {
            return message.success("New listing has been created!")
        } else {
            return message.error("Error creating new job listing")
        }
    }

    return (
        <div>
            <h2 style={{ margin: "20px" }}>New Tuition Listing</h2>

            <div className="p-fluid">
                <Form
                    {...layout}
                    form={form}
                    name="createJobListing"
                    onFinish={onFinish}
                >
                    {/* Listing Title */}
                    <Form.Item
                        label="Listing Title"
                        name="listingTitle"
                        rules={[{ required: true, message: 'Please input a listing title' }]}
                    >
                        <Input />
                    </Form.Item>


                    {/* Subject */}
                    <Form.Item
                        label="Subject"
                        name="subject"
                        rules={[{ required: true, message: 'Please select a subject' }]}
                    >

                        <Select
                            showSearch
                            style={{ width: 200 }}
                            placeholder="Search a subject"
                            optionFilterProp="children"
                            onChange={onSelectSubject}
                            // onBlur={onBlur}
                            filterOption={(input: string, option: any) =>
                                option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                            }
                        >
                            {subjectState.uniqueSubjects && subjectState.uniqueSubjects.map((sub, i) => (
                                <Option key={i} value={sub}>{sub}</Option>
                            ))}
                        </Select>

                    </Form.Item>

                    <Form.Item
                        label="Levels"
                        name="levels"
                        rules={[{ required: true, message: 'Please select the level' }]}
                    >
                        <Select
                            showSearch
                            mode="multiple"
                            allowClear
                            style={{ width: 200 }}
                            placeholder="Select a level"
                            optionFilterProp="children"

                            filterOption={(input: string, option: any) =>
                                option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                            }
                        >
                            {levels.map((l, i) => (
                                <Option key={i} value={l}>{l}</Option>
                            ))}
                        </Select>
                    </Form.Item>

                    {/* Hourly Rate */}
                    <Tooltip title="">
                        <Form.Item
                            label="Rates ($/hr)"
                            name="rate"
                            rules={[{ required: true, message: 'Please enter your hourly rate!' }]}
                        >
                            <InputNumber
                                min={1}
                            />
                        </Form.Item>
                    </Tooltip>

                    <Tooltip
                        trigger="click"
                        title="Eg. Mon 10am - 2pm and weekends after 1pm">
                        <Form.Item
                            label="Timeslot"
                            name="timeslot"
                            rules={[{ required: true, message: 'Please select the days of availability!' }]}
                        >

                            <Input
                                name="timeslot"
                                placeholder="Enter your preferred timeslots"
                            >

                            </Input>

                        </Form.Item>
                    </Tooltip>

                    <Form.Item
                        label="Area"
                        name="area"
                        rules={[{
                            required: true,
                            message: 'Please input your location!'
                        }]}
                    >
                        <Input
                            name="location"
                            placeholder="Enter your preferred locations"
                        />
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
                        />
                    </Form.Item>

                    <Form.Item {...tailLayout}>
                        <Button type="primary" htmlType="submit">
                            Submit
                        </Button>
                    </Form.Item>

                </Form>
            </div>
        </div>
    )
}

export default CreateJobListing

