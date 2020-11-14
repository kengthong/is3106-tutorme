import React from 'react';
import {Select} from 'antd';

const { Option } = Select;
type filterProps = {
    labels: string[];
    label: string;
    _key: string;
    params: {[key:string]: any};
    data: string[];
    pushUrl: (params: any) => any;
}

const Filter = (props: filterProps) => {
    const { params, _key, data, label, labels, pushUrl } = props;
    const defaultVal = params[_key] ? '' + params[_key] : '';

    const handleChange = (value:any) => {
        pushUrl({
            ...params,
            [_key]: value === undefined? "" : value
        })
    }

    return (
        <div style={{display: 'flex', flexDirection: 'column'}}>
            <div >
                {label}
            </div>
            <Select
                showSearch
                defaultValue={defaultVal}
                style={{ width: 180 }}
                allowClear
                onChange={handleChange}>
                {data.map( (d, i) => (
                    <Option key={i} value={d}>{labels[i]}</Option>
                ))}
            </Select>
        </div>
    )
};

export default Filter;