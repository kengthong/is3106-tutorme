import React from "react";
import { useHistory } from "react-router-dom";
import { Button } from "primereact/button";
import BodyContainer from "../../../components/Layout/BodyContainer";
import logo from "../../../assets/logo.jpg";
import LoginComponent from "../../../components/Login/Login";

const Login = () => {
  const history = useHistory();
  const handleClick = () => history.push("/registration");

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
          Welcome, Please log in
        </h1>
        <LoginComponent />
        <Button
          label="Register new account"
          className="p-button-link"
          onClick={handleClick}
        />
      </BodyContainer>
    </div>
  );
};

export default Login;
