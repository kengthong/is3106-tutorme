import React from "react";
import Header from "../../components/Header/Header";
import BodyContainer from "../../components/Layout/BodyContainer";
import { ContactUs } from "../../components/ContactUs/ContactUs";
import { Footer } from "../../components/LandingPage/Footer";

const ContactUsPage = () => {
    return (
        <div>
            <Header />
            <BodyContainer>
                <ContactUs />
            </BodyContainer>
            <Footer />

        </div>
    );
};

export default ContactUsPage;
