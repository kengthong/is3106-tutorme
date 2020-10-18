import React from 'react';
import {Input} from 'antd';

const { Search } = Input;
type filterProps = {
    label: string;
    _key: string;
    params: {[key:string]: any};
    pushUrl: (params: any) => any;
}

const NameFilter = (props: filterProps) => {
    const { params, _key, label, pushUrl } = props;
    const defaultVal = params[_key] ? '' + params[_key] : '';
    const handleApply = (value:string ) => {
        pushUrl({
            ...params,
            [_key]: value
        })
    };

    return (
        <div style={{display: 'flex', flexDirection: 'column'}}>
            <div >
                {label}
            </div>
            <div className={'flex-row'}>
                <Search placeholder={'Name of tutor'}
                        onSearch={(value) => handleApply(value)}
                        enterButton="Apply"
                        defaultValue={defaultVal}/>
            </div>
        </div>
    )
};

export default NameFilter;