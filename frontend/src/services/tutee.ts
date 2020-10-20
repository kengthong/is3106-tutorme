// interface TuteeServiceInterface {
//     getJobListingListWithParams(params: getJobListingListWithParamsProps): getJobListingListWithParamResposeProps;
// }
export default class TuteeService {
    static getJobListingListWithParams(params: getJobListingListWithParamsProps): getJobListingListWithParamResposeProps {
        return [
            {
                name: 'Kor Qianyi',
                img: 'https://www.cocotutors.com/wp-content/uploads/2018/10/photo6186083947126040598-e1538998971695-1.jpg',
                education: "Stanford University - Mathematics",
                description: "I am studying mathematics in Stanford University and I also love playing sports!",
                subjects: [
                    {
                        name: "Mathematics",
                    }, {
                        name: "Physics"
                    }
                ],
                price: "45",
                reviewScore: 4.5,
                reviewCount: 123
            },
            {
                name: 'Mary',
                img: 'https://www.cocotutors.com/wp-content/uploads/2018/10/IMG_3847.jpg',
                education: "National University of Singapore - Computer science",
                description: "I am studying CS in NUS and I hate sports!",
                subjects: [
                    {
                        name: "Computer science",
                    }, {
                        name: "Physics"
                    }
                ],
                price: "50",
                reviewScore: 4.2,
                reviewCount: 13
            },
            {
                name: 'Clarissa',
                img: 'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTS9z7_GLCKTojVjKh4VWrqiLvdfxFrictgdg&usqp=CAU',
                education: "Nanyang Technical University - Social sciences",
                description: "I am studying social sciences in NTU and I also love filming",
                subjects: [
                    {
                        name: "Social sciences",
                    }, {
                        name: "History"
                    }
                ],
                price: "40",
                reviewScore: 4.6,
                reviewCount: 10
            },
            {
                name: 'Snoop Dog',
                img: 'https://www.cocotutors.com/wp-content/uploads/2018/10/WhatsApp-Image-2018-09-08-at-12.46.50-300x300.jpeg',
                education: "Nanyang Technical University - Music",
                description: "I am studying music in NTU and I also love playing sports!",
                subjects: [
                    {
                        name: "Music",
                    }
                ],
                price: "40",
                reviewScore: 4.1,
                reviewCount: 13
            },
            {
                name: 'Jane',
                education: "National University of Singapore - Chemistry",
                description: "I am studying chemistry in NUS and I also love playing sports!",
                subjects: [
                    {
                        name: "Chemistry",
                    }, {
                        name: "Biology"
                    }
                ],
                price: "35",
                reviewScore: 4.2,
                reviewCount: 30
            }
        ]
    }
};