import React, { useEffect, useState } from "react";
import Header from '../../components/Header/Header';
import { BodyContainer } from '../../components/Layout/BodyContainer';
import JobListingDetail from '../../components/ListingPage/JobListingDetail';
import MakeOfferForm from "../../components/Offer/MakeOfferForm";
import { Button, Collapse } from 'antd';
import Review from "../../components/Review/Review";
import { useSelector } from "react-redux";
import { UserState } from "../../reducer/user-reducer";
import { IRootState } from "../../store";
import { TutorService } from "../../services/Tutor";
import { JobListingService } from "../../services/JobListing";
import qs from "qs";
import { useLocation, useHistory } from "react-router-dom";

const ListingPage = () => {
    const userState = useSelector<IRootState, UserState>((state) => state.userReducer);
    const [listingData, setListingData] = useState<jobListingType>();
    const getListingDetails = async () => {
        const params: { [key: string]: any } = qs.parse(location.search.substring(1), { ignoreQueryPrefix: true });
        const data: jobListingType | null = await JobListingService.getJobListing(params.id);
        if (data) {
            setListingData(data);
        }
    }

    useEffect(() => {
        getListingDetails();
    }, []
    )
    const location = useLocation();
    const history = useHistory();

    return (
        <div>
            <Header />
            <BodyContainer>
                <div style={{ display: "flex", flexDirection: "column" }}>
                    <div style={{ marginBottom: "20px" }}>
                        {listingData ? <JobListingDetail listing={listingData} getListingDetails={getListingDetails}/> : null}
                    </div>
                </div>
            </BodyContainer>
        </div>
    );
};

export default ListingPage;
