import React from "react";
import Header from "../../components/Header/Header";
import BodyContainer from "../../components/Layout/BodyContainer";
import { AboutUs } from "../../components/AboutUs/AboutUs";
import { Footer } from "../../components/LandingPage/Footer";

const FAQpage = () => {
    return (
        <div>
            <Header />
            <BodyContainer>
                <AboutUs />
            </BodyContainer>
            <Footer />

        </div>
    );
};

export default FAQpage;
