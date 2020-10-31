import React from "react";
import Header from "../../../components/Header/Header";
import {SubjectSection} from "../../../components/LandingPage/SubjectSection";
import {Footer} from "../../../components/LandingPage/Footer";

const LandingPage = () => {
  return (
    <div>
        <Header />
        <div>
            <SubjectSection />
        </div>
        <Footer />

    </div>
  );
};

export default LandingPage;
