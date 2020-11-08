import React from "react";
import Header from "../../components/Header/Header";
import { LandingPageBodyContainer } from "../../components/Layout/BodyContainer";
import { FeedbackCom } from "../../components/Feedback/FeedbackCom";
import { Footer } from "../../components/LandingPage/Footer";

const Feedback = () => {
    return (
        <div>
            <Header />
            <LandingPageBodyContainer>
                <FeedbackCom />
            </LandingPageBodyContainer>
            <Footer />
        </div>
    );
};

export default Feedback;
