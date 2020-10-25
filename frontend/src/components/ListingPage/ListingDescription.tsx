import React from 'react';
import { Card } from 'antd';


const ListingDescription = () => {
    const test = ["This is the first chapter in a step-by-step guide about main React concepts. You can find a list of all its chapters in the navigation sidebar. If you’re reading this from a mobile device, you can access the navigation by pressing the button in the bottom right corner of your screen.", "Lorem ipsum dolor sit amet, consectetur adipiscing elitEtiam consectetur" +
        " faucibus tortor eget aliquet.Vestibulum efficitur ut sem a porta. Suspendisse ac scelerisque risus, id lobortis nibh."]


    return (
        <div>
            <div style={{ display: "flex", flexDirection: "column", margin: "10px" }}>
                <ul>
                    <Card
                        title="Math tuition for Secondary and JC levels"
                        headStyle={{
                            fontWeight: "bold"
                        }}
                    >
                        <p> {test} </p>
                    </Card>
                </ul>

            </div>
        </div>
    )
}

export default ListingDescription;
