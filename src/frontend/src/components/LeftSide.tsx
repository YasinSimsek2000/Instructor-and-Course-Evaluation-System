import React, {useState} from 'react';
import '../scss/Home.scss';
import '../components/ModalNewSemester';
import {MDBBtn, MDBCard, MDBCardBody, MDBCardFooter, MDBCardHeader, MDBTypography} from 'mdb-react-ui-kit';
import ModalNewSemester from './ModalNewSemester';
import NewsBestPractices from "./NewsBestPractices";
import axios from "axios";

export interface ITerm {
    termId: number | undefined,
    name: string | undefined,
    startDate: string | undefined,
    endDate: string | undefined,
    termType: string | undefined
}

export const LeftSide = () => {
    const [isNewSemesterOpen, setIsNewSemesterOpen] = useState(false);
    const [term, setTerm] = useState<ITerm | undefined>();
    const [shownTerm, setShownTerm] = useState<ITerm | undefined>();
    const [bool1, setBool1] = useState<boolean>(false);

    const token = localStorage.getItem('jwtToken')
    axios.defaults.headers['Authorization'] = token;
    axios.get("http://localhost:8081/term/get-current", {
    })
        . then(response => {
            if (response.status == 200) {
                setShownTerm({
                    termId: response.data.id,
                    name: response.data.name,
                    startDate: response.data.startDate,
                    endDate: response.data.endDate,
                    termType: response.data.termType
                })
            }
        })
        .catch(error => {

        });

    return (
        <div>
            <MDBCard className="m-2 card-1">
                <MDBCardHeader className="m-0 card-title">
                    <h5 className="card-title">{shownTerm?.name} {shownTerm?.termType} TERM</h5>
                </MDBCardHeader>
                <MDBCardBody className="m-0 p-0">
                    <p className="card-title">Start Date: {shownTerm?.startDate}</p>
                    <p className="card-title">End Date: {shownTerm?.endDate}</p>
                </MDBCardBody>
                <MDBCardFooter>
                    <MDBBtn className="m-1 w-33">End Term</MDBBtn>
                    <MDBBtn className="m-1 w-33" onClick={() => {
                        setIsNewSemesterOpen(true);
                    }}>
                        Start Term
                    </MDBBtn>
                    {isNewSemesterOpen && (
                        <ModalNewSemester open={isNewSemesterOpen} onClose={() => setIsNewSemesterOpen(false)}>
                            <MDBTypography tag='div' className='display-6'>Start Term</MDBTypography>
                            <MDBTypography className='lead mb-0' tag='small'>Please select a start date & end date for
                                the semester.</MDBTypography>
                        </ModalNewSemester>
                    )}
                </MDBCardFooter>
            </MDBCard>
            <NewsBestPractices></NewsBestPractices>
        </div>
    );
};
