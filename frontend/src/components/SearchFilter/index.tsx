import React from 'react';
import {
    useHistory,
    useLocation,
    useParams
} from "react-router-dom";


import Filter from "./Filter";
import qs from "qs";
import {Row} from "antd";
import {qualifications} from "../../config/constants";
import NameFilter from "./NameFilter";

const Index = () => {
    const history = useHistory();
    const location = useLocation();

    const handleSubmit = () => {
        history.push('/component-two',{params:'Hello World'})
    };

    const params: {[key: string]:any} = qs.parse(location.search.substring(1), { ignoreQueryPrefix: true });

    const handlePush = (params: {[key: string]:any}) => {

        // Form array of query string key value pair. Eg. ['subject=English', 'price=l']
        const paramsList =  Object.keys(params).map((k: string) => '' + k + '=' + params[k]);
        const newPath = location.pathname+'?' + paramsList.join('&');
        history.push(newPath)
    };

    const pathName = location.pathname + '?subject=maths&price=l';

    const filterProps = {
        pushUrl: handlePush,
        params:params,

    };

    return (
        <div style={{width: '100%',border: '1px solid #e8e8e8', backgroundColor: 'white', height: '100px'}} className='flex-row align-center'>
            <div className={'custom-container'}>
                <div className={'flex-row justify-space-around'}>
                    <NameFilter label={'Name'} _key={'name'} {...filterProps} />
                    <Filter label={'Subject'} _key={'subject'} {...filterProps} data={['Maths', 'English', 'Chinese']} />
                    <Filter label={'Level'} _key={'level'} {...filterProps} data={qualifications} />
                    <Filter label={'Price'} _key={'price'} {...filterProps} data={['s', 'm', 'l']} />
                    <Filter label={'Gender'} _key={'gender'} {...filterProps} data={['M','F']} />
                </div>

            </div>
        </div>
    )
};

export default Index;