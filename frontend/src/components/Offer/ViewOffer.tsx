import React, { useEffect, useState } from 'react'
import { Button, Card, Col, Modal, Row } from 'antd';
import reviewData from '../../TestData/review.json'


const ViewOffer = () => {
    const [showOffer, setShowOffer] = useState(false);
    return (
        <div>
            <div>
                <Button type="primary" onClick={() => setShowOffer(true)}>
                    Offer
                </Button>
            </div>


            <div>
                <Modal
                    title="Offers for listing"
                    visible={showOffer}
                    onOk={() => setShowOffer(false)}
                    onCancel={() => setShowOffer(false)}
                    width={600}
                    footer={null}
                >
                    <li >
                        {
                            reviewData.map(offer => {
                                return (
                                    <Card
                                        size="small"
                                        title={offer.review_title}
                                        headStyle={{ fontWeight: "bold" }}
                                        style={{ width: 500, margin: "15px", borderColor: "Black" }}
                                    >
                                        <span style={{ display: "flex", justifyContent: "space-around", flexWrap: "wrap" }}>

                                            <p>
                                                Name : {offer.first_name + " " + offer.last_name}
                                            </p>
                                            <p>
                                                Rate : {"$" + offer.offered_rate}
                                            </p>
                                            <p>
                                                Time: {offer.time_start + " - " + offer.time_end}
                                            </p>
                                            <p>
                                                Frequency: {offer.frequency + "/week"}
                                            </p>

                                        </span>
                                        <span style={{ display: "flex", justifyContent: "space-evenly" }}>
                                            <Button
                                                onClick={() => setShowOffer(false)}
                                                type="primary"
                                            >
                                                Accept
                                             </Button>
                                            <Button
                                                onClick={() => setShowOffer(false)}
                                                type="primary"
                                            >
                                                Reject
                                             </Button>
                                            <Button
                                                onClick={() => setShowOffer(false)}
                                            >
                                                Chat
                                            </Button>
                                        </span>
                                    </Card>
                                )
                            })
                        }
                    </li>

                </Modal>
            </div>
        </div>
    )
}

export default ViewOffer;
