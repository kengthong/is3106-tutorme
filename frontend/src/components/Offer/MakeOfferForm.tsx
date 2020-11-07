import React, { useEffect, useState } from 'react';
import { Form, Input, Button, Modal, Alert, Menu, Dropdown, Select, InputNumber } from 'antd';
import { useForm } from 'antd/lib/form/Form';


const MakeOfferForm = () => {
    const [formData, setFormData] = useState({
        tutee: "",
        tutor: "",
        price: 0,
        subject: "",
        daysPerWeek: 0,
        duration: 0
    })

    const [showOffer, setShowOffer] = useState(false);
    const [currTutee, setCurrTutee] = useState(-1);
    const [currTutor, setCurrTutor] = useState(-1);
    const { Option } = Select;

    useEffect(() => {
        const getListingSubj = async () => {


        }

    })

    const tailLayout = {
        wrapperCol: { offset: 8, span: 16 },
    };

    const verifyForm = () => {
        let a = formData.tutee != "";
        let b = formData.tutor != "";
        let c = formData.price > 0;
        let d = formData.subject != "";
        let e = formData.daysPerWeek > 0 && formData.daysPerWeek < 8;
        let f = formData.duration > 0;
        return a && b && c && d && e && f;
    };

    const handleSubmit = (e: any) => {
        // send to backend
        console.log(formData);
        alert("submitted!");
    }

    const handleChange = (e: any) => {
        const name = e && e.target && e.target.name ? e.target.name : "";
        const value = e && e.target && e.target.value ? e.target.value : "";
        console.log(name + " and " + value);
        setFormData((prevState) => ({
            ...prevState,
            [name]: value,
        }));
        console.log(formData);
    };


    const testSubj = ["A-maths", "E-Maths", "H2 Maths"];

    return (
        <div style={{ display: "flex", flexDirection: "column", margin: "10px" }}>

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
                style={{ display: "flex", flexDirection: "column" }}
            >

                <Form
                    style={{ display: "flex", justifyContent: "space-evenly" }}
                    name="Submit offer form"
                    scrollToFirstError={true}
                >

                    <Form.Item
                        label="Price"
                        rules={[{
                            required: true,
                            message: "Please input your offer! (Per hour)"
                        }]}
                    >
                        <InputNumber
                            name="price"
                            min={1}
                            required={true}
                            onChange={(e) => handleChange(e)}
                        />
                    </Form.Item>


                    <Form.Item
                        label="Subjects"
                        rules={[{
                            required: true,
                            message: "Please select a subject!"
                        }]}
                    >
                        <Select></Select>

                    </Form.Item>

                    <span>
                        <Form.Item
                            label="Days per week"
                            rules={[{
                                required: true,
                                message: "Number"
                            }]}
                        >
                            <Input
                                name="daysPerWeek"
                                onChange={(e) => handleChange(e)}
                            />
                        </Form.Item>


                        <Form.Item
                            label="Duration per session"
                            name="duration"
                            rules={[{
                                required: true,
                                message: "Time"
                            }]}
                        >
                            <Select
                                defaultValue="1"
                                onChange={(e) => handleChange(e)}>
                                <Option value={1}>1 hour</Option>
                                <Option value={1.5}>1.5 hour </Option>
                                <Option value={2}> 2 hours </Option>
                            </Select>
                        </Form.Item>
                    </span>
                </Form>

                <span style={{ marginTop: "20px", marginRight: "55px", alignItems: "flex-end" }}>
                    <Form.Item {...tailLayout}>
                        <Button
                            type="primary"
                            htmlType="submit"
                            onClick={handleSubmit}
                        >
                            Submit
                    </Button>
                    </Form.Item>
                </span>
            </Modal>
        </div>
    )
}

export default MakeOfferForm