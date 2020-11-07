import React from "react";
import Header from "../../components/Header/Header";
import BodyContainer from "../../components/Layout/BodyContainer";
import { FeedbackCom } from "../../components/Feedback/FeedbackCom";
import { Footer } from "../../components/LandingPage/Footer";

const Feedback = () => {
    return (
        <div>
            <Header />
            <BodyContainer>
                <FeedbackCom />
            </BodyContainer>
            <Footer />
        </div>
    );
};

export default Feedback;
