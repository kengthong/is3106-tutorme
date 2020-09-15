import React, { useEffect, useState, Component } from "react";
import { Button } from "primereact/button";
import { InputText } from "primereact/inputtext";
import { Password } from "primereact/password";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [submitted, setSubmitted] = useState(false);

  function validateForm() {
    return email.length > 0 && password.length > 0;
  }

  function submitForm() {
    return setEmail("Done");
  }

  return (
    <form>
      <div className="p-fluid">
        <div className="p-field">
          <label htmlFor="email">Email</label>
          <InputText id="email" type="text" />
        </div>
        <div className="p-field">
          <label htmlFor="password">Password</label>
          <Password id="password" type="text" />
        </div>
        <br></br>
        <div>
          <Button
            label="Login"
            className="p-button-raised p-pt-4"
            onClick={submitForm}
          />
        </div>
      </div>
    </form>
  );
};

export default Login;
