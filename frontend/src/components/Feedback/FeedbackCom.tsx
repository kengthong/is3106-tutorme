import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { Input, Button, Card } from 'antd'
import { CANCELLED } from 'dns';

export const FeedbackCom = () => {

    const [formData, setFormData] = useState({
        Subject: "",
        Body: "",
    });

    //To Be implemented
    const handleSubmit = (e: any) => {

    };

    const handleChange = (e: any) => {
        const name = e && e.target && e.target.name ? e.target.name : "";
        const value = e && e.target && e.target.value ? e.target.value : "";
        setFormData((prevState) => ({
            ...prevState,
            [name]: value,
        }));

    };

    return (
        <div className={"custom-container"} style={{ height: '100%' }}>
            <br />
            <Card title="Feedback" className="col-lg-12 col-sm-8" >
                <p>
                    Feedback on any issues you faced or report an incident to our team.
                </p>

                <br />
                <form>
                    <div className="p-fluid ">

                        <label htmlFor="Subject">Subject</label>
                        <Input
                            name="Subject"
                            type="text"
                            onChange={(e) => handleChange(e)}
                            required={true}
                        />

                        <label htmlFor="Body">Message</label>
                        <Input.TextArea
                            name="Body"
                            rows={4}
                            onChange={(e) => handleChange(e)}
                            required={true}
                        />
                    </div>

                    <br></br>
                    <div style={{ display: "flex", justifyContent: "center" }}>
                        <Button type="primary" onClick={handleSubmit}>
                            Submit
                        </Button>
                    </div>
                </form>
            </Card>
            <br />
        </div>

    )

}
export default FeedbackCom;