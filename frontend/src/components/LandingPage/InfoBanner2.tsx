import React from 'react';

export const InfoBanner2 = () => {

    return (
        <div className={"flex-row"} style={{ height: '480px' }}>

            <div className="flex-row justify-end w-50 h-100 subject-section-form align-center" style={{ width: "100%", justifyContent: "center", position: "relative" }}>

                <div className="flex-col" style={{ zIndex: 1, padding: 30, width: "70%" }}>
                    <span className="fs-24" style={{ color: '#2F4F4F', fontWeight: "bold" }}>
                        Trust and Commitment
                    </span>
                    <br />
                    <span className="fs-18" style={{ color: '#2F4F4F' }}>
                        Over 200 Parents and teachers across Singapore trust our service because of our
                        commitment towards our students. We believe that education is a universal right
                        and that the cost of quality education should be fair. Hence, we allow users of
                        of our platform to negotiate prices with our tutors to ensure a fair price is
                        achieved.
                    </span>
                    <br />
                    <h4 style={{ justifyContent: "left", display: "flex", color: "#2F4F4F" }}>
                        <a href="/registration" >JOIN US TODAY! </a>
                    </h4>
                </div>
            </div>

            <div style={{ flexWrap: "wrap", overflow: "hidden" }}>
                <img src={"https://www.simplilearn.com/ice9/free_resources_article_thumb/book-resources-to-read-for-cissp-certification-exam-article.jpg"} style={{ height: "480px", width: "1800px" }} />
            </div>
        </div>
    )
}