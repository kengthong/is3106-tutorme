import React, { useEffect, useState } from "react";
import { Button } from "primereact/button";
import { InputText } from "primereact/inputtext";
import { Password } from "primereact/password";
import BodyContainer from "../../../components/Layout/BodyContainer";
import logo from "../../../assets/logo.jpg";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [submitted, setSubmitted] = useState(false);

  function validateForm() {
    return email.length > 0 && password.length > 0;
  }

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
        <form>
          <span className="p-float-label p-field">
            <InputText
              id="emailinput"
              value={email}
              onChange={(e) => setEmail(e.toString)}
            />
            <label htmlFor="emailinput">Email</label>
          </span>
          <br></br>
          <span className="p-float-label">
            <Password
              value={password}
              onChange={(e) => setPassword(e.toString)}
            />
            <label htmlFor="password">Password</label>
          </span>
        </form>
        <br></br>
        <Button
          label="Log in"
          className="p-button-raised p-button-rounded"
          //disabled={validateForm()}
          //onClick={setSubmitted(true)}
        />
        <Button label="Register" className="p-button-link" />{" "}
      </BodyContainer>
    </div>
  );
};

export default Login;
