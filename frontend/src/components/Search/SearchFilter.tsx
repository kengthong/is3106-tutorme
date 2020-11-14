import React, {useEffect} from 'react';
import {useHistory, useLocation} from "react-router-dom";


import Filter from "./Filter";
import qs from "qs";
import {levels} from "../../config/constants";
import NameFilter from "./NameFilter";
import {useSelector} from "react-redux";
import {SubjectState} from "../../reducer/subject-reducer";
import {SubjectsService} from "../../services/Subjects";
import {IRootState} from "../../store";

const SearchFilter = () => {
    const history = useHistory();
    const location = useLocation();
    const subjectState = useSelector<IRootState,SubjectState>((state) => state.subjectReducer);
    const loadSubjects = async() => {
        await SubjectsService.getAllSubjects();
    };

    useEffect(() => {
        if(!subjectState || !subjectState.uniqueSubjects || subjectState.uniqueSubjects.length === 0) {
            loadSubjects();
        }
    },[subjectState]);

    const params: {[key: string]:any} = qs.parse(location.search.substring(1), { ignoreQueryPrefix: true });

    const handlePush = (params: {[key: string]:any}) => {

        // Form array of query string key value pair. Eg. ['subject=English', 'price=l']
        const paramsList =  Object.keys(params).map((k: string) => '' + k + '=' + params[k]);
        const newPath = location.pathname+'?' + paramsList.join('&');
        history.push(newPath)
    };

    const filterProps = {
        pushUrl: handlePush,
        params:params,

    };

    return (
        <div style={{width: '100%',border: '1px solid #e8e8e8', backgroundColor: 'white', height: '100px'}} className='flex-row align-center'>
            <div className={'custom-container'}>
                <div className={'flex-row justify-space-around w-100'}>
                    <NameFilter label={'Name'} _key={'name'} {...filterProps} />
                    <Filter label={'Subject'} _key={'subject'} {...filterProps} data={subjectState.uniqueSubjects} labels={subjectState.uniqueSubjects}/>
                    <Filter label={'Level'} _key={'level'} {...filterProps} data={levels} labels={levels} />
                    <Filter label={'Price'} _key={'price'} {...filterProps} data={['s', 'm', 'l']} labels={['<$30', '$30/hr - $60/hr', '>$60/hr']} />
                    {/*<Filter label={'Gender'} _key={'gender'} {...filterProps} data={['M','F']} labels={['M','F']} />*/}
                </div>

            </div>
        </div>
    )
};

export default SearchFilter;