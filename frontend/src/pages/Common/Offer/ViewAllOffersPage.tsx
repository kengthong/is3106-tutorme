import React from "react";
import BodyContainer from "../../../components/Layout/BodyContainer";
import Header from "../../../components/Header/Header";
import OfferListComponent from "../../../components/OfferList/OfferList";

const NotFoundPage = () => {
    return (
        <div>
            <Header />
            <BodyContainer>
                <OfferListComponent />
            </BodyContainer>
        </div>
    );
};

export default NotFoundPage;
