import React from "react";

const BodyContainer = (props: any) => {
  return (
    <div
      style={{
            backgroundColor: "rgb(237 247 255)",
      }}
    >
      <div className='custom-container'>
          {props.children}
      </div>
    </div>
  );
};

export default BodyContainer;
