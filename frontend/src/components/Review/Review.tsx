import React from 'react'
import { Button, Card } from 'antd'
import reviewData from '../../TestData/review.json'

const Review = () => {
    return (
        <div>
            <div style={{ display: "flex", flexDirection: "column", margin: "10px" }}>
                <li>
                    {
                        reviewData
                            .slice(0, 6)
                            .map(review => {
                                return (
                                    <Card
                                        title={review.review_title}
                                        headStyle={{
                                            fontWeight: "bold"
                                        }}
                                        style={{
                                            marginBottom: "10px"
                                        }}
                                    >
                                        <p>
                                            {review.review}
                                        </p>

                                        <h4 style={{ marginTop: "15px" }}>
                                            {review.first_name + " " + review.last_name}
                                        </h4>

                                    </Card>
                                )
                            }
                            )
                    }
                </li>
                <h5 style={{ textAlign: "center", margin: "15px" }}>Latest 5 testimonials are displayed</h5>
                <Button>View All Testimonials</Button>


            </div>
        </div>
    )
}

export default Review
