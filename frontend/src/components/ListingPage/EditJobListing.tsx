import {Button, Form, Input, InputNumber, message, Modal, Select, Tooltip} from 'antd';
import React, {useEffect, useState} from 'react'
import {JobListingService} from '../../services/JobListing';
import {useHistory} from 'react-router-dom';
import moment from 'antd/node_modules/moment';

const EditJobListing = (props: any) => {
    const jobListing = props.listing;
    const [form] = Form.useForm();

    const [showEdit, setShowEdit] = useState(false);


    useEffect(() => {
        form.setFieldsValue({
            ...jobListing,
            timeslot: moment(jobListing.timeslot && jobListing.timeslot.split("[")[0]).format("DD-MM-YYYY")
        });
    }, [form, jobListing]);


    const onFinish = (fieldsValue: any) => {
        const values = {
            jobListingId: fieldsValue.jobListingId.toString(),
            listingTitle: fieldsValue.jobListingTitle,
            listingDesc: fieldsValue.jobListingDesc,
            area: fieldsValue.areas,
            timeslot: fieldsValue.timeslot,
            rate: fieldsValue.hourlyRates
        }
        editJobListing(values);
    }

    const editJobListing = async (editJobListingParams: any): Promise<void> => {
        const response = await JobListingService.editJobListing(editJobListingParams);
        if (response) {
            return message.success("Successfully updated job listing!")
        } else {
            return message.error("Error updating new job listing")
        }
    }

    return (
        <div>
            <div>
                <Button type="primary" onClick={() => setShowEdit(true)}>
                    Edit Listing
                </Button>
            </div>

            <Modal
                title="Edit Listing"
                visible={showEdit}
                onOk={() => setShowEdit(false)}
                onCancel={() => setShowEdit(false)}
                width={800}
                footer={null}
                style={{display: "flex", flexDirection: "column"}}
            >
                <Form
                    form={form}
                    onFinish={onFinish}
                >

                    {/* Listing Title */}
                    <Form.Item
                        label="Listing Title"
                        name="jobListingTitle"
                        rules={[{required: true, message: 'Please input a listing title'}]}
                    >
                        <Input/>
                    </Form.Item>

                    {/* Hourly Rate */}
                    <Tooltip title="">
                        <Form.Item
                            label="Rates ($/hr)"
                            name="hourlyRates"
                            rules={[{required: true, message: 'Please enter your hourly rate!'}]}
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
                            rules={[{required: true, message: 'Please select the days of availability!'}]}
                        >
                            <Input
                                placeholder="Enter your preferred timeslots"
                            >
                            </Input>
                        </Form.Item>
                    </Tooltip>

                    <Form.Item
                        label="Area"
                        name="areas"
                        rules={[{
                            required: true,
                            message: 'Please input your location!'
                        }]}
                    >
                        <Input

                            placeholder="Enter your preferred locations"
                        />
                    </Form.Item>

                    {/* Listing description */}
                    <Form.Item
                        label="Description"
                        name="jobListingDesc"
                        rules={[{
                            required: true,
                            message: 'Please input a listing description!'
                        }]}
                    >
                        <Input.TextArea
                            maxLength={500}
                        />
                    </Form.Item>

                    <Form.Item
                        name="jobListingId"
                        hidden
                    />

                    <Form.Item>
                        <Button type="primary" htmlType="submit">
                            Submit
                        </Button>
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    )
}

export default EditJobListing;

