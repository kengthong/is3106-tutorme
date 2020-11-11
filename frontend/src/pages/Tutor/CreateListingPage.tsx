import React from 'react'
import { useSelector } from 'react-redux'
import { useHistory } from 'react-router-dom'
import Header from '../../components/Header/Header'
import { BodyContainer } from '../../components/Layout/BodyContainer'
import CreateJobListing from '../../components/ListingPage/CreateJobListing'
import { UserState } from '../../reducer/user-reducer'
import { IRootState } from '../../store'

const CreateListingPage = () => {
    const userState = useSelector<IRootState, UserState>((state) => state.userReducer);
    const history = useHistory();
    if (userState.currentUser?.personEnum !== "TUTOR") {
        history.push("/error")
    }

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


