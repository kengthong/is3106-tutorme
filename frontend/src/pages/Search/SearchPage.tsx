import React from 'react';
import Header from "../../components/Header/Header";
import {BodyContainer} from "../../components/Layout/BodyContainer";
import JobListings from "../../components/Search/JobListings";
import SearchFilter from "../../components/Search/SearchFilter";

const SearchPage = () => {
    return (
        <div>
            <Header />
            <SearchFilter />
            <BodyContainer>
                <JobListings />
            </BodyContainer>
        </div>
    )
}

export default SearchPage;