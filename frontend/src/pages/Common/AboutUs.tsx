import React from "react";
import Header from "../../components/Header/Header";
import { LandingPageBodyContainer } from "../../components/Layout/BodyContainer";
import { AboutUs } from "../../components/AboutUs/AboutUs";
import { Footer } from "../../components/LandingPage/Footer";

const FAQpage = () => {
    return (
        <div>
            <Header />
            <LandingPageBodyContainer>
                <AboutUs />
            </LandingPageBodyContainer>
            <Footer />

        </div>
    );
};

export default FAQpage;
