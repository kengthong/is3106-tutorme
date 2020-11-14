import React from 'react';
import {Button, Card} from 'antd'

export const AboutUs = () => {

    return (
        <div className={"custom-container"} style={{ height: '100%' }}>

            <br />

            <Card title="About Us" className="col-lg-10 col-sm-8" >
                <p >
                    TutorMe is a Carousell-inspired platform that partners with hundreds of high-quality tutors and the app allows students
                    and parents to find suitable tutors in seconds. The application allows students and parents the ability to search for
                    tutors based on subjects and the student's current level of education.
                </p>

                <p>
                    Students can rapidly communicate with the tutors through the messaging system, negotiate the tutoring terms and conditions
                    and make an offer. The tutor will then accept on their end and a contract is formed. The TutorMe administrator can ensure
                    that everyone honors with agreed contracts and this possible with the in-built reporting system.
                </p>

                <p>
                    This removes the need for a matching agency and its a win-win situation for both students and tutors. Students get to browse as
                    many suitable tutors and make multiple bids. As for tutors, you are not required to pay any agency fees like traditional matching platforms.
                </p>

                <br />

                <Button type="primary" style={{ alignSelf: "center" }} href="/registration" >
                    Sign up for an account now!
                </Button>
            </Card>

            <br />
        </div>

    )

}

export default AboutUs;