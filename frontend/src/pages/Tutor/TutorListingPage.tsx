import React from "react";
import Header from '../../components/Header/Header';
import BodyContainer from '../../components/Layout/BodyContainer';
import JobListingDetail from '../../components/ListingPage/JobListingDetail';
import ListingDescription from "../../components/ListingPage/ListingDescription";
import { Button, Collapse } from 'antd';
import Review from "../../components/Review/Review";
import ViewOffer from "../../components/Offer/ViewOffer";

const TutorListingPage = () => {
    const { Panel } = Collapse;

    return (
        <div>
            <Header />
            <BodyContainer>
                <div style={{ display: "flex", flexDirection: "column" }}>
                    <div style={{ marginBottom: "20px" }}>
                        {/* <JobListingDetail /> */}
                    </div>
                    <div style={{ display: "flex", marginLeft: "70px" }}>
                        <ViewOffer />
                        <Button style={{ marginLeft: "20px" }}>
                            Chat
                        </Button>
                    </div>

                    <div>
                        <Collapse defaultActiveKey={['1']} style={{ marginTop: "50px" }}>
                            <Panel header="Description" key="1">
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

export default TutorListingPage;
