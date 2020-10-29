import React from 'react';
import Header from "../../components/Header/Header";
import BodyContainer from "../../components/Layout/BodyContainer";
import JobListings from "../../components/Search/JobListings";
import SearchFilter from "../../components/Search/SearchFilter";
// import JobListings from "../../components/JobListings/JobListings";

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