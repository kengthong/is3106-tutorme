import React from "react";
import BodyContainer from "../../../components/Layout/BodyContainer";
import logo from "../../../assets/logo.jpg";
import RegistrationComponent from "../../../components/login/Registration";

const Registration = () => {
  return (
    <div>
      <BodyContainer>
        <img src={logo} style={{ padding: 100 }} />
        <h1
          style={{
            fontSize: "3rem",
            padding: 50,
          }}
        >
          Registration page
        </h1>
        <RegistrationComponent />
      </BodyContainer>
    </div>
  );
};

export default Registration;
