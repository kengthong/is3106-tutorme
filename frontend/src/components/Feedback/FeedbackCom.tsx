import React, {useState} from 'react';
import {useHistory} from 'react-router-dom';
import {Button, Card, Input, message} from 'antd'
import {UserService} from '../../services/User';

export const FeedbackCom = () => {

    const history = useHistory();

    const [formData, setFormData] = useState({
        body: "",
    });

    //To Be implemented
    const handleSubmit = async (e: any): Promise<void> => {
        const response = await UserService.sendFeedback(formData.body);
        if (response) {
            message.success("Successfully sent your feedback to Customer Care")
            history.push("/chat");
        } else {
            message.error("Error: Unable to Send");
        }

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
            <Card title="Feedback Page" className="col-lg-12 col-sm-8" >
                <p>
                    Let us know of any issues you faced or report an incident to our team.
                </p>

                <form>
                    <div className="p-fluid ">

                        <label htmlFor="Body">Message</label>
                        <Input.TextArea
                            name="body"
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