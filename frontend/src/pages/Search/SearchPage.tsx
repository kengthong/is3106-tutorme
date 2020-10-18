import React from 'react';
import Header from "../../components/Header/Header";
import BodyContainer from "../../components/Layout/BodyContainer";
import SearchFilter from "../../components/SearchFilter";
import JobListings from "../../components/JobListings/JobListings";

const SearchPage = () => {
    return (
        <div>
            <Header />
            <SearchFilter />
            <BodyContainer>
                {/*<SearchComponent />*/}
                <JobListings />
            </BodyContainer>
        </div>
    )
}

export default SearchPage;