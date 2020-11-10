import React, { useEffect, useState } from "react";
import { Image, Card, Descriptions, Button, Col, Row } from "antd";
import TuteeProfileComponent from "./TuteeSettingsMenu"

import BasicDetails from "./BasicDetails";
import TuteeService from "../../services/tutee";
import { useSelector } from "react-redux";
import { IRootState } from "../../store";
import { UserState } from "../../reducer/user-reducer";

type tuteeProfileProps = {
  user: tuteeDataType,
  settingsPage?: boolean
}


const TuteeProfile = (props: tuteeProfileProps) => {
  const { user, settingsPage } = props;
  return (
    <div
      style={{
        width: "100%",
        display: "flex",
        flexDirection: "row",
        justifyContent: "center",
      }}
    >
      <div style={{ width: `${settingsPage ? "100%" : "65%"}`, marginRight: "20px" }}>
        <Card>
          <BasicDetails user={user} />
        </Card>
      </div>
      {settingsPage ?
        null :
        <div style={{ width: "35%" }}>
          <Card>
            {/*{user.type === "tutee" ? (*/}
            {/*  <MakeOfferComponent />*/}
            {/*) : null}*/}
          </Card>
        </div>
      }

    </div>
  );
};

export default TuteeProfile;
