import React from "react";
import Header from "../../components/Header/Header";
import { LandingPageBodyContainer } from "../../components/Layout/BodyContainer";
import { FAQs } from "../../components/FAQ/FAQ";
import { Footer } from "../../components/LandingPage/Footer";

const FAQpage = () => {
    return (
        <div>
            <Header />
            <LandingPageBodyContainer>
                <FAQs />
            </LandingPageBodyContainer>
            <Footer />

        </div>
    );
};

export default FAQpage;
