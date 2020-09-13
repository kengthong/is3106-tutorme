import React from "react";
import BodyContainer from "../../../components/Layout/BodyContainer";
import Header from "../../../components/Header/Header";

const NotFoundPage = () => {
  return (
    <div>
      <Header />
      <BodyContainer>
        <div
          style={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            fontSize: "20px",
            paddingTop: "30%",
          }}
        >
          <span>Error 404</span>
          <span>Unable to find page</span>
        </div>
      </BodyContainer>
    </div>
  );
};

export default NotFoundPage;
