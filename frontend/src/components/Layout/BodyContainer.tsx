import React from "react";

export const LandingPageBodyContainer = (props: any) => {
    return (
        <div
            style={{
                backgroundColor: "rgb(237 247 255)",
                minHeight: "calc(100vh - 90px - 285px)",
                width: '100%'
            }}
        >
            <div className='custom-container'>
                {props.children}
            </div>
        </div>
    );
}
export const BodyContainer = (props: any) => {
  return (
    <div
      style={{
        backgroundColor: "rgb(237 247 255)",
        minHeight: "calc(100vh - 90px)",
          width: '100%'
      }}
    >
      <div className='custom-container'>
          {props.children}
      </div>
    </div>
  );
};