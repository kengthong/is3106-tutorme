import React from "react";
import BodyContainer from "../../../components/Layout/BodyContainer";
import logo from "../../../assets/logo.jpg";
import LoginComponent from "../../../components/login/Login";

const Login = () => {
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
      </BodyContainer>
    </div>
  );
};

export default Login;
