import React, {useEffect, useState} from "react";
import Header from '../../components/Header/Header';
import {BodyContainer} from '../../components/Layout/BodyContainer';
import JobListingDetail from '../../components/ListingPage/JobListingDetail';
import {JobListingService} from "../../services/JobListing";
import qs from "qs";
import {useLocation} from "react-router-dom";

const ListingPage = () => {
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
