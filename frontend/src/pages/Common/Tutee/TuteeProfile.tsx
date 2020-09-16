import React from "react";
import Header from "../../../components/Header/Header";
import BodyContainer from "../../../components/Layout/BodyContainer";
import TuteeProfile from "../../../components/Tutee/TuteeProfile";

const Login = () => {
  return (
    <div>
      <Header />
      <br />
      <BodyContainer>
        <TuteeProfile />
      </BodyContainer>
    </div>
  );
};

export default Login;
