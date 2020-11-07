import React from "react";

const BodyContainer = (props: any) => {
  return (
    <div
      style={{
        backgroundColor: "rgb(237 247 255)",
        minHeight: "calc(100vh - 90px - 285px)"
      }}
    >
      <div className='custom-container'>
          {props.children}
      </div>
    </div>
  );
};

export default BodyContainer;
