import React, { useEffect, useState } from 'react';
import { Form, Input, Button, Modal, Alert } from 'antd';
import { useForm } from 'antd/lib/form/Form';


const MakeOfferForm = () => {
    const [offerRate, setOfferRate] = useState(10);
    const [submitOffer, setSubmitOffer] = useState(false);
    const [showOffer, setShowOffer] = useState(false);

    const tailLayout = {
        wrapperCol: { offset: 8, span: 16 },
    };

    function submitSuccess() {
        setShowOffer(false);
        console.log("success!")
    }

    function submitError() {
        console.log("fail!")

    }

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
                    initialValues={{ price: offerRate, days: 2, duration: "2h" }}
                    onFinish={submitSuccess}
                    onFinishFailed={submitError}
                    scrollToFirstError={true}

                >
                    <Form.Item
                        label="Price"
                        name="price"
                        rules={[{ required: true, message: "Please input your offer! (Per hour)" }]}
                    >
                        <Input />
                    </Form.Item>

                    <span>
                        <Form.Item
                            label="Days per week"
                            name="days"
                            rules={[{ required: true, message: "Number" }]}
                        >
                            <Input />
                        </Form.Item>


                        <Form.Item
                            label="Duration per session"
                            name="duration"
                            rules={[{ required: true, message: "Time" }]}
                        >
                            <Input />
                        </Form.Item>
                    </span>
                </Form>

                <span style={{ marginTop: "20px", marginRight: "55px", alignItems: "flex-end" }}>
                    <Form.Item {...tailLayout}>
                        <Button
                            type="primary"
                            htmlType="submit"
                            onClick={submitSuccess}
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