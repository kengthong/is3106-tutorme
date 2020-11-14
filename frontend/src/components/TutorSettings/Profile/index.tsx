import React, {useEffect, useState} from "react";
import { Card } from "antd";

import BasicDetails from "./BasicDetails";
import MakeOfferComponent from "../../MakeOffer/MakeOffer";
import ReviewsComponent from "../../Reviews/Reviews";
import OfferListComponent from "../../Dashboard/OfferList";
import {TutorService} from "../../../services/Tutor";
import {useSelector} from "react-redux";
import {IRootState} from "../../../store";
import {UserState} from "../../../reducer/user-reducer";

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
        {settingsPage?
            null:
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

export default TutorProfileComponent;
