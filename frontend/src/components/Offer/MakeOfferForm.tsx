import React, { useEffect, useState } from 'react';
import { Form, Input, Button, Modal, Alert, Menu, Dropdown, Select, InputNumber, Radio } from 'antd';
import { useForm } from 'antd/lib/form/Form';


const MakeOfferForm = () => {
    const [formData, setFormData] = useState({
        tuteeId: -1,
        jobListingId: -1,
        price: 0,
        subject: "",
        duration: "",
        remarks: ""
    })

    const [showOffer, setShowOffer] = useState(false);
    const [currTutee, setCurrTutee] = useState(-1);
    const [currTutor, setCurrTutor] = useState(-1);
    const { Option } = Select;

    useEffect(() => {
        const getListing = (async () => {
            const response = await fetch("");
            const data = await response.json();
            const [joblisting] = data.results;
            setFormData(joblisting);
        });
    })


    const tailLayout = {
        wrapperCol: { offset: 8, span: 16 },
    };

    const verifyForm = () => {
        let a = formData.tuteeId != -1;
        let b = formData.jobListingId != -1;
        let c = formData.price > 0;
        let d = formData.subject !== "";
        let e = formData.duration != "";
        let f = formData.remarks != "";
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
        setFormData((prevState) => ({
            ...prevState,
            [name]: value,
        }));
        // console.log("form name: " + name + " and value:" + value);
        console.log(formData)
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
                    onValuesChange={(e) => handleChange(e)}
                >

                    <span>
                        <Form.Item
                            label="Price"
                            rules={[{
                                required: true,
                                message: "Please input your offer! (Per hour)"
                            }]}
                        >
                            <Input
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

                    </span>


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
                            name="duration"
                            label="Duration per session"
                            rules={[{
                                required: true,
                                message: "Time"
                            }]}
                        >
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
        </div >
    )
}

export default MakeOfferForm