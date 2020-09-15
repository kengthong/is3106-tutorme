import React from "react";
import BodyContainer from "../../../components/Layout/BodyContainer";
import { useHistory } from "react-router-dom";
import { Button } from "primereact/button";
import logo from "../../../assets/logo.jpg";
import RegistrationComponent from "../../../components/Login/Registration";

const Registration = () => {
  const history = useHistory();
  const handleClick = () => history.push("/login");

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
        <Button
          label="Register new account"
          className="p-button-link"
          onClick={handleClick}
        />
      </BodyContainer>
    </div>
  );
};

export default Registration;
