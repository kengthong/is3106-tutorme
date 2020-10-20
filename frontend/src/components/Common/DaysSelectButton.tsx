import { Checkbox } from 'antd';
import React, { useState } from 'react'

const DaysSelectButton = () => {
    const [FormData, setFormData] = useState();
    const days = [
        { label: 'Mon', value: 'Mon' },
        { label: 'Tue', value: 'Tue' },
        { label: 'Wed', value: 'Wed' },
        { label: 'Thu', value: 'Thu' },
        { label: 'Fri', value: 'Fri' },
        { label: 'Sat', value: 'Sat' },
        { label: 'Sun', value: 'Sun' }
    ];


    return (
        <div>
            <Checkbox.Group
                name="daysGroup"
                options={days}
            >



            </Checkbox.Group>
        </div>
    )
}

export default DaysSelectButton
