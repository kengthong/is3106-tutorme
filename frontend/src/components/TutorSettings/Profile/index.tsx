import React from "react";
import { Card } from "antd";

import BasicDetails from "./BasicDetails";
import MakeOfferComponent from "../../MakeOffer/MakeOffer";
import ReviewsComponent from "../../Reviews/Reviews";
import OfferListComponent from "../../OfferList/OfferList";

type TutorProfileComponentProps = {
  user: { name: string; type: string };
};

const TutorProfileComponent = (props: TutorProfileComponentProps) => {
  const { user } = props;
  return (
    <div
      style={{
        width: "100%",
        display: "flex",
        flexDirection: "row",
        justifyContent: "center",
      }}
    >
      <div style={{ width: "65%", marginRight: "20px" }}>
        <Card>
          <BasicDetails />
          <ReviewsComponent />
        </Card>
      </div>
      <div style={{ width: "35%" }}>
        <Card>
          {user.type === "tutee" ? (
            <MakeOfferComponent />
          ) : (
            <OfferListComponent />
          )}
        </Card>
      </div>
    </div>
  );
};

export default TutorProfileComponent;
