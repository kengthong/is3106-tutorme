import React from "react";

const BodyContainer = (props: any) => {
  return (
    <div
      style={{
        backgroundColor: "rgb(237 247 255)",
      }}
    >
      <div style={{
          maxWidth: "1260px",
          flex: "auto",
          marginLeft: "auto",
          marginRight: "auto",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          flexDirection: "column",
          paddingLeft: "20px",
          paddingRight: "20px",
      }}>
          {props.children}
      </div>
    </div>
  );
};

export default BodyContainer;
