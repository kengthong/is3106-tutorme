import React, {useEffect, useState} from 'react';
import {useHistory, useLocation} from "react-router-dom";
import qs from "qs";
import {Avatar, Card, Col, Rate, Row} from "antd";
import {JobListingService} from "../../services/JobListing";

const JobListings = () => {
    const location = useLocation();
    const history = useHistory();

    const [jobListingList, setJobListingList] = useState<getJobListingListWithParamResposeProps>([]);
    const [loading, setLoading] = useState(true)
    const handleClick = (id?: number ) => {
        if(id) {
            const newPath = '/job?id=' + id;
            history.push(newPath)
        }
    }
    const getJobListings = async() => {
        const params: {[key: string]:any} = qs.parse(location.search.substring(1), { ignoreQueryPrefix: true });
        const result: getJobListingListWithParamResposeProps = await JobListingService.getJobListingListWithParams(params);
        const jobListings = result.filter(j => j.activeStatus)
        setJobListingList(jobListings);
        setLoading(false);
    }
    useEffect(() => {
        getJobListings();
    },[getJobListings, location]);
    return (
        <div className={'flex-row justify-center job-listing-list-body'}>
            <Row gutter={30} style={{width: '100%'}}>
                {loading?
                    [1,2,3,4,5,6].map( (r, i) => <JobListingCard key={i} loading={loading}/>)
                    :
                    jobListingList.map( (r: jobListingType, i: number) => <JobListingCard handleClick={handleClick} key={i} {...r} /> )}
            </Row>
        </div>
    )
};

const JobListingCard = (props: jobListingCardProps) => {
    const { jobListingId, tutor , hourlyRates, jobListingDesc, createdDate,  reviewCount, reviewScore, loading, handleClick, subjects } = props;
    const profileImage = tutor && tutor.profileImage || "";
    const date = createdDate ? new Date(createdDate.split("[")[0]).toLocaleDateString("en-US") : "";
    const firstName = tutor && tutor.firstName || "";
    return (
        <Col span={6} style={{marginTop: '20px'}}>
            <Card
                onClick={() => handleClick && handleClick(jobListingId) || null}
                style={{width: '100%', minWidth: 267.5}}
                className={"clickable"}
                loading={loading}
                    cover={
                        profileImage && profileImage != ""?
                            <img style={{width: '100%', height: '300px', objectFit: 'cover'}} src={profileImage} alt={profileImage} />
                            :
                        <Avatar
                            className="flex-row align-center"
                            shape="square"
                            style={{width: '100%', height: '300px', fontSize: '48px'}}>
                            {tutor && tutor.firstName.substring(0,1).toUpperCase()}
                        </Avatar>
                    }>
                <div style={{display: 'flex', flexDirection: 'column', justifyContent: 'space-between', height: '150px'}}>
                    <div>
                        <Row>
                            <Col span={16} style={{fontWeight: 300, fontSize: '18px'}}>
                                {firstName}
                            </Col>
                            <Col span={8} style={{fontWeight: 500, fontSize: '16px'}}>
                                $ {hourlyRates} /hr
                            </Col>
                        </Row>
                        <Row style={{marginTop: '8px'}}>
                            <span style={{fontSize: '16px', fontWeight: 'bold'}}>
                                {subjects && subjects.length && subjects[0].subjectName || "" }
                            </span>
                        </Row>
                        <Row>
                            {jobListingDesc}
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
                    <div className='flex-row justify-end'>
                        {date}
                    </div>
                </div>
            </Card>
        </Col>
    )
};

export default JobListings;