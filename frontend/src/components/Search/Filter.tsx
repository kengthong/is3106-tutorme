import React from 'react';
import qs from 'qs';
import { Select } from 'antd';

const { Option } = Select;
type filterProps = {
    label: string;
    _key: string;
    params: {[key:string]: any};
    data: string[];
    pushUrl: (params: any) => any;
}

const Filter = (props: filterProps) => {
    const { params, _key, data, label, pushUrl } = props;
    const defaultVal = params[_key] ? '' + params[_key] : '';

    const handleChange = (value:any) => {
        pushUrl({
            ...params,
            [_key]: value
        })
    }

    return (
        <div style={{display: 'flex', flexDirection: 'column'}}>
            <div >
                {label}
            </div>
            <Select defaultValue={defaultVal} style={{ width: 180 }} onChange={handleChange}>
                {data.map( (d, i) => (
                    <Option key={i} value={d}>{d}</Option>
                ))}
            </Select>
        </div>
    )
};

export default Filter;