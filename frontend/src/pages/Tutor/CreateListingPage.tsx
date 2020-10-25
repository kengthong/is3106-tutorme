import React from 'react'
import Header from '../../components/Header/Header'
import BodyContainer from '../../components/Layout/BodyContainer'
import CreateJobListing from '../../components/ListingPage/CreateJobListing'

const CreateListingPage = () => {
    return (
        <div>
            <Header />
            <BodyContainer>
                <div style={{ margin: "20px" }}>
                    <CreateJobListing />
                </div>
            </BodyContainer>
        </div>
    )
}

export default CreateListingPage;
