import React from "react";
import {Card} from "antd";

import BasicDetails from "./BasicDetails";
import ReviewsComponent from "../../Reviews/Reviews";

type tutorProfileProps = {
    user: tutorDataType | null,
    offerRatings: offerType[],
    settingsPage?: boolean
}
const TutorProfileComponent = (props: tutorProfileProps) => {
    const { user, offerRatings, settingsPage } = props;
    if(!user) {
        return <div>
            Unable to find user
        </div>
    }
  return (
    <div
      style={{
        width: "100%",
        display: "flex",
        flexDirection: "row",
        justifyContent: "center",
      }}
    >
      <div style={{ width: `${settingsPage? "100%": "65%"}`, marginRight: "20px" }}>
        <Card>
          <BasicDetails user={user} />
          <div style={{height: '50px'}}/>
          <ReviewsComponent ratings={offerRatings} avgRating={user.avgRating} ratingCount={user.ratingCount}/>
        </Card>
      </div>
    </div>
  );
};

export default TutorProfileComponent;
