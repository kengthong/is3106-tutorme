import React from "react";
import Header from "../../../components/Header/Header";
import { SubjectSection } from "../../../components/LandingPage/SubjectSection";
import { InfoBanner } from "../../../components/LandingPage/InfoBanner"
import { InfoBanner2 } from "../../../components/LandingPage/InfoBanner2"
import { Footer } from "../../../components/LandingPage/Footer";

const LandingPage = () => {
  return (
    <div>
      <Header />
      <div>
        <SubjectSection />
        <InfoBanner />
        <InfoBanner2 />
      </div>
      <Footer />

    </div>
  );
};

export default LandingPage;
