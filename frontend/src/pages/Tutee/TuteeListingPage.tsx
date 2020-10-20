import React from "react";
import Header from '../../components/Header/Header';
import BodyContainer from '../../components/Layout/BodyContainer';
import JobListingDetail from '../../components/ListingPage/JobListingDetail';
import ListingDescription from "../../components/ListingPage/ListingDescription";
import MakeOfferForm from "../../components/Offer/MakeOfferForm";
import { Button, Collapse } from 'antd';
import Review from "../../components/Review/Review";

const TuteeListingPage = () => {
    const { Panel } = Collapse;

    return (
        <div>
            <Header />
            <BodyContainer>
                <div style={{ display: "flex", flexDirection: "column" }}>
                    <div style={{ marginBottom: "20px" }}>
                        <JobListingDetail />
                    </div>

                    <div style={{ display: "flex", marginLeft: "50px" }}>
                        <MakeOfferForm />
                        <Button style={{ margin: "10px" }}>
                            Chat
                        </Button>
                    </div>



                    <div>
                        <Collapse defaultActiveKey={['1']} style={{ marginTop: "50px" }}>
                            <Panel header="Subjects" key="1">
                                <ListingDescription />
                            </Panel>

                            <Panel header="Testimonials" key="2">
                                <Review />
                            </Panel>
                        </Collapse>


                    </div>

                </div>


            </BodyContainer>


        </div>
    );
};

export default TuteeListingPage;
