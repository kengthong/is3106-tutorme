import React from "react";
import Header from "../../components/Header/Header";
import { LandingPageBodyContainer } from "../../components/Layout/BodyContainer";
import { ContactUs } from "../../components/ContactUs/ContactUs";
import { Footer } from "../../components/LandingPage/Footer";

const ContactUsPage = () => {
    return (
        <div>
            <Header />
            <LandingPageBodyContainer>
                <ContactUs />
            </LandingPageBodyContainer>
            <Footer />

        </div>
    );
};

export default ContactUsPage;
