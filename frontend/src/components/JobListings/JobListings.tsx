import React, {useEffect, useState} from 'react';
import {useLocation} from "react-router-dom";
import qs from "qs";
import TuteeService from "../../services/tutee";
import {Card, Col, Rate, Row} from "antd";

const JobListings = () => {
    const location = useLocation();
    const params: {[key: string]:any} = qs.parse(location.search.substring(1), { ignoreQueryPrefix: true });
    const [jobListingList, setJobListingList] = useState<getJobListingListWithParamResposeProps>([]);
    const [loading, setLoading] = useState(true)
    // let jobListingList: getJobListingListWithParamResposeProps = [];
    useEffect(() => {
        const result: getJobListingListWithParamResposeProps = TuteeService.getJobListingListWithParams({});
        setTimeout(() => {
            setJobListingList(result);
            setLoading(false);
        },1000)
    },[]);
    return (
        <div className={'flex-row justify-center job-listing-list-body'}>
            <Row gutter={30}>
                {loading?
                    [1,2,3,4,5,6].map( (r, i) => <JobListingCard key={i} loading={loading}/>)
                    :
                    jobListingList.map( (r: jobListingType, i: number) => <JobListingCard key={i} {...r} /> )}
            </Row>
        </div>
    )
};

const JobListingCard = (props: jobListingType) => {
    const {name, img, price, reviewCount, reviewScore, education, loading } = props;
    return (
        <Col span={6} style={{marginTop: '20px'}}>
            <Card
                style={{height: '500px', width: '100%', minWidth: 267.5}}
                loading={loading}
                    cover={<img style={{width: '100%', height: '300px', objectFit: 'cover'}} src={img} />}>
                <div style={{display: 'flex', flexDirection: 'column', justifyContent: 'space-between'}}>
                    <div>
                        <Row>
                            <Col span={16} style={{fontWeight: 300, fontSize: '18px'}}>
                                {name}
                            </Col>
                            <Col span={8} style={{fontWeight: 500, fontSize: '16px'}}>
                                $ {price} /hr
                            </Col>
                        </Row>
                        <Row>
                            {education}
                        </Row>
                    </div>
                    <div>
                        <Row align={'middle'} style={{paddingTop: '8px'}}>
                            <Rate value={reviewScore} disabled />
                            <span style={{marginLeft: '4px'}}>
                        {reviewCount} Reviews
                    </span>
                        </Row>
                    </div>
                </div>
            </Card>
        </Col>
    )
}

export default JobListings;