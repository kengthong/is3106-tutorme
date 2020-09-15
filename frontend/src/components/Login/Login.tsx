import React, { useEffect, useState, Component, FormEvent } from "react";
import { Button } from "primereact/button";
import { InputText } from "primereact/inputtext";
import { Password } from "primereact/password";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const validateForm = () => {
    return email.length > 0 && password.length > 0;
  };

  // function validateForm() {
  //   return email.length > 0 && password.length > 0;
  // }

  const handleSubmit = (e: any) => {
    //Should call on
    //Skeleton
    console.log("Submitted");
  };

  const handleEmailChange = (e: any) => {
    const value = e && e.target && e.target.value ? e.target.value : "";
    setEmail(value);
  };

  const handlePasswordChange = (e: any) => {
    const value = e && e.target && e.target.value ? e.target.value : "";
    setPassword(value);
  };

  // function submitForm() {
  //   return setEmail(email);
  // }

  return (
    <form onSubmit={handleSubmit}>
      <div className="p-fluid">
        <div className="p-field">
          <label htmlFor="email">Email</label>
          <InputText
            id="emailinput"
            keyfilter="email"
            type="text"
            onChange={(e) => handleEmailChange(e)}
          />
        </div>
        <div className="p-field">
          <label htmlFor="password">Password</label>
          <InputText
            id="passwordinput"
            type="password"
            onChange={(e) => handlePasswordChange(e)}
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
      </div>
    </form>
  );
};

export default Login;
