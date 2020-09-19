import React, { useEffect, useState, Component, FormEvent } from "react";
import { useHistory } from "react-router-dom";
import { REGISTRATION_URL } from "../../config/constants";
import logo from "../../assets/logo.jpg";
import { Input, Button } from "antd";

const Login = () => {
  const history = useHistory();
  const handleRedirectToRegister = () => history.push(REGISTRATION_URL);

  const [formData, setFormData] = useState({
    email: "5",
    password: "43",
  });

  const handleChange = (e: any) => {
    console.log(e);
    const name = e && e.target && e.target.name ? e.target.name : "";
    const value = e && e.target && e.target.value ? e.target.value : "";
    setFormData((prevState) => ({
      ...prevState,
      [name]: value,
    }));

    console.log(formData);
  };

  const handleSubmit = (e: any) => {
    //Should call on
    //Skeleton
    console.log("Submitted");
  };

  return (
    <div>
      <img src={logo} style={{ padding: 100 }} />
      <h1
        style={{
          fontSize: "3rem",
          padding: 50,
        }}
      >
        Welcome, Please log in
      </h1>

      <form onSubmit={handleSubmit}>
        <div className="p-fluid">
          <div className="p-field">
            <label htmlFor="email">Email</label>
            <Input
              name="email"
              id="emailinput"
              type="text"
              onChange={(e) => handleChange(e)}
            />
          </div>
          <div className="p-field">
            <label htmlFor="password">Password</label>
            <Input.Password
              name="password"
              id="passwordinput"
              type="password"
              onChange={(e) => handleChange(e)}
            />
          </div>
          <br></br>
          <div style={{ display: "flex", justifyContent: "center" }}>
            <Button
              type="primary"
              className="p-button-raised p-pt-4"
              onClick={handleSubmit}
            >
              Login
            </Button>
          </div>
          <div style={{ display: "flex", justifyContent: "center" }}>
            <Button
              type="link"
              className="p-button-link"
              onClick={handleRedirectToRegister}
            >
              Registration
            </Button>
          </div>
        </div>
      </form>
    </div>
  );
};

export default Login;
