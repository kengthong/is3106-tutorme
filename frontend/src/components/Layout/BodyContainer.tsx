import React from "react";

const BodyContainer = (props: any) => {
  return (
    <div
      style={{
        flex: "auto",
        maxWidth: "996px",
        marginLeft: "auto",
        marginRight: "auto",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        flexDirection: "column",
      }}
    >
      {props.children}
    </div>
  );
};

export default BodyContainer;
