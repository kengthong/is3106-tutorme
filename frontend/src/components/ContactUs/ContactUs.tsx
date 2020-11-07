import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { Card, Button } from 'antd'

export const ContactUs = () => {

    return (
        <div className={"custom-container"} style={{ height: '100%' }}>

            <br />

            <Card title="Contact Us" className="col-lg-12 col-sm-8" >
                <p >
                    You may contact us through any of the following means.
                </p>

                <p>
                    Email: support@tutorme.com
                </p>

                <p>
                    Office Number: +65 6122 3912
                </p>

                <p>
                    Address: Aperia Tower 2, Level 4, 8 Kallang Ave, Singapore 339509
                </p>

                <br />

            </Card>

            <br />
        </div>

    )

}

export default ContactUs;