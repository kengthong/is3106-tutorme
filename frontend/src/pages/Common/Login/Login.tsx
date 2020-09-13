import React, { useEffect, useState } from "react";
import { Button } from "primereact/button";
import Header from "../../../components/Header/Header";
import { getLorumIpsum } from "../../../actions/home";
import BodyContainer from "../../../components/Layout/BodyContainer";
const Login = () => {
  const [data, setData] = useState([]);
  useEffect(() => {
    getLorumIpsum("https://baconipsum.com/api/?type=meat-and-filler").then(
      (data) => {
        setData(data);
      }
    );
  }, []);
  return (
    <div>
      <Header />
      <BodyContainer
      // style={{
      //     backgroundColor: "rgb(0, 21, 41)",
      //     color: "#fff",
      //     flex: "auto",
      //     maxWidth: '90%',
      //     marginLeft: 'auto',
      //     marginRight: 'auto',
      //     display: "flex",
      //     alignItems: "center",
      //     justifyContent: "center",
      //     flexDirection: "column",
      // }}
      >
        <h1 style={{ fontSize: "3rem" }}>Welcome, log in</h1>
        <Button label="Log in" className="p-button-raised p-button-rounded" />
        <div>Data retrieved from lorum ipsum:</div>
        <p>{data}</p>
      </BodyContainer>
    </div>
  );
};

export default Login;
