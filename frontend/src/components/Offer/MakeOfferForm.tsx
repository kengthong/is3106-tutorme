import React, {useEffect, useState} from 'react';
import {Button, DatePicker, Form, Input, InputNumber, message, Modal, Select} from 'antd';
import {OfferService} from '../../services/Offer';
import {useSelector} from "react-redux";
import {IRootState} from "../../store";
import {SubjectState} from "../../reducer/subject-reducer";
import {useHistory} from 'react-router-dom';
import {SubjectsService} from '../../services/Subjects';
import moment from 'antd/node_modules/moment';
import {UserState} from '../../reducer/user-reducer';
import {ChatService} from '../../services/Chat';

const MakeOfferForm = (props: any) => {
    const history = useHistory();
    const {Option} = Select;
    const [form] = Form.useForm();

    const userState = useSelector<IRootState, UserState>((state) => state.userReducer);
    const subjectState = useSelector<IRootState, SubjectState>((state) => state.subjectReducer);
    const loadSubjects = async () => {
        await SubjectsService.getAllSubjects();
    }
    useEffect(() => {
        if (!subjectState || !subjectState.uniqueSubjects || subjectState.uniqueSubjects.length === 0) {
            loadSubjects();
        }
    }, [subjectState]);

    const [showOffer, setShowOffer] = useState(false);

    const onFinish = (fieldsValue: any) => {
        const values = {
            ...fieldsValue,
            numSessions: fieldsValue.numSessions.toString(),
            price: fieldsValue.price.toString(),
            startDate: moment(fieldsValue.startDate).format("DD-MM-YYYY")
        }
        createOffer(values);

        const msgValues = {
            currUserId: userState.currentUser?.personId.toString(),
            receiverId: props.listing.tutor.personId.toString(),
            body: "Hi! I have sent you an offer of $" + values.price + " for your listing on " + props.listing.jobListingTitle
        }
        sendMessage(msgValues);
        history.push("/chat");
    };

    const createOffer = async (createOfferParams: any): Promise<void> => {
        const response = await OfferService.createOffer(createOfferParams);
        if (response) {
            return message.success("Successfully made offer!")
        } else {
            return message.error("Error sending offer. Please try again.");
        }
    }

    const sendMessage = async (messageValues: any): Promise<void> => {
        await ChatService.sendMessage(messageValues.senderId, messageValues.receiverId, messageValues.body);
    }


    const tailLayout = {
        wrapperCol: {offset: 8, span: 16},
    };

    return (
        <div style={{display: "flex", flexDirection: "column", margin: "10px"}}>

            <div>
                <Button type="primary" onClick={() => setShowOffer(true)}>
                    Offer
                </Button>
            </div>

            <Modal
                title="Make an offer!"
                visible={showOffer}
                onOk={() => setShowOffer(false)}
                onCancel={() => setShowOffer(false)}
                width={800}
                footer={null}
                style={{display: "flex", flexDirection: "column"}}
            >

                <Form
                    style={{display: "flex", justifyContent: "space-evenly"}}
                    form={form}
                    name="submitOfferForm"
                    onFinish={onFinish}
                >
                    <div>
                        <Form.Item
                            name="jobListingId"
                            initialValue={props.listing.jobListingId.toString()}
                            hidden
                        />

                        <Form.Item
                            name="price"
                            label="Price"
                            initialValue={props.listing.hourlyRates}
                            rules={[{
                                required: true,
                                message: "Please input your offer! (Per hour)"
                            }]}
                        >
                            <InputNumber
                                min={1}
                                required={true}
                            />
                        </Form.Item>

                        <Form.Item
                            name="subject"
                            label="Subjects"
                            initialValue={props.listing.subjects[0].subjectName}
                            rules={[{
                                required: true,
                                message: "Please select a subject!"
                            }]}
                        >
                            <Input
                                disabled
                            />

                        </Form.Item>

                        <Form.Item
                            name="level"
                            label="Subject Level"
                            rules={[{
                                required: true,
                                message: "Please select a subject level!"
                            }]}
                        >

                            <Select
                                showSearch
                                allowClear
                                placeholder="Select your levels"
                            >
                                {props.listing.subjects.map((sub: any, i: any) => (
                                    <Option
                                        key={i}
                                        value={sub.subjectLevel}
                                    >
                                        {sub.subjectLevel}
                                    </Option>
                                ))}
                            </Select>
                        </Form.Item>

                        <Form.Item
                            name="numSessions"
                            label="No. of Sessions per week"
                            rules={[{
                                required: true,
                                message: "Number"
                            }]}
                        >
                            <InputNumber
                                min={1}
                                max={10}
                            />
                        </Form.Item>

                        <Form.Item
                            name="hoursPerSession"
                            label="Duration per session (hour)"
                            rules={[{
                                required: true,
                                message: "Time"
                            }]}
                        >
                            <Input/>
                        </Form.Item>

                        <Form.Item
                            name="startDate"
                            label="Start date of 1st session"
                            rules={[{
                                required: true,
                                message: "Select Date"
                            }]}
                        >
                            <DatePicker format="DD-MM-YYYY"/>

                        </Form.Item>

                        <Form.Item
                            name="notes"
                            label="Additional notes to Tutor"
                            rules={[{
                                required: true,
                                message: "Additional Note"
                            }]}
                        >
                            <Input.TextArea/>
                        </Form.Item>
                    </div>

                    <div style={{marginTop: "330px", marginRight: "30px"}}>
                        <Form.Item {...tailLayout}>
                            <Button
                                type="primary"
                                htmlType="submit"
                            >
                                Submit
                            </Button>
                        </Form.Item>
                    </div>
                </Form>
            </Modal>
        </div>
    )
}

export default MakeOfferForm

