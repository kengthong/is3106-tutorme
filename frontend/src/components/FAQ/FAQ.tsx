import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import { Card } from 'antd'

export const FAQs = () => {

    return (

        <div className={"custom-container"} style={{ height: '100%' }}>

            <br />

            <Card title="What is TutorMe?" className="col-lg-8 col-sm-8" >
                <p>
                    Tutorme is the premier peer-to-peer platform that focuses on fulfilling your private tuition needs in Singapore.
                    Think of us as the Uber/Grab of the private education sectors where tutors may advertise their services and tutees
                    may engage their preferred tutors for tution services.
                </p>

                <p>
                    We focus mainly on the formal educational levels in Singapore; namely, PSLE, N-Levels, O-Levels, and A-Levels.
                    Tutors are free to advertise whichever subject they may teach in any of these levels.
                </p>
            </Card>

            <br />

            <Card title="Why should I choose Tutorme as a Tutee?" className="col-lg-8 col-sm-8">
                <p>
                    Tutorme is provides a better proposition compared to many of the traditional tuition centers.
                    Our students can expect the following:
                </p>

                <ol>
                    <li>
                        Tutorme is a free market hence the tuition rates are usually more competitive compared to the fixed prices of tuition centers
                    </li>

                    <li>
                        We provide you the flexibility of selecting the tutor of your choice with the time slot of your choice, subjected to your tutor's
                        availability.
                    </li>

                    <li>
                        Our platform services are free for all tutees. Hence, you save on registration costs usually required by tuition centers.
                    </li>

                    <li>
                        You may also negotiate for a fair price with your tutors without any platform fees.
                    </li>

                    <li>
                        You only have to pay your tutors after the completion of each lesson, there is no need to pay them on a subscription-basis.
                        Hence, you are able to easily change your tutor if they do not meet your expectations.
                    </li>
                </ol>
            </Card>

            <br />

            <Card title="Why should I choose Tutorme as a Tutor?" className="col-lg-8 col-sm-8">
                <p>
                    Tutorme is provides a better proposition compared to many of the traditional tuition centers.
                    Our Tutors can expect the following:
                </p>

                <ol>
                    <li>
                        Unlike tuition agencies, we would not be taking any agency fees. Hence, you would be able to join us for free and earn more for your tuition services.
                    </li>
                    <li>
                        We operate much faster compared to tuition agencies, which may take up to 3 days to pair you with a student.
                        Once you join us, you can list your services on our platform immediately.
                    </li>
                    <li>
                        We would not be ignoring any application to join our platform contrary to how agencies may drop application of prospective tutors.
                    </li>
                    <li>
                        You can negotiate for a fair price for your tutoring services with students.
                    </li>
                </ol>
            </Card>

            <br />

            <Card title="Why should I choose a Premium Tutor account?" className="col-lg-8 col-sm-8">
                <ol>
                    <li>
                        If you register for a premium account, we will advertise your services more often as compared to non-premium accounts.
                        Most tutors would get a match within two day of using our premium account as our search engine would advertise your services more often
                        and to more users.
                    </li>
                    <li>
                        We also provide competitive prices for premium accounts over a variety of duration to ensure that you gain the most from our
                        premium services for the right price.
                        Durations include: 1 month, 3 months, 6 months, and 1 year.
                    </li>
                </ol>
            </Card>

            <br />

            <Card title="How do I join TutorMe?" className="col-lg-8 col-sm-8">
                <p>
                    You may join us as either a tutor or a tutee from our registration page, which can be accessed
                    from your navigation bar located on the top of your screen or you can click the following link.
                </p>

                <h4 style={{ justifyContent: "center", display: "flex" }}>
                    <a href="/registration" >JOIN US TODAY! </a>
                </h4>
            </Card>

            <br />

            <Card title="My Tutor did not show up, what can I do?" className="col-lg-8 col-sm-8">
                <p>
                    We recommend that you talk to your tutor again on his tardiness. If he/she does not change,
                    please file a complain on our "Contact Support" page.
                </p>
                <p>
                    We apologise for the inconvenience caused. However, please rest assured that we will hand out the
                    appropriate disciplinary actions against the tutor's account.
                </p>
            </Card>

            <br />

            <Card title="How do we contact you if we need help?" className="col-lg-8 col-sm-8">
                <p>You can email us at support@tutorme.com</p>
            </Card>

            <br />
        </div>

    )

}

export default FAQs;