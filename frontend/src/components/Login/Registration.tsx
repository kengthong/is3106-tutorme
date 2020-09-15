import React, { useEffect, useState, Component } from "react";
import { Button } from "primereact/button";
import { Calendar } from "primereact/calendar";
import { InputText } from "primereact/inputtext";
import { Password } from "primereact/password";
import { RadioButton } from "primereact/radiobutton";

const Registration = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [submitted, setSubmitted] = useState(false);

  function validateForm() {
    return email.length > 0 && password.length > 0;
  }

  function submitForm() {
    return setEmail(email);
  }

  return (
    <form>
      <div className="p-fluid ">
        <div className="p-field p-col-12 p-md-6">
          <label htmlFor="firstname2">First name</label>
          <InputText id="firstname2" type="text" />
        </div>
        <div className="p-field p-col-12 p-md-6">
          <label htmlFor="lastname2">Last name</label>
          <InputText id="lastname2" type="text" />
        </div>
        <div className="p-field ">
          <label htmlFor="email">Email</label>
          <InputText id="email" type="text" />
        </div>
        <div className="p-field ">
          <label htmlFor="email">Password</label>
          <Password id="password" type="text" />
        </div>
        <div className="p-field ">
          <label htmlFor="phonenumber">Phone Number</label>
          <InputText id="phonenumber" type="text" />
        </div>
        <div className="p-field ">
          <label htmlFor="gender">Gender</label>
        </div>
        <div className="p-field-radiobutton">
          <RadioButton value="gen1" name="gender" />
          <label htmlFor="gen1">Male</label>
        </div>
        <div className="p-field-radiobutton">
          <RadioButton value="gen2" name="gender" />
          <label htmlFor="gen2">Female</label>
        </div>
        <div className="p-field ">
          <label htmlFor="dob">Date of Birth</label>
        </div>
      </div>

      <br></br>
      <div className="p-fluid p-formgrid p-grid">
        <Button
          label="Register"
          className="p-button-raised p-pt-4"
          onClick={submitForm}
        />
        <Button label="Login" className="p-button-link" />
      </div>
    </form>
  );
};

export default Registration;
