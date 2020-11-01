import React from "react";
import Header from "../../components/Header/Header";
import BodyContainer from "../../components/Layout/BodyContainer";
import { FAQs } from "../../components/FAQ/FAQ";
import { Footer } from "../../components/LandingPage/Footer";

const FAQpage = () => {
    return (
        <div>
            <Header />
            <BodyContainer>
                <FAQs />
            </BodyContainer>
            <Footer />

        </div>
    );
};

export default FAQpage;
