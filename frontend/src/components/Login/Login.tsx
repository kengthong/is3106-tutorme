import React, { useEffect, useState, Component, FormEvent } from "react";
import { Button } from "primereact/button";
import { InputText } from "primereact/inputtext";
import { useHistory } from "react-router-dom";
import { REGISTRATION_URL } from "../../config/constants";

const Login = () => {
  const history = useHistory();
  const handleRedirectToLogin = () => history.push(REGISTRATION_URL);

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
    <form onSubmit={handleSubmit}>
      <div className="p-fluid">
        <div className="p-field">
          <label htmlFor="email">Email</label>
          <InputText
            name="email"
            id="emailinput"
            keyfilter="email"
            type="text"
            onChange={(e) => handleChange(e)}
          />
        </div>
        <div className="p-field">
          <label htmlFor="password">Password</label>
          <InputText
            name="password"
            id="passwordinput"
            type="password"
            onChange={(e) => handleChange(e)}
          />
        </div>
        <br></br>
        <div>
          <Button
            label="Login"
            className="p-button-raised p-pt-4"
            onClick={handleSubmit}
          />
        </div>
        <div style={{ display: "flex", justifyContent: "center" }}>
          <Button
            label="Login"
            className="p-button-link"
            onClick={handleRedirectToLogin}
          />
        </div>
      </div>
    </form>
  );
};

export default Login;
